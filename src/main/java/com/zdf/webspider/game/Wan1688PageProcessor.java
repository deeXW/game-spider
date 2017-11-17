package com.zdf.webspider.game;

import com.zdf.dao.dfwd.EcmsNewsDao;
import com.zdf.dao.dfwd.EcmsNewsData1Dao;
import com.zdf.dao.dfwd.EcmsNewsIndexDao;
import com.zdf.entity.dfwd.EcmsNewsData1Entity;
import com.zdf.entity.dfwd.EcmsNewsEntity;
import com.zdf.entity.dfwd.EcmsNewsIndexEntity;
import com.zdf.thread.DownloadImgConsumer;
import com.zdf.thread.ImgUrlBlockQueen;
import com.zdf.util.URLConnectionDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
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
@Component
public class Wan1688PageProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(Wan1688PageProcessor.class);
    private ImgUrlBlockQueen imgUrlBlockQueen = new ImgUrlBlockQueen();

    private Site site;
    private static final String DOMAIN = "www.1688wan.com";
    private static final String HTTP = "http://www.1688wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";

    private int pageCount = 1;

    @Autowired
    private EcmsNewsDao ecmsNewsDao;

    @Autowired
    private EcmsNewsData1Dao ecmsNewsData1Dao;

    @Autowired
    private EcmsNewsIndexDao ecmsNewsIndexDao;


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
    public void process(Page page){
        String urlType = (String)page.getRequest().getExtra("urlType");

        if("page".equals(urlType) && pageCount < 2 ){
            pageCount++;
            System.out.println("====================获取下一页链接=======================");
            //获取下一页链接
            String nextUrl = page.getHtml().css(".page-next").xpath("//a[@class='dis-b']/@href").toString();
            System.out.println(HTTP + nextUrl);
            Request request = new Request(HTTP + nextUrl);
            request.putExtra("urlType", "page");
            page.addTargetRequest(request);

            System.out.println("===================分页中的内容链接========================");
            //分页中的内容链接
            List<String> contentUrlList = page.getHtml().css(".font-hide").xpath("//a/@href").all();
            List<String> titlePicList = page.getHtml().xpath("//img[@class='lazy']/@data-url").all();
            List<String> smallTextList = page.getHtml().xpath("//li[@class='pl20']//p/text()").all();

            String titlePic = "";
            for (int i = 0; i < contentUrlList.size(); i++) {
                System.out.println(contentUrlList.get(i));

                System.out.println();
                titlePic = titlePicList.get(i);
                if(!titlePic.contains("1688wan")){
                    titlePic = HTTP + titlePic;
                }
                imgUrlBlockQueen.addImgUrl(titlePic);

                Request requestContentUrl = new Request(contentUrlList.get(i));
                requestContentUrl.putExtra("urlType", "content");
                requestContentUrl.putExtra("smallText", smallTextList.get(i).trim());
                requestContentUrl.putExtra("titlePicName", URLConnectionDownloader.getSuffix(titlePic));
                page.addTargetRequest(requestContentUrl);
            }

        }else if("content".equals(urlType)){
            String smallText = (String) page.getRequest().getExtra("smallText");
            String titlePicName = (String) page.getRequest().getExtra("titlePicName");
            String titlePicPath = "/d/file/p/wan/" + titlePicName;
            String title = page.getHtml().xpath("//title/text()").toString().replace("_1688玩手游网","");
            String keyboard = page.getHtml().xpath("//meta[@name='keywords']/@content").toString();
            String userName = "625u";
            int classId = 2;
            int haveHtml = 1;
            Date date = new Date();
            long time = date.getTime()/1000;


            String contentClass = "//div[@class='show_content']";
            Selectable selectable = page.getHtml().xpath(contentClass);
            if(null == selectable.toString() || "".equals(selectable.toString())){
                contentClass = "//div[@id='wz-content']";
                selectable = page.getHtml().xpath(contentClass);
            }
//            if(){
//                contentClass = "//div[@id='bosom_lf']";
//                selectable = page.getHtml().xpath(contentClass);
//            }

            String content = selectable.replace("<div[^>]*>","").replace("</div[^>]*>","").toString();
            List<String> imgUrls = selectable.xpath(contentClass).xpath("//img/@src").all();
            System.out.println(content);

            for (String imgUrl : imgUrls) {
                System.out.println(imgUrl);
                content = content.replace(imgUrl, "/d/file/p/wan/"+ URLConnectionDownloader.getSuffix(imgUrl));
                if(!imgUrl.contains("1688wan")){
                    imgUrl = HTTP + imgUrl;
                }
                System.out.println(URLConnectionDownloader.getSuffix(imgUrl));
                imgUrlBlockQueen.addImgUrl(imgUrl);
            }

            if(null == content || "".equals(content)){
                logger.error("content is null URL: " + page.getUrl().toString());
            }else{
                //数据新增
                EcmsNewsEntity ecmsNewsEntity = new EcmsNewsEntity();
                ecmsNewsEntity.setTitle(title);
                ecmsNewsEntity.setTruetime((int)time);
                ecmsNewsEntity.setLastdotime((int)time);
                ecmsNewsEntity.setNewstime((int)time);
                ecmsNewsEntity.setUserid(1);
                ecmsNewsEntity.setUsername(userName);
                ecmsNewsEntity.setTitlepic(titlePicPath);
                ecmsNewsEntity.setClassid(classId);
                ecmsNewsEntity.setHavehtml(haveHtml);
                ecmsNewsEntity.setKeyboard(keyboard);
                ecmsNewsEntity.setSmalltext(smallText);
                ecmsNewsEntity.setFmimg("");
                ecmsNewsDao.insert(ecmsNewsEntity);
                System.out.println(ecmsNewsEntity.getId());

                EcmsNewsEntity ecmsNewsEntityUpdate = new EcmsNewsEntity();
                ecmsNewsEntityUpdate.setId(ecmsNewsEntity.getId());
                ecmsNewsEntityUpdate.setTitleurl("/news/"+ecmsNewsEntity.getId()+".html");
                ecmsNewsEntityUpdate.setFilename(ecmsNewsEntity.getId().toString());
                int retUpdate = ecmsNewsDao.update(ecmsNewsEntityUpdate);
                System.out.println(retUpdate + "========ecmsNewsData1Dao========");

                EcmsNewsData1Entity ecmsNewsData1Entity = new EcmsNewsData1Entity();
                ecmsNewsData1Entity.setId(ecmsNewsEntity.getId());
                ecmsNewsData1Entity.setClassid(classId);
                ecmsNewsData1Entity.setInfotags(keyboard);
                content = content.trim().replace(page.getUrl().toString(),"/news/"+ecmsNewsEntity.getId()+".html");
                ecmsNewsData1Entity.setNewstext(content);
                ecmsNewsData1Entity.setWriter(userName);
                ecmsNewsData1Dao.insert(ecmsNewsData1Entity);
                System.out.println("========ecmsNewsData1Dao========");

                EcmsNewsIndexEntity ecmsNewsIndexEntity = new EcmsNewsIndexEntity();

                ecmsNewsIndexEntity.setId(ecmsNewsEntity.getId());
                ecmsNewsIndexEntity.setClassid(classId);
                ecmsNewsIndexEntity.setChecked(0);
                ecmsNewsIndexEntity.setHavehtml(haveHtml);
                ecmsNewsIndexEntity.setNewstime((int)time);
                ecmsNewsIndexEntity.setTruetime((int)time);
                ecmsNewsIndexEntity.setLastdotime((int)time);
                ecmsNewsIndexDao.insert(ecmsNewsIndexEntity);
                System.out.println("========ecmsNewsIndexDao========");
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

    public void handleLinks(Wan1688PageProcessor wan1688PageProcessor){
        wan1688PageProcessor.downloadImgThread();
        Request request = new Request("http://www.1688wan.com/news/");
        request.putExtra("urlType", "page");
        Spider.create(wan1688PageProcessor).addRequest(request).thread(1).run();
    }

    public static void main(String[] args) {
        Wan1688PageProcessor test = new Wan1688PageProcessor();
        test.downloadImgThread();
        Request request = new Request("http://www.1688wan.com/news/");
        request.putExtra("urlType", "page");
        Spider.create(test).addRequest(request).thread(1).run();

    }
}
