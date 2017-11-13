//package com.zdf.webspider.processor.mj;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
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
//
//import com.zdf.bean.mj.MeijuUpdateNotice;
//import com.zdf.bean.mj.MjDirectoryVideo;
//import com.zdf.service.mj.MeijuUpdateNoticeService;
//import com.zdf.service.mj.MjDirectoryVideoService;
//import com.zdf.util.EscapeUnescape;
//import com.zdf.util.JuTypeUtil.JuType;
//import com.zdf.util.MjWebsiteConstant;
//
//@Component
//public class Mj941kProcessor implements PageProcessor {
//    private final Logger             logger = Logger.getLogger(Mj941kProcessor.class);
//
//    private StringBuffer             sb     = null;
//    @Autowired
//    private MjDirectoryVideoService mjDirectoryVideoService;
//    @Autowired
//   	private MeijuUpdateNoticeService meijuUpdateNoticeService;
//
//    private String sjUrl = "http://q673815913.pw/kx.php?vid=%s&type=mp4";
//
//    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
//
//
//    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
//    		.setDomain("www.941k.win")
//			.addHeader("Upgrade-Insecure-Requests", "1")
//            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//
//	@Override
//	public void process(Page page) {
//		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,video_url,video_name,create_time,create_by)";
//
//		Long directoryId = (Long) page.getRequest().getExtra("directoryId");
//		//美剧片名
//		String title = page.getHtml().css("title").regex("《.*》").toString();
//
//		String date = page.getHtml().css("div.player div.main").regex("\\(.*)\\").toString();
//		if(null != date && !"".equals(date)){
//			date = date.replace("(", "").replace("'", "");
//			date = EscapeUnescape.unescape(date);
//		}
//		//总的集数
//		String[] totalJi = date.split("#");
//
//		System.out.println("-- " + title);
//		Map<Integer,MjDirectoryVideo> maps = mjDirectoryVideoService.getVideoMapByDirectoryId(directoryId);
//
//		Integer curCount = totalJi.length;
//		if(curCount > maps.size()){
//			Long sort = mjDirectoryVideoService.getMaxSort(directoryId);
//			maxSortMap.put(directoryId, sort);
//
//			for (int i = 0; i < curCount; i++) {
//				String[] res = totalJi[i].split("\\$");
//				String videoUrl = String.format(sjUrl, res[1]);
//				String jishu = res[0].replace("第", "");
//
//				String indexJiShu = null;
//				Pattern pattern = Pattern.compile("[0-9].*[0-9]|[0-9]");
//		    	Matcher m = pattern.matcher(jishu);
//		    	while(m.find()) {
//		    		indexJiShu = m.group();
//		    	}
//
//				if(indexJiShu.lastIndexOf("-") != -1){
//					indexJiShu = indexJiShu.substring(0,indexJiShu.lastIndexOf("-"));
//				}
//				Integer indexJi = Integer.valueOf(indexJiShu);
//
//				if(maps.get(indexJi) == null){
//			    	maxSortMap.put(directoryId, maxSortMap.get(directoryId) + 1);
//			    	MjDirectoryVideo mjDirectoryVideo = new MjDirectoryVideo();
//			    	mjDirectoryVideo.setDirectoryId(directoryId);
//			    	mjDirectoryVideo.setType(MjWebsiteConstant.J941K);
//			    	mjDirectoryVideo.setVideoUrl(videoUrl);
//			    	mjDirectoryVideo.setVideoName(indexJi + "集");
//			    	mjDirectoryVideo.setSort(maxSortMap.get(directoryId));
//			    	String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.J941K+"', '"+videoUrl+"', '"+jishu+"', NOW(), 'admin');";
//			    	//System.out.println(sqlret);
//			    	try {
//						boolean flag = mjDirectoryVideoService.insert(mjDirectoryVideo);
//						logger.info(title + "--> " + indexJi + "集    新增:"+flag);
//					} catch (Exception e) {
//						System.out.println(title +" --> "+sqlret);
//					}
//				}
//			}
//		}else{
//			logger.info("无更新-->" + title + ": " + directoryId + " 当前记录:" + curCount + " 已有记录:"+ maps.size());
//		}
//
//	}
//
//	@Override
//	public Site getSite() {
//		return site;
//	}
//
//
//	public void handleLinks(Mj941kProcessor mj941kProcessor){
//    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.J941K,JuType.MEIJU.getCode());
//    	if(null != list && list.size() > 0){
//            sb = new StringBuffer();
//    		Spider spider = Spider.create(mj941kProcessor);
//    		Request request = null;
//            int index = 1;
//    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
//    			request = new Request(meijuUpdateNotice.getLink());
//    			request.putExtra("id", meijuUpdateNotice.getId());
//                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
//                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
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
////	public static void main(String[] args) {
////		Map<String,String> map = new HashMap<String,String>();
////    	map.put("2130", "http://www.941k.win/?m=vod-play-id-1515-src-1-num-3.html");
////
////    	Mj941kProcessor sql941k = new Mj941kProcessor();
////    	Spider spider = Spider.create(sql941k);
////    	for (Map.Entry<String,String> entry : map.entrySet()) {
////    		Request request = new Request(entry.getValue());
////        	request.putExtra("directoryId", entry.getKey());
////        	spider.addRequest(request);
////        }
////        spider.thread(1).run();
////    }
//}
