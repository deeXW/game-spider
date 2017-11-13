//package com.zdf.webspider.processor.mj;
//
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
//import us.codecraft.webmagic.selector.Selectable;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zdf.bean.mj.MeijuUpdateNotice;
//import com.zdf.service.mj.MeijuUpdateNoticeService;
//import com.zdf.service.mj.MjDirectoryLinksService;
//import com.zdf.util.MjWebsiteConstant;
//import com.zdf.util.JuTypeUtil.JuType;
//
//
///**
// * 字幕组爬虫
// * www.zimuzu.tv
// * 主要问题在于cookie
// */
//@Component
//public class ZimuzuProcessor implements PageProcessor {
//	private final Logger             logger = Logger.getLogger(MeijuHuiProcessor.class);
//
//    private StringBuffer             sb     = null;
//
//    @Autowired
//	private MeijuUpdateNoticeService meijuUpdateNoticeService;
//    @Autowired
//    private MjDirectoryLinksService mMjDirectoryLinksService;
//
//
//
//    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
//            .setDomain("www.zimuzu.tv")
//            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//            .addHeader("AlexaToolbar-ALX_NS_PH", "AlexaToolbar/alx-4.0")
//            .addHeader("Cookie", "last_item:34747=Easy.S01E01.720p.WEBRip.X264-DEFLATE.mkv; last_item_date:34747=1474645718; mykeywords=a%3A10%3A%7Bi%3A0%3Bs%3A12%3A%22%E6%80%A7%E7%88%B1%E5%A4%A7%E5%B8%88%22%3Bi%3A1%3Bs%3A9%3A%22%E8%B7%AF%E8%A5%BF%E6%B3%95%22%3Bi%3A2%3Bs%3A12%3A%22%E7%88%86%E7%AC%91%E8%B6%85%E5%B8%82%22%3Bi%3A3%3Bs%3A12%3A%22%E5%85%AD%E5%BA%A6%E5%8D%B1%E6%9C%BA%22%3Bi%3A4%3Bs%3A12%3A%22%E5%85%AD%E5%9C%BA%E5%8D%B1%E4%BA%8B%22%3Bi%3A5%3Bs%3A12%3A%22%E9%9A%90%E4%B8%96%E7%8B%BC%E5%A5%B3%22%3Bi%3A6%3Bs%3A12%3A%22%E6%97%B6%E5%85%89%E7%97%95%E8%BF%B9%22%3Bi%3A7%3Bs%3A15%3A%22%E6%8C%87%E5%AE%9A%E5%B9%B8%E5%AD%98%E8%80%85%22%3Bi%3A8%3Bs%3A12%3A%22%E7%A5%9E%E7%83%A6%E8%AD%A6%E6%8E%A2%22%3Bi%3A9%3Bs%3A18%3A%22%E7%BE%8E%E5%9B%BD%E6%81%90%E6%80%96%E6%95%85%E4%BA%8B%22%3B%7D; PHPSESSID=ojm0cka4glik0c1mt1p828nj74; GINFO=uid%3D4387036%26nickname%3Dwoikmj%26group_id%3D1%26avatar_t%3Dhttp%3A%2F%2Ftu.rrsub.com%2Fftp%2Favatar%2Ff_noavatar_t.gif%26main_group_id%3D0%26common_group_id%3D54; GKEY=6af449b86a3eec4acd06f492d97e3db6; cps=suning%2F1476553793%3Byhd%2F1477220533%3Btujia%2F1476553798%3Bwomai%2F1476553817; ctrip=ctrip%2F1477220533; CNZZDATA1254180690=1589025395-1474206150-null%7C1477215766")
//            .addHeader("Host", "www.zimuzu.tv")
//                                //            .addHeader("Referer", "http://www.zimuzu.tv/resource/32138")
//            .addHeader("Upgrade-Insecure-Requests", "1")
//            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//
//    @Override
//    public void process(Page page) {
//    	Long directoryId = (Long) page.getRequest().getExtra("directoryId");
//    	String directoryName = (String) page.getRequest().getExtra("directoryName");
//    	String season = (String) page.getRequest().getExtra("season");
//    	String format = (String) page.getRequest().getExtra("format");
//    	Integer curLinksCount = mMjDirectoryLinksService.getLinksCount(directoryId);
//
//    	Selectable selectable = page.getHtml().css("div.media-box div.media-list ul li[season="+season+"][format="+format+"]");
//        List<String> links = selectable.css("div.fr").xpath("//a[@type='ed2k']/@href").all();
//        List<String> names = selectable.css("div.fl").xpath("//a[@class='f7']/@title").all();
//        //System.out.println("-- "+directoryName);
////        for (int i = 0; i < links.size(); i++) {
////        	System.out.println(names.get(i) + "--> " + links.get(i));
////		}
//        if(null == links || links.size() < 1){
//        	System.out.println(directoryName + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
//        	page.setSkip(true);
//        }
//        if(links.size() > curLinksCount){
//        	for (int i = curLinksCount; i < links.size(); i++) {
//            	System.out.println(names.get(i) + "--> " + links.get(i));
//    		}
//        }else{
//        	System.out.println("无更新 --> 剧名："+directoryName+" 目录Id: " + directoryId);
//        }
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//    @SuppressWarnings({ "unchecked", "static-access" })
//	public void handleLinks(ZimuzuProcessor zimuzuProcessor){
//    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.ZIMUZU,JuType.MEIJU.getCode());
//    	if(null != list && list.size() > 0){
//    		JSONObject json = new JSONObject();
//            sb = new StringBuffer();
//    		Spider spider = Spider.create(zimuzuProcessor);
//    		Request request = null;
//            int index = 1;
//    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
//    			Map<String,String> mapExt = json.parseObject(meijuUpdateNotice.getExtContent(), Map.class);
//    			String season = (String)mapExt.get("season");
//    			String format = (String)mapExt.get("format");
//    			request = new Request(meijuUpdateNotice.getLink());
//    			request.putExtra("id", meijuUpdateNotice.getId());
//    			request.putExtra("nowUpdateCount", meijuUpdateNotice.getNowUpdateCount());
//                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
//                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
//                request.putExtra("season", season);
//            	request.putExtra("format", format);
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
//
//
//}
