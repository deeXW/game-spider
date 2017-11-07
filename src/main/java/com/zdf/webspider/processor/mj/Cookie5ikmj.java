package com.zdf.webspider.processor.mj;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.util.ReadFromFile;

public class Cookie5ikmj implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(300)
            .setDomain("www.zimuzu.tv")
            .addCookie("CNZZDATA1254180690", "843937239-1465709399-%7C1469696997")
            .addCookie("FTAPI_BLOCK_SLOT", "FUCKIE")
            .addCookie("FTAPI_PVC", "1012026-1-ipcppoxc")
            .addCookie("FTAPI_ST", "FUCKIE")
            .addCookie("GINFO", "uid%3D4387036%26nickname%3Dwoikmj%26group_id%3D1%26avatar_t%3D%26main_group_id%3D0%26common_group_id%3D53")
            .addCookie("GKEY", "637fd05b12abd25fe918965d9051956a")
            .addCookie("PHPSESSID", "jshfpbg8atdo1ki3ss88hrc9v1")
            .addCookie("cps", "suning%2F1469696986%3Byhd%2F1469696996%3Btujia%2F1469697114%3Bwomai%2F1467874961")
            .addCookie("ctrip", "ctrip%2F1469696986")
            .addCookie("mykeywords", "a%3A1%3A%7Bi%3A0%3Bs%3A9%3A%22%E6%9C%AB%E6%97%A5%E5%AD%A4%22%3B%7D")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
            .addHeader("AlexaToolbar-ALX_NS_PH", "AlexaToolbar/alx-4.0")
            .addHeader("Cookie", "FTAPI_BLOCK_SLOT=FUCKIE; FTAPI_ST=FUCKIE; FTAPI_PVC=1012026-1-ipcppoxc; mykeywords=a%3A1%3A%7Bi%3A0%3Bs%3A9%3A%22%E6%9C%AB%E6%97%A5%E5%AD%A4%22%3B%7D; PHPSESSID=jshfpbg8atdo1ki3ss88hrc9v1; GINFO=uid%3D4387036%26nickname%3Dwoikmj%26group_id%3D1%26avatar_t%3D%26main_group_id%3D0%26common_group_id%3D53; GKEY=637fd05b12abd25fe918965d9051956a; ctrip=ctrip%2F1469696986; cps=suning%2F1469696986%3Byhd%2F1469696996%3Btujia%2F1469697114%3Bwomai%2F1467874961; CNZZDATA1254180690=843937239-1465709399-%7C1469696997")
            .addHeader("Host", "www.zimuzu.tv")
                                //            .addHeader("Referer", "http://www.zimuzu.tv/resource/32138")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml()
            .css("div.media-box div.media-list ul li[season=3][format=HDTV]")
            .xpath("//a/@href").all();
        for (String string : links) {
            System.out.println(string);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	Spider spider = Spider.create(new Cookie5ikmj());
        
        String fileName = "D:/4000.txt";
        List<String> list = ReadFromFile.readFileByLinesList(fileName);
        for (String string : list) {
        	spider.addUrl(string);
        }
        spider.thread(1).run();
    }
}
