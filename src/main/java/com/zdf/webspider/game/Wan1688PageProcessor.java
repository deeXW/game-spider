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
 * Created by cheguo on 2017/4/7.
 */
public class Wan1688PageProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(Wan1688PageProcessor.class);
    private ImgUrlBlockQueen imgUrlBlockQueen = new ImgUrlBlockQueen();

    private Site site;
    private static final String DOMAIN = "www.1688wan.com";
    private static final String HTTP = "http://www.1688wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";

//    Wan1688ContentProcessor wan1688ContentProcessor = new Wan1688ContentProcessor();

    public void downloadImgThread(){
        int threadCount = 2;
        //共享资源
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        //添加生产者和消费者
        for(int i = 0 ; i < threadCount; i ++){
            pool.execute(new DownloadImgConsumer(imgUrlBlockQueen));
        }

//        pool.shutdown();
    }
    public void process(Page page){

        if(page.getUrl().toString().lastIndexOf(".html") == -1){
            //获取列表信息
            List<String> contentUrlList = page.getHtml().css(".font-hide").xpath("//a/@href").all();

            for (String contentUrl : contentUrlList) {
                System.out.println(contentUrl);
                //Spider.create(wan1688ContentProcessor).addUrl(contentUrl).thread(1).run();
                page.addTargetRequest(contentUrl);
            }
            System.out.println("===========================================");


            //获取下一页链接
//        List<String> nextTextList = page.getHtml().css(".page_box").xpath("//a[@class='a1']/text()").all();
//        List<String> nextUrlList = page.getHtml().css(".page_box").xpath("//a[@class='a1']/@href").all();
//        for (int i = 0; i < nextTextList.size(); i++) {
//            if(nextTextList.get(i).trim().contains("下一页")){
//                System.out.println(nextUrlList.get(i));
//                page.addTargetRequest(nextUrlList.get(i));
//            }
//        }
        }else{
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

    }

    public Site getSite(){
        if(site == null){
            site = Site.me().setDomain(DOMAIN)
                    .setSleepTime(500)
                    .setUserAgent(UA)
                    .addHeader("Cookie","UM_distinctid=15fc266ec42698-04937d11d6c8e6-c303767-232800-15fc266ec439f1; JSESSIONID=FAC23C764A4BDA402512132AFF7B5EB4; CNZZDATA1254642433=769858982-1510791992-%7C1510791992; CNZZDATA1252898618=861325848-1510794143-%7C1510794143");
        }
        return site;
    }

    public static void main(String[] args) {
        Wan1688PageProcessor test = new Wan1688PageProcessor();
        test.downloadImgThread();
        Spider.create(test).addUrl("http://www.1688wan.com/news/").thread(1).run();

        try {
            Thread.sleep(60000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}