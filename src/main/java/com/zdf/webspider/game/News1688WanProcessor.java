package com.zdf.webspider.game;

import com.zdf.thread.DownloadImgConsumer;
import com.zdf.thread.ImgUrlBlockQueen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhongdifeng on 2017-11-15.
 */
public class News1688WanProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(News1688WanProcessor.class);
    private ImgUrlBlockQueen imgUrlBlockQueen = new ImgUrlBlockQueen();
    private Site site;
    private static final String DOMAIN = "http://www.1688wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";


    public void testThread(){
        //共享资源
        ExecutorService pool = Executors.newFixedThreadPool(100);

        //添加生产者和消费者
        for(int i = 0 ; i < 10; i ++){
            pool.execute(new DownloadImgConsumer(imgUrlBlockQueen));
        }

        pool.shutdown();
    }

    public void process(Page page){

        String title = page.getHtml().css(".t1_newsshow").xpath("//h1/text()").toString();
        String editor = "dee";
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

//        String content = page.getHtml().css(".show_content").toString();
        String content = page.getHtml().xpath("//div[@class='show_content']").
                replace("<div[^>]*>","").replace("</div[^>]*>","").toString();
        List<String> imgUrls = page.getHtml().xpath("//div[@class='show_content']").xpath("//img/@src").all();

        for (String imgUrl : imgUrls) {
            System.out.println(imgUrl);
            imgUrlBlockQueen.addImgUrl(imgUrl);
        }



//        if("http://www.9game.cn/news/13_1/".equals(page.getUrl().toString())){
//            List<String> urls = page.getHtml().css(".box-text .news-list-con").xpath("//a/@href").all();
//            List<String> titles = page.getHtml().css(".box-text .news-list-con").xpath("//a/@title").all();
//            for (int i = 0; i < urls.size(); i++) {
//                System.out.println("标题:"+ titles.get(i) + " 链接:" + urls.get(i));
//            }
//            page.addTargetRequests(urls);
//        }else{
//            String title = page.getHtml().xpath("//title//text()").toString();
//            String description = page.getHtml().xpath("//meta[@name='description']/@content").toString();
//
//            String daodu = page.getHtml().css(".post-content .art-review").replace("<[^>]*>","").toString();
//
//            String content = page.getHtml().css(".post-content p").replace("<[^>]*>","").toString();
//
//            String imgAlt = page.getHtml().css(".post-content").xpath("//p/img/@alt").toString();
//            String imgSrc = page.getHtml().css(".post-content").xpath("//p/img/@src").toString();
//
//
//            System.out.println("title: " + title);
//            System.out.println("description: " + description);
//            System.out.println("daodu: " + daodu);
//            System.out.println("content: " + content);
//            System.out.println("imgAlt: " + imgAlt);
//            System.out.println("imgSrc: " + imgSrc);
//
//        }
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
        News1688WanProcessor test = new News1688WanProcessor();
        test.testThread();
        Spider.create(test).addUrl("http://www.1688wan.com/news/zixun/201711/info_194286.html").thread(1).run();
    }
}
