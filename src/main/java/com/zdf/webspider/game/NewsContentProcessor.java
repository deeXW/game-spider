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
public class NewsContentProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(NewsContentProcessor.class);

    private Site site;
    private static final String DOMAIN = "www.9game.cn";
    private static final String START_URL = "http://www.9game.cn/news/13_1/";
//    private static final String START_URL = "http://www.9game.cn/news/1976774.html";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";

    public void process(Page page){
        if("http://www.9game.cn/news/13_1/".equals(page.getUrl().toString())){
            List<String> urls = page.getHtml().css(".box-text .news-list-con").xpath("//a/@href").all();
            List<String> titles = page.getHtml().css(".box-text .news-list-con").xpath("//a/@title").all();
            for (int i = 0; i < urls.size(); i++) {
                System.out.println("标题:"+ titles.get(i) + " 链接:" + urls.get(i));
            }
            page.addTargetRequests(urls);
        }else{
            String title = page.getHtml().xpath("//title//text()").toString();
            String description = page.getHtml().xpath("//meta[@name='description']/@content").toString();

            String daodu = page.getHtml().css(".post-content .art-review").replace("<[^>]*>","").toString();

            String content = page.getHtml().css(".post-content p").replace("<[^>]*>","").toString();

            String imgAlt = page.getHtml().css(".post-content").xpath("//p/img/@alt").toString();
            String imgSrc = page.getHtml().css(".post-content").xpath("//p/img/@src").toString();


            System.out.println("title: " + title);
            System.out.println("description: " + description);
            System.out.println("daodu: " + daodu);
            System.out.println("content: " + content);
            System.out.println("imgAlt: " + imgAlt);
            System.out.println("imgSrc: " + imgSrc);

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
        NewsContentProcessor test = new NewsContentProcessor();
        Spider.create(test).addUrl(START_URL).thread(1).run();
    }
}
