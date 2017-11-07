package com.zdf.webspider.sql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.webspider.sql.bean.SqlMeijuttBean;

@Component
public class SqlMeijutt implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.meijutt.com")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    @Override
    public void process(Page page) {
    	String directoryId = (String) page.getRequest().getExtra("directoryId");
    	//0表示第一个模块，1表示第二个模块
        String rowNum = (String) page.getRequest().getExtra("rowNum");
        
        String name = page.getHtml().xpath("//*[@class=info-title]/h1/text()").toString();
        //可以获取到第一个class数据
        List<String> names = page.getHtml()
            .css("div.down_list ul li input[name=down_url_list_"+rowNum+"] + p").xpath("//a/text()").all();

        List<String> links = page.getHtml()
            .css("div.down_list ul li input[name=down_url_list_"+rowNum+"] + p").xpath("//a/@href").all();

        String a = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link,download_name_720,download_link_720,is_distinguish,is_subtitle,pixel,create_time,create_by,change_time,change_by) VALUES(";
        String b = "'','','0','1','11',NOW(),'0000',NOW(),'0000');";
		
        System.out.println("-- " + name);
        if(links.size() > 0){
        	for (int i = 0; i < links.size(); i++) {
        		System.out.println(a + (i+1) +",'" + directoryId + "','"+ names.get(i) + "','"+links.get(i)+ "',"+b);

        		if(names.get(i).toUpperCase().contains("END")){
                    System.out.println("-- 已剧终！！！");
                }
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
    	list.add(new SqlMeijuttBean("2391","http://www.meijutt.com/content/meiju22778.html","0"));
    	
    	SqlMeijutt meijuttSql = new SqlMeijutt();
    	Spider spider = Spider.create(meijuttSql);
    	for (SqlMeijuttBean sqlMeijuttBean : list) {
    		Request request = new Request(sqlMeijuttBean.getUrl());
    		request.putExtra("directoryId", sqlMeijuttBean.getDirectoryId());
        	request.putExtra("rowNum", sqlMeijuttBean.getRowNum());
        	spider.addRequest(request);
		}
        spider.thread(1).run();
    	
    	
    	//Request request = new Request("http://www.meijutt.com/content/meiju19794.html");
    	//request.putExtra("directoryId", "1589");
    	//request.putExtra("rowNum", "1");
    	//SqlMeijutt meijuttSql = new SqlMeijutt();
    	//http://cn163.net/archives/17663/
        //Spider spider = Spider.create(meijuttSql);
        //spider.thread(1).addRequest(request).run();
        
//        Spider spider = Spider.create(new MeijuttSql());
//        spider.addUrl("http://www.meijutt.com/content/meiju320.html")
//              .thread(1).run();
    }

}
