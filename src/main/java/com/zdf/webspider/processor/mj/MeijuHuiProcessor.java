package com.zdf.webspider.processor.mj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.mail.JavaMail;
import com.zdf.service.mj.MeijuUpdateNoticeService;
import com.zdf.service.mj.MjDirectoryVideoService;
import com.zdf.util.JuTypeUtil.JuType;
import com.zdf.util.MjWebsiteConstant;

/**
 * 美剧汇
 * www.meijukankan.net
 * @author dee
 *
 */
@Component
public class MeijuHuiProcessor implements PageProcessor {
    private final Logger             logger = Logger.getLogger(MeijuHuiProcessor.class);

    private StringBuffer             sb     = null;

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.meijukankan.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    @Autowired
	private MeijuUpdateNoticeService meijuUpdateNoticeService;
    
    @Autowired
    private MjDirectoryVideoService mjDirectoryVideoService;
    
    @Autowired
    private JavaMail                 javaMail;

    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
    
    @Override
    public void process(Page page) {
    	String sql = "INSERT INTO mj_directory_video(directory_id,type,org_url,video_url,video_name,create_time,create_by)"; 
    	if(page.getUrl().toString().contains("detail")){
    		Long directoryId = (Long) page.getRequest().getExtra("directoryId");
    		String directoryName = (String)page.getRequest().getExtra("directoryName");
    		
    		List<String> links = page.getHtml().css("#qiyi_con").xpath("//a[contains(@href, 'player')]/@href").all();
			List<String> names = page.getHtml().css("#qiyi_con").xpath("//a[contains(@href, 'player')]/em[@class='num']/text()").all();
			Map<Integer,MjDirectoryVideo> maps = mjDirectoryVideoService.getVideoMapByDirectoryId(Long.valueOf(directoryId));
			Integer curCount = names.size();
			if(null == names || names.size() < 1){
	        	System.out.println(directoryName + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
	        	page.setSkip(true);
	        }
			if(curCount > maps.size()){
				Long sort = mjDirectoryVideoService.getMaxSort(directoryId);
				maxSortMap.put(directoryId, sort);
				Request request = null;
				for (int i = 0; i < names.size(); i++) {
					Integer indexJi = Integer.valueOf(names.get(i).replace("集", "").trim());
					if(maps.get(indexJi) == null){
						request = new Request(links.get(i).replace("..", "http://www.meijukankan.net"));
						request.putExtra("title", directoryName);
						request.putExtra("name", names.get(i));
						request.putExtra("directoryId", directoryId);
						logger.info(directoryName+": "+directoryId + "-->  新增 第"+ names.get(i));
						page.addTargetRequest(request);
					}
				}
			}else{
				logger.info("无更新-->" + directoryName + ": " + directoryId + " 当前记录:" + curCount + " 已有记录:"+maps.size());
				//处理更新的情况
//				Request request = null;
//				for (int i = 0; i < maps.size(); i++) {
//					Integer indexJi = Integer.valueOf(names.get(i).replace("集", "").trim());
//					MjDirectoryVideo mjDirectoryVideo = maps.get(indexJi);
//					if(null == mjDirectoryVideo){
//						logger.error("数据异常-->" + directoryName + ": " + directoryId + " 第" + indexJi + " 获取记录为空");
//					}else{
//						if(null == mjDirectoryVideo.getOrgUrl() || "".equals(mjDirectoryVideo.getOrgUrl())){
//							//需要被update
//							request = new Request(links.get(i).replace("..", "http://www.meijukankan.net"));
//							request.putExtra("title", directoryName);
//							request.putExtra("name", names.get(i));
//							request.putExtra("directoryId", directoryId);
//							logger.info(directoryName+": "+directoryId + "-->  新增 第"+ names.get(i));
//							page.addTargetRequest(request);
//						}
//					}
//				}
				
				
			}
			
		}else if(page.getUrl().toString().contains("player")){
//	    	String url =page.getHtml().regex(".*f : '(.*?)'.*").toString().replace("/geth5", "http://www.meijukankan.net/geth5").replace("->ajax/get/utf-8", "");
			String type = "meijuhui";
			String url =page.getHtml().regex("video = \\[ '(.+?)'").toString();
			if(null==url || "".equals(url)){
	    		type = "iframe";
	    		url = page.getHtml().regex("var vid='(.+?)';").toString();
	    		url = "http://www.mjt100.com/CC/index.php?vid=" + url;
	    	}else{
	    		url =url.replace("/geth5", "http://www.meijukankan.net/geth5").replace("->ajax/get/utf-8", "");
	    	}
	    	
	    	String title = (String) page.getRequest().getExtra("title");
	    	String name = (String) page.getRequest().getExtra("name");
	    	Long directoryId = (Long) page.getRequest().getExtra("directoryId");
	    	maxSortMap.put(directoryId, maxSortMap.get(directoryId) + 1);
	    	MjDirectoryVideo mjDirectoryVideo = new MjDirectoryVideo();
	    	mjDirectoryVideo.setDirectoryId(directoryId);
	    	mjDirectoryVideo.setType(type);
	    	mjDirectoryVideo.setOrgUrl(url);
	    	mjDirectoryVideo.setVideoName(name);
	    	mjDirectoryVideo.setSort(maxSortMap.get(directoryId));
	    	sql += "VALUES ("+directoryId+", '"+maxSortMap.get(directoryId)+"', '"+url+"', '', '"+name+"', NOW(), 'admin');";
	    	try {
				boolean flag = mjDirectoryVideoService.insert(mjDirectoryVideo);
				logger.info(title + "-->" + name + " 新增:"+flag);
			} catch (Exception e) {
				System.out.println();
				System.out.println(title +" --> "+sql);
			}
	    	
		}
    
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void handleLinks(MeijuHuiProcessor meijuHuiProcessor){
    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.MEIJUHUI,JuType.MEIJU.getCode());
    	if(null != list && list.size() > 0){
            sb = new StringBuffer();
    		Spider spider = Spider.create(meijuHuiProcessor);
    		Request request = null;
            int index = 1;
    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
    			request = new Request(meijuUpdateNotice.getLink());
    			request.putExtra("id", meijuUpdateNotice.getId());
    			request.putExtra("nowUpdateCount", meijuUpdateNotice.getNowUpdateCount());
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
    
    public static void main(String[] args) {
    	Request request = new Request("http://cn163.net/archives/17663/");
    	request.putExtra("1", "nihao");
    	
    	MeijuHuiProcessor meijuHuiProcessor = new MeijuHuiProcessor();
    	//http://cn163.net/archives/17663/
        Spider spider = Spider.create(meijuHuiProcessor);
        spider.thread(1).addRequest(request).run();
    }
}
