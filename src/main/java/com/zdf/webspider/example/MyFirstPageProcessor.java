package com.zdf.webspider.example;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyFirstPageProcessor implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    //    @Override
    //    public void process(Page page) {
    //        //        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    //        //        page.putField("name",
    //        //            page.getHtml().xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/text()")
    //        //                .all().toString());
    //        //        page.putField("links",
    //        //            page.getHtml().xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/@href")
    //        //                .all().toString());
    //        System.out.println("============================" + page.getUrl()
    //                           + "============================");
    //        List<String> name = page.getHtml()
    //            .xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/text()").all();
    //
    //        List<String> links = page.getHtml()
    //            .xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/@href").all();
    //
    //        for (int i = 0; i < links.size(); i++) {
    //            page.putField(name.get(i).trim(), links.get(i).trim());
    //            System.out.println(name.get(i).trim() + "--" + links.get(i).trim());
    //        }
    //
    //        //        System.out.println(name.toString());
    //        //        System.out.println(links.toString());
    //        //        if (page.getResultItems().get("name")==null){
    //        //            page.setSkip(true);
    //        //        }
    //        //        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    //    }
    @Override
    public void process(Page page) {
        //        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        //        page.putField("name",
        //            page.getHtml().xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/text()")
        //                .all().toString());
        //        page.putField("links",
        //            page.getHtml().xpath("//div[@class='entry_box_s']/div[@class='entry']/div/p/a/@href")
        //                .all().toString());


        //可以获取到第一个class数据
//        List<String> names = page.getHtml()
//            .css("div.down_list ul li input[name=down_url_list_0] + p").xpath("//a/text()").all();
//
//        List<String> links = page.getHtml()
//            .css("div.down_list ul li input[name=down_url_list_0] + p").xpath("//a/@href").all();
//        System.out.println("============================" + page.getUrl()
//                           + "============================ start");
//        for (int i = 0; i < links.size(); i++) {
//            page.putField(names.get(i).trim(), links.get(i).trim());
//            System.out.println(names.get(i).trim() + "--" + links.get(i).trim());
//        }
//        System.out.println("============================" + page.getUrl()
//                           + "============================ end");
    	List<String> links = page.getHtml().css("div.entry").xpath("//a/@href").all();
    	System.err.println(links.size());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MyFirstPageProcessor())
        //            .addUrl("http://www.meijutt.com/content/meiju20945.html")
        //            .addUrl("http://www.meijutt.com/content/meiju20408.html")
            //            .addUrl("http://www.meijutt.com/content/meiju20916.html")
            .addUrl("http://movie.asjyy.com/ptvod.php?v=gq_8432202_sohu")
        //            .addUrl("http://www.5ikmj.com/archives/3/603")
            //            .addUrl("http://www.5ikmj.com/archives/1/1072")
            //            .addUrl("http://www.5ikmj.com/archives/3/592")
            //.addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
            .thread(1).run();
    }
}
