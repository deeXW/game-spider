package com.zdf.webspider.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by cheguo on 2017/4/7.
 */
public class GameContentProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(GameContentProcessor.class);

    private Site site;
    private static final String DOMAIN = "www.9game.cn";
    private static final String HTTP = " http://www.444wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";

    public void process(Page page){

        if(page.getUrl().toString().contains("down.js")){
            List<String> list = page.getHtml().css(".fb").xpath("//a/@href").all();
            System.out.println("高速下载器通道");
            for (String string: list) {
                System.out.println(string);
            }
        }else{
            String name = page.getHtml().xpath("//h1[@class='name']//text()").toString();
            String subName = page.getHtml().xpath("//i[@class='sub_name']//text()").toString();
            String sdIco = page.getHtml().xpath("//img[@class='sd_ico']//@src").toString();
            List<String> spanList = page.getHtml().css(".sdtc_1 span").replace("<[^>]*>","").all();
            String guanang = page.getHtml().css(".sdtc_1 span").xpath("//a/@href").toString();
            String content = page.getHtml().css(".sd_intro .more-bd").replace("<[^>]*>","").toString();

            String apkurl = page.getHtml().css(".cf .sdown_box").regex("apkurl = \"(.*?)\";").toString();
            String apkJs = HTTP + page.getHtml().css(".cf .sdown_box").regex("script src=\"(.*?)\"></script").toString();

            page.addTargetRequest(apkJs);

            System.out.println("name: " + name);
            System.out.println("subName: " + subName);
            System.out.println("图标 sdIco: " + HTTP + sdIco);

            for (String string: spanList) {
                if(string.contains("应用官网")){
                    System.out.println(string + " 链接：" + guanang);
                }else{
                    System.out.println(string);
                }
            }

            System.out.println("软件详情：" + content);

            System.out.println("其它下载地址: " + apkurl);
        }

    }

    public Site getSite(){
        if(site == null){
            site = Site.me().setDomain(DOMAIN)
                    .setSleepTime(500)
                    .setUserAgent(UA);

        }
        return site;
    }

    public static void main(String[] args) {
        GameContentProcessor test = new GameContentProcessor();
        Spider.create(test).addUrl("http://www.444wan.com/shouji/8.html").thread(1).run();
    }
}
