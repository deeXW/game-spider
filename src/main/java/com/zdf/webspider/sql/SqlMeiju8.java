package com.zdf.webspider.sql;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.webspider.sql.bean.SqlMeijuttBean;

public class SqlMeiju8 implements PageProcessor{
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
		
//		List<String> links = page.getHtml().css("div.o_list_cn_r div.con4").xpath("//ul[@class='downurl']["+rowNum+"]//a/@href").all();
//		List<String> names = page.getHtml().css("div.o_list_cn_r div.con4").xpath("//ul[@class='downurl']["+rowNum+"]//a/text()").all();
		
//		System.out.println("============================================");
		System.out.println(page.getHtml().css("div.more-down-sel-box div.o_list_cn_r").all());
	    
//		System.out.println("============================================");
//		System.out.println(page.getHtml().xpath("//div[@class='o_cn2']/div[@class='o_list_cn_r'][2]").toString());;
//		System.out.println("============================================");
//		System.out.println(page.getHtml().xpath("//div[@class='o_cn2']/div[@class='o_list_cn_r'][3]").toString());
//		System.out.println("============================================");
//		System.out.println("============================================");
//		System.out.println("============================================");
//		System.out.println(page.getHtml().xpath("//div[@class='o_list_cn_r']/div[@class='con4'][1]").toString());
//		System.out.println("============================================");
//		System.out.println(page.getHtml().xpath("//div[@class='o_list_cn_r']/div[@class='con4'][2]").toString());;
//		System.out.println("============================================");
//		System.out.println(page.getHtml().xpath("//div[@class='o_list_cn_r']/div[@class='con4'][3]").toString());
//		System.out.println("============================================");
		
		List<String> links = page.getHtml().xpath("//div[@class='o_list_cn_r']/div[@class='con4']").all();
		List<String> names = page.getHtml().xpath("//div[@class='o_list_cn_r']/div[@class='con4']/["+rowNum+"]//a/text()").all();
		
//		links = page.getHtml().css("div.o_list_cn_r div.con4 div.downurl").xpath("//a[@target='_self']/@href").all();
//		names = page.getHtml().css("div.o_list_cn_r div.con4 div.downurl").xpath("//a[@target='_self']/text()").all();
//		
//		String a = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link,download_name_720,download_link_720,is_distinguish,is_subtitle,pixel,create_time,create_by,change_time,change_by) VALUES(";
//        String b = "'','','0','1','11',NOW(),'0000',NOW(),'0000');";
//		
//        System.out.println("-- " + title.replace("《", "").split("》")[0]);
//        if(links.size() > 0){
//        	for (int i = 0; i < links.size(); i++) {
//        		System.out.println(a +"'"+i+"','" + directoryId + "','"+ names.get(i) + "','"+links.get(i)+ "',"+b);
//    		}
//        }
	}


	@Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        
        List<SqlMeijuttBean> list = new ArrayList<SqlMeijuttBean>();
//    	list.add(new SqlMeijuttBean("","",""));
    	list.add(new SqlMeijuttBean("1945","http://www.meijub.com/anime/qinshimingyuezhijunlintianxia/bofang-2-0.html","1"));
    	
    	SqlMeiju8 sqlMeiju8 = new SqlMeiju8();
    	Spider spider = Spider.create(sqlMeiju8);
    	for (SqlMeijuttBean sqlMeijuttBean : list) {
    		Request request = new Request(sqlMeijuttBean.getUrl());
    		request.putExtra("directoryId", sqlMeijuttBean.getDirectoryId());
        	request.putExtra("rowNum", sqlMeijuttBean.getRowNum());
        	spider.addRequest(request);
		}
        spider.thread(1).run();
    }
}
