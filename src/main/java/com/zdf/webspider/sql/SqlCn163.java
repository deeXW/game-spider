package com.zdf.webspider.sql;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.mail.JavaMail;
import com.zdf.service.mj.MeijuUpdateNoticeService;

@Component
public class SqlCn163 implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlCn163.class);

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(300);

    @Autowired
	private MeijuUpdateNoticeService meijuUpdateNoticeService;

    @Override
    public void process(Page page) {
        //可以获取到第一个class数据
        List<String> names = page.getHtml().css("div.entry").xpath("//a/text()").all();
        List<String> links = page.getHtml().css("div.entry").xpath("//a/@href").all();
        String a = "INSERT INTO mj_directory_links(directory_id,download_name,download_link,download_name_720,download_link_720,is_distinguish,is_subtitle,pixel,create_time,create_by,change_time,change_by) VALUES(";
        String b = "'','','0','1','11',NOW(),'0000',NOW(),'0000');";
		
        String directoryId = (String) page.getRequest().getExtra("directoryId");
        if(names.size() > 0){
        	for (int i = 0; i < names.size(); i++) {
        		System.out.println(a +"'" + directoryId + "','"+ names.get(i) + "','"+links.get(i)+ "',"+b);
    		}
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	Request request = new Request("http://cn163.net/archives/2385/");
    	request.putExtra("directoryId", "406");
    	
    	SqlCn163 cn163Sql = new SqlCn163();
    	//http://cn163.net/archives/17663/
        Spider spider = Spider.create(cn163Sql);
        spider.thread(1).addRequest(request).run();
    }
}
