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
public class GamePageProcessor implements PageProcessor {

    private static Logger logger = LoggerFactory.getLogger(GamePageProcessor.class);

    private Site site;
    private static final String DOMAIN = "www.9game.cn";
    private static final String HTTP = "http://www.444wan.com";
    private static final String UA = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";

    GameContentProcessor gameContentProcessor = new GameContentProcessor();

    public void process(Page page){

        //获取列表信息
        List<String> softList = page.getHtml().css(".soft_list .sl_name").xpath("//a/@href").all();

        for (String string : softList) {

//            System.out.println(string);

            Spider.create(gameContentProcessor).addUrl(HTTP + string).thread(1).run();

        }
        System.out.println("===========================================");


        //获取游戏内容也数据


        //获取下一页链接
        List<String> nextTextList = page.getHtml().css(".page_box").xpath("//a[@class='a1']/text()").all();
        List<String> nextUrlList = page.getHtml().css(".page_box").xpath("//a[@class='a1']/@href").all();
        for (int i = 0; i < nextTextList.size(); i++) {
            if(nextTextList.get(i).trim().contains("下一页")){
                System.out.println(nextUrlList.get(i));
                page.addTargetRequest(nextUrlList.get(i));
            }
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
        GamePageProcessor test = new GamePageProcessor();
        Spider.create(test).addUrl("http://www.444wan.com/shouji/saiche/").thread(1).run();
    }
}
