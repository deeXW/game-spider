package com.zdf.webspider.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.webspider.sql.bean.SqlMeijuttBean;

public class SqlMeijuck implements PageProcessor{
	private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
			.setDomain("www.meijuck.com")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
    private final Logger             logger = Logger.getLogger(SqlMeijuck.class);
    
    
	@Override
	public void process(Page page) {
		//1表示第一个模块，2表示第二个模块
        String rowNum = (String) page.getRequest().getExtra("rowNum");
		String directoryId = (String) page.getRequest().getExtra("directoryId");
		
		String title = page.getHtml().xpath("//title/text()").toString();
		List<String> links = page.getHtml().css("div.content").xpath("//ol[@class='list-paddingleft-2']["+rowNum+"]//a/@href").all();
		List<String> names = page.getHtml().css("div.content").xpath("//ol[@class='list-paddingleft-2']["+rowNum+"]//a/text()").all();
		
		links = page.getHtml().css("div.content article.article-content p").xpath("//a[@target='_self']/@href").all();
		names = page.getHtml().css("div.content article.article-content p").xpath("//a[@target='_self']/text()").all();
		
		String a = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link,download_name_720,download_link_720,is_distinguish,is_subtitle,pixel,create_time,create_by,change_time,change_by) VALUES(";
        String b = "'','','0','1','11',NOW(),'0000',NOW(),'0000');";
		
        System.out.println("-- " + title.replace("《", "").split("》")[0]);
        if(links.size() > 0){
        	for (int i = 0; i < links.size(); i++) {
        		System.out.println(a +"'"+i+"','" + directoryId + "','"+ names.get(i) + "','"+links.get(i)+ "',"+b);
    		}
        }
	}


	@Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        
        List<SqlMeijuttBean> list = new ArrayList<SqlMeijuttBean>();
//    	list.add(new SqlMeijuttBean("","",""));
    	list.add(new SqlMeijuttBean("1945","http://www.meijuck.com/lyjs/2250.html","1"));
    	
    	SqlMeijuck sqlMeijuck = new SqlMeijuck();
    	Spider spider = Spider.create(sqlMeijuck);
    	for (SqlMeijuttBean sqlMeijuttBean : list) {
    		Request request = new Request(sqlMeijuttBean.getUrl());
    		request.putExtra("directoryId", sqlMeijuttBean.getDirectoryId());
        	request.putExtra("rowNum", sqlMeijuttBean.getRowNum());
        	spider.addRequest(request);
		}
        spider.thread(1).run();
    }
}
