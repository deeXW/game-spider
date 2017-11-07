package com.zdf.webspider.processor.hj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.bean.mj.MjDirectory;
import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.mail.JavaMail;
import com.zdf.service.mj.MeijuUpdateNoticeService;
import com.zdf.service.mj.MjDirectoryService;
import com.zdf.service.mj.MjDirectoryVideoService;
import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.JuTypeUtil.JuType;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 主站 http://www.77ds.net/
 * 主站中的资源他是通过flash播放的，同时还是断点加载
 * 
 * 手机端 http://m.77ds.net/
 * 资源是mp4 从这里爬取资源
 * 
 * @author dee
 *
 */

@Component
public class HjM77dsProcessor implements PageProcessor {
    private final Logger             logger = Logger.getLogger(HjM77dsProcessor.class);

    private StringBuffer             sb     = null;

    @Autowired
	private MeijuUpdateNoticeService meijuUpdateNoticeService;
    
    @Autowired
    private MjDirectoryVideoService mjDirectoryVideoService;
    
    @Autowired
    private MjDirectoryService mjDirectoryService;
    
    @Autowired
    private JavaMail                 javaMail;
    
    private static final String DOMAIN = "http://m.77ds.net/";

    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
    
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("m.77ds.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,video_url,video_name,create_time,create_by)"; 
		
		if(page.getUrl().toString().contains("zaixianbofang")){
			Long directoryId = (Long) page.getRequest().getExtra("directoryId");
			//美剧片名
			String title = page.getHtml().css("title").regex("《.*》").toString();
	        
			//获取集数名称
			List<String> titleList = page.getHtml().css("div#playbox p.ykyun + div").xpath("//a/@title").all();
			//获取js信息
			String jsPath = page.getHtml().css("div#wrapper div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			if(null == titleList || titleList.size() < 1){
				titleList = page.getHtml().css("section.box div.serieslist div.ykyun + div").xpath("//a/@title").all();
				jsPath = page.getHtml().css("div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			}
			
			String comPath = DOMAIN + jsPath;
	        Request request = new Request(comPath);
			page.addTargetRequest(request);
			request.putExtra("titleList", titleList);
			request.putExtra("title", title);
			request.putExtra("directoryId", directoryId);
			//由于获取js的时候出现乱码，由于采用默认的编码格式导致，所以在调用js内容时，先设置下编码格式
			site.setCharset("GBK");
		}else{
			String title = (String) page.getRequest().getExtra("title");
			Long directoryId = (Long) page.getRequest().getExtra("directoryId");
			String res = page.getHtml().regex("云视频.*ykyun']],").toString();
			if("".equals(res) || null == res){
				System.out.println(title + " " + directoryId + " 获取js数据为空  连接: " + page.getUrl());
				page.setSkip(true);
			}
			//获取每集名称
			List<String> titleList = (List<String>) page.getRequest().getExtra("titleList");
			//从js获取主要关键参数
			res = res.replace("云视频',[", "").replace("]],", "");
			String[] resArr = res.split(",");
			
			Map<Integer,MjDirectoryVideo> maps = mjDirectoryVideoService.getVideoMapByDirectoryId(Long.valueOf(directoryId));
			Integer curCount = resArr.length;
			
			if(null == resArr || resArr.length < 1){
	        	System.out.println(title + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
	        	page.setSkip(true);
	        }
			
			if(curCount > maps.size()){
				Long sort = mjDirectoryVideoService.getMaxSort(directoryId);
				maxSortMap.put(directoryId, sort);
				
				for (int i = 0; i < resArr.length; i++) {
//					Pattern pattern = Pattern.compile("[^0-9]");
//			    	Matcher m = pattern.matcher(titleList.get(i));
//			    	String indexJiShu = m.replaceAll("");
			    	
			    	String indexJiShu = null;
					Pattern pattern = Pattern.compile("[0-9].*[0-9]|[0-9]");
			    	Matcher m = pattern.matcher(titleList.get(i));
			    	while(m.find()) {
			    		indexJiShu = m.group();
			    	}
			    	
					if(indexJiShu.lastIndexOf("-") != -1){
						indexJiShu = indexJiShu.substring(0,indexJiShu.lastIndexOf("-"));
					}
					
					Integer indexJi = Integer.valueOf(indexJiShu);
					if(maps.get(indexJi) == null){
						String ss = resArr[i].substring(resArr[i].indexOf("$")+1,resArr[i].lastIndexOf("$")+6);
						String videoUrl = MjWebsiteConstant.M77DS_VIDEO_DOMAIN + "?url="+ss.replace("$", "_");
						
				    	maxSortMap.put(directoryId, maxSortMap.get(directoryId) + 1);
				    	MjDirectoryVideo mjDirectoryVideo = new MjDirectoryVideo();
				    	mjDirectoryVideo.setDirectoryId(directoryId);
				    	mjDirectoryVideo.setType(MjWebsiteConstant.M77DS);
				    	mjDirectoryVideo.setVideoUrl(videoUrl);
				    	mjDirectoryVideo.setVideoName(indexJi + "集");
				    	mjDirectoryVideo.setSort(maxSortMap.get(directoryId));
				    	String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.M77DS+"', '"+videoUrl+"', '"+indexJi + "集', NOW(), 'admin');";
				    	
				    	MjDirectory mjDirectory = new MjDirectory();
				    	mjDirectory.setId(directoryId);
				    	mjDirectory.setUpdateCount(indexJi+"");
				    	try {
							boolean flag = mjDirectoryVideoService.insert(mjDirectoryVideo);
							boolean flag2 = mjDirectoryService.update(mjDirectory);
							logger.info(title + "--> " + indexJi + "集    新增:"+flag + " 更新主表updateCount信息：" + flag2);
						} catch (Exception e) {
							System.out.println(title +" --> "+sqlret);
						}
				    	
					}
				}
			}else{
				logger.info("无更新-->" + title + ": " + directoryId + " 当前记录:" + curCount + " 已有记录:"+ maps.size());
			}
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	public void handleLinks(HjM77dsProcessor m77dsProcessor){
    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.M77DS,JuType.HANJU.getCode());
    	if(null != list && list.size() > 0){
            sb = new StringBuffer();
    		Spider spider = Spider.create(m77dsProcessor);
    		Request request = null;
            int index = 1;
    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
    			request = new Request(meijuUpdateNotice.getLink());
    			request.putExtra("id", meijuUpdateNotice.getId());
                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
                request.putExtra("endFlag", false);
                if (index == list.size()) {
                    request.putExtra("endFlag", true);
                }
    			spider.addRequest(request);
                index++;
			}
    		spider.thread(1).run();
    	}
    }
	
}
