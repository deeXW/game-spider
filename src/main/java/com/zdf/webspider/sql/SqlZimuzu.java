//package com.zdf.webspider.sql;
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
//import com.zdf.bean.mj.MjDirectoryLinks;
//import com.zdf.service.mj.MeijuUpdateNoticeService;
//import com.zdf.service.mj.MjDirectoryLinksService;
//
//
///**
// * 字幕组爬虫
// * www.zimuzu.tv
// * 主要问题在于cookie
// */
//@Component
//public class SqlZimuzu implements PageProcessor {
//	private final Logger             logger = Logger.getLogger(SqlZimuzu.class);
//
//    private StringBuffer             sb     = null;
//
//    @Autowired
//	private MeijuUpdateNoticeService meijuUpdateNoticeService;
//    @Autowired
//    private MjDirectoryLinksService mjDirectoryLinksService;
//
//
//
//    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
//            .setDomain("www.zimuzu.tv")
//            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//            .addHeader("AlexaToolbar-ALX_NS_PH", "AlexaToolbar/alx-4.0")
//            .addHeader("Cookie", "PHPSESSID=mk3ag1actcmad4mev2otqhk4i2; last_item_date:31846=1473261785; mykeywords=a%3A1%3A%7Bi%3A0%3Bs%3A6%3A%22%E6%AF%92%E6%9E%AD%22%3B%7D; GINFO=uid%3D4387036%26nickname%3Dwoikmj%26group_id%3D1%26avatar_t%3D%26main_group_id%3D0%26common_group_id%3D54; GKEY=8b11684cfe3bf897f3c86696656162fd; CNZZDATA1254180690=689732897-1473255724-http%253A%252F%252Fwww.zimuzu.tv%252F%7C1473261124")
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
//
//    	Map<String,MjDirectoryLinks> youMap = mjDirectoryLinksService.getLinksMapByParams(directoryId, "1");
//    	Map<String,MjDirectoryLinks> wuMap = mjDirectoryLinksService.getLinksMapByParams(directoryId, "0");
//    	Integer curYouLinksCount = null == youMap ? 0:youMap.size();
//    	Integer curWuLinksCount = null == wuMap ? 0:wuMap.size();
//
//    	//有字幕
//    	Selectable selectable = page.getHtml().css("div.media-box div.media-list ul li[season="+season+"][format="+format+"]");
//        List<String> links = selectable.css("div.fr").xpath("//a[@type='ed2k']/@href").all();
//        List<String> names = selectable.css("div.fl").xpath("//a/@title").all();
//        System.out.println("-- "+directoryName);
//        if(null == links || links.size() < 1){
//        	System.out.println(directoryName + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
//        	page.setSkip(true);
//        }
//        if(links.size() > curYouLinksCount){
//        	for (int i = curYouLinksCount; i < links.size(); i++) {
//            	System.out.println(names.get(i) + "--> " + links.get(i));
//    		}
//        }else{
//        	System.out.println("-- 无更新");
//        }
//
//        //无幕
//        Selectable selectable2 = page.getHtml().css("div.media-box div.media-list ul li[season="+season+"][format=WEB-DL]");
//        List<String> links2 = selectable2.css("div.fr").xpath("//a[@type='ed2k']/@href").all();
//        List<String> names2 = selectable2.css("div.fl").xpath("//a/@title").all();
//
//
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//
//    public static void main(String[] args) {
//    	SqlZimuzu sqlZimuzu = new SqlZimuzu();
//    	Spider spider = Spider.create(sqlZimuzu);
//    		Request request = new Request("http://www.zimuzu.tv/gresource/list/33725");
//    		request.putExtra("directoryId", 123l);
//        	request.putExtra("season", "2");
//        	request.putExtra("format", "HR-HDTV");
//        	spider.addRequest(request);
//        spider.thread(1).run();
//    }
//}
