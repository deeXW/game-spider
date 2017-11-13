//package com.zdf.webspider.processor;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Request;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.processor.PageProcessor;
//import us.codecraft.webmagic.selector.Html;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zdf.bean.mj.MeijuUpdateNotice;
//import com.zdf.bean.mj.MjDirectory;
//import com.zdf.bean.mj.MjDirectoryVideo;
//import com.zdf.mail.JavaMail;
//import com.zdf.service.mj.MeijuUpdateNoticeService;
//import com.zdf.service.mj.MjDirectoryService;
//import com.zdf.service.mj.MjDirectoryVideoService;
//import com.zdf.util.MjWebsiteConstant;
//
///**
// * 4k吧
// * http://www.kkkkba.com/
// * @author dee
// *
// */
//@Component
//public class KkkkbaProcessor implements PageProcessor {
//    private final Logger             logger = Logger.getLogger(KkkkbaProcessor.class);
//
//    private StringBuffer             sb     = null;
//
//    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
//    		.setDomain("www.meijukankan.net")
//			.addHeader("Upgrade-Insecure-Requests", "1")
//            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//    private String domain = "http://www.kkkkba.com";
//
//    @Autowired
//	private MeijuUpdateNoticeService meijuUpdateNoticeService;
//
//    @Autowired
//    private MjDirectoryVideoService mjDirectoryVideoService;
//
//    @Autowired
//    private MjDirectoryService mjDirectoryService;
//
//    @Autowired
//    private JavaMail                 javaMail;
//
//    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
//
//    @Override
//    public void process(Page page) {
//    	List<String> modulelinks = page.getHtml().css("#detail-list").xpath("//div[@class='play-list-box']").all();
//    	Map mapExt = (Map)page.getRequest().getExtra("mapExt");
//
//    	if(!page.getUrl().toString().contains("html")){
//    		String mouleType = (String)mapExt.get("code");
//    		String title = page.getHtml().css("title").regex("《.*》").toString();
//    		Long directoryId = (Long) page.getRequest().getExtra("directoryId");
//    		//logger.info("--"+title);
//    		int loopType = 0;
//    		for (String modulelink : modulelinks) {
//        		if(modulelink.contains(mouleType) && loopType == 0){
//        			loopType++;
//        			List<String> links = new Html(modulelink).css("div.content").xpath("//a/@href").all();
//        			List<String> names = new Html(modulelink).css("div.content").xpath("//a/text()").all();
//
//        			Map<Integer,MjDirectoryVideo> maps = mjDirectoryVideoService.getVideoMapByDirectoryId(directoryId);
//        			Integer curCount = links.size();
//
//        			if(curCount > maps.size()){
//        				Long sort = mjDirectoryVideoService.getMaxSort(directoryId);
//        				maxSortMap.put(directoryId, sort);
//
//        				Request request = null;
//            			for (int i = curCount - maps.size() - 1; i > -1 ; i--) {
//            				request = new Request(domain + links.get(i));
//            				request.putExtra("mouleType", mouleType);
//            				request.putExtra("directoryId", directoryId);
//            				request.putExtra("indexJi", names.get(i).replace("第", ""));
//            				request.putExtra("title", title);
//            				page.addTargetRequest(request);
//        				}
//        			}else{
//        				logger.info("无更新-->" + title + ": " + directoryId + " 当前记录:" + curCount + " 已有记录:"+ maps.size());
//        			}
//        		}
//    		}
//    	}else{
//    		String src = page.getHtml().css("#play-focus").xpath("//iframe/@src").toString();
//
//    		String indexJi = (String) page.getRequest().getExtra("indexJi");
//    		Long directoryId = (Long) page.getRequest().getExtra("directoryId");
//    		String mouleType = (String) page.getRequest().getExtra("mouleType");
//    		String title = (String) page.getRequest().getExtra("title");
//    		String type = MjWebsiteConstant.get4kbaTypeByCode(mouleType);
//
//    		src = src.substring(0,src.indexOf(MjWebsiteConstant.get4kbaSubByCode(mouleType)));
//
//    		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_url,video_name,create_time,create_by)";
//
//
//
//
//    		maxSortMap.put(directoryId, maxSortMap.get(directoryId) + 1);
//	    	MjDirectoryVideo mjDirectoryVideo = new MjDirectoryVideo();
//	    	mjDirectoryVideo.setDirectoryId(directoryId);
//	    	mjDirectoryVideo.setType(MjWebsiteConstant.get4kbaTypeByCode(mouleType));
//	    	mjDirectoryVideo.setOrgUrl(src);
//	    	mjDirectoryVideo.setVideoUrl(src.replace("player3", "player5"));
//	    	mjDirectoryVideo.setVideoName(indexJi);
//	    	mjDirectoryVideo.setSort(maxSortMap.get(directoryId));
//
//	    	MjDirectory mjDirectory = new MjDirectory();
//	    	mjDirectory.setId(Long.valueOf(directoryId));
//	    	mjDirectory.setUpdateCount(indexJi.replace("集", ""));
//
//	    	String sqlret = sql + "VALUES (" + indexJi.replace("集", "") +","+directoryId+", '"+type+"', '"+src+"', '"+src.replace("player3", "player5")+"', '"+indexJi + "', NOW(), 'admin');";
////    		logger.info(sqlret);
//	    	try {
//				boolean flag = mjDirectoryVideoService.insert(mjDirectoryVideo);
//				boolean flag2 = mjDirectoryService.update(mjDirectory);
//				logger.info(title + "--> " + indexJi + " " + directoryId +" 新增:"+flag + " 更新主表updateCount信息：" + flag2);
//			} catch (Exception e) {
//				System.out.println(title +" --> "+sqlret);
//			}
//    	}
//
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//    public void handleLinks(KkkkbaProcessor meijuHuiProcessor,String juType){
//    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.KKKK,juType);
//    	if(null != list && list.size() > 0){
//    		JSONObject json = new JSONObject();
//            sb = new StringBuffer();
//    		Spider spider = Spider.create(meijuHuiProcessor);
//    		Request request = null;
//            int index = 1;
//    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
//				Map<String,String> mapExt = json.parseObject(meijuUpdateNotice.getExtContent(), Map.class);
//    			request = new Request(meijuUpdateNotice.getLink());
//    			request.putExtra("id", meijuUpdateNotice.getId());
//                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
//                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
//                request.putExtra("mapExt", mapExt);
//                request.putExtra("endFlag", false);
//                if (index == list.size()) {
//                    request.putExtra("endFlag", true);
//                }
//    			spider.addRequest(request);
//                index++;
//			}
//    		spider.thread(1).run();
//    	}
//    }
//
//}
