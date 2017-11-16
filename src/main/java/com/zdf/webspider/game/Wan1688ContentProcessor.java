package com.zdf.webspider.game;

import com.zdf.thread.DownloadImgConsumer;
import com.zdf.thread.ImgUrlBlockQueen;
import com.zdf.util.URLConnectionDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhongdifeng on 2017-11-15.
 */
public class Wan1688ContentProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(Wan1688ContentProcessor.class);
    private ImgUrlBlockQueen imgUrlBlockQueen = new ImgUrlBlockQueen();
    private Site site;
    private static final String DOMAIN = "http://www.1688wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";


    public void downloadImgThread(){
        int threadCount = 2;
        //共享资源
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        //添加生产者和消费者
        for(int i = 0 ; i < threadCount; i ++){
            pool.execute(new DownloadImgConsumer(imgUrlBlockQueen));
        }

        pool.shutdown();
    }

    public Wan1688ContentProcessor() {
    }

    public void process(Page page){
        String title = page.getHtml().css(".t1_newsshow").xpath("//h1/text()").toString();
        String editor = "dee";
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String contentClass = "//div[@class='show_content']";
        Selectable selectable = page.getHtml().xpath(contentClass);
        if(null == selectable.toString() || "".equals(selectable.toString())){
            contentClass = "//div[@id='wz-content']";
            selectable = page.getHtml().xpath(contentClass);
        }
        String content = selectable.replace("<div[^>]*>","").replace("</div[^>]*>","").toString();
        List<String> imgUrls = selectable.xpath(contentClass).xpath("//img/@src").all();

        for (String imgUrl : imgUrls) {
            System.out.println(imgUrl);
            System.out.println(URLConnectionDownloader.getSuffix(imgUrl));
            imgUrlBlockQueen.addImgUrl(imgUrl);
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
        Wan1688ContentProcessor test = new Wan1688ContentProcessor();
        Spider.create(test).addUrl("http://www.1688wan.com/news/zixun/201711/info_194293.html").thread(1).run();
    }
}
