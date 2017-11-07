package com.zdf.webspider.sql.meijukankan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class SqlMeijuhui implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.meijukankan.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
    private final Logger             logger = Logger.getLogger(SqlMeijuhui.class);
    @Override
    public void process(Page page) {
    	String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_url,video_name,create_time,create_by)"; 
    	if(page.getUrl().toString().contains("detail")){
    		String directoryId = (String) page.getRequest().getExtra("directoryId");
    		String title = page.getHtml().css(".pfen").xpath("//H4/text()").toString();
			List<String> links = page.getHtml().css("#qiyi_con").xpath("//a[contains(@href, 'player')]/@href").all();
			List<String> names = page.getHtml().css("#qiyi_con").xpath("//a[contains(@href, 'player')]/em[@class='num']/text()").all();
			Request request = null;
			for (int i = 0; i < names.size(); i++) {
				request = new Request(links.get(i).replace("..", "http://www.meijukankan.net"));
				if(i == 0){
					request.putExtra("title", title);
				}
				request.putExtra("name", names.get(i));
				request.putExtra("directoryId", directoryId);
				request.putExtra("sort", i);
				page.addTargetRequest(request);
			}
		}else if(page.getUrl().toString().contains("player")){
//	    	String url =page.getHtml().regex(".*f : '(.*?)'.*").toString().replace("/geth5", "http://www.meijukankan.net/geth5").replace("->ajax/get/utf-8", "");
//	    	String url =page.getHtml().regex("video = \\[ '(.+?)'").toString().replace("/geth5", "http://www.meijukankan.net/geth5").replace("->ajax/get/utf-8", "");
	    	String type = "meijuhui";
			String url =page.getHtml().regex("video = \\[ '(.+?)'").toString();
			if(null==url || "".equals(url)){
	    		type = "iframe";
	    		url = page.getHtml().regex("var vid='(.+?)';").toString();
	    		url = "http://www.mjt100.com/C/index.php?vid=" + url;
	    	}else{
	    		url =url.replace("/geth5", "http://www.meijukankan.net/geth5").replace("->ajax/get/utf-8", "");
	    	}
			
	    	String title = (String) page.getRequest().getExtra("title");
	    	String name = (String) page.getRequest().getExtra("name");
	    	String directoryId = (String) page.getRequest().getExtra("directoryId");
	    	Integer sort = (Integer) page.getRequest().getExtra("sort");
//	    	Request request = new Request(url);
//	    	request.putExtra("name", name);
//	    	request.putExtra("orgUrl", url);
//	    	request.putExtra("directoryId", directoryId);
	    	//page.addTargetRequest(request);
	    	if(title != null && !"".equals(title)){
	    		System.out.println("-- "+title);
	    	}
	    	
	    	sql += "VALUES (" + sort +","+directoryId+", '"+type+"', '"+url+"', '', '"+name+"', NOW(), 'admin');";
	    	System.out.println(sql);
//	    	logger.info(sql);
		}
//		else {
//			String name = (String) page.getRequest().getExtra("name");
//			String orgUrl = (String) page.getRequest().getExtra("orgUrl");
//			String directoryId = (String) page.getRequest().getExtra("directoryId");
//			sql += "VALUES ("+directoryId+", '0', '"+orgUrl+"', '"+page.getHtml().xpath("//body/text()").toString()+"', '"+name+"', NOW(), 'admin');";
//			System.out.println(sql);
//		}
	    	
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	Map<String,String> map = new HashMap<String,String>();
//    	map.put("", "");
    	map.put("1335", "http://www.meijukankan.net/detail/46-1628.html");
    	
    	SqlMeijuhui sqlMeijuhui = new SqlMeijuhui();
    	Spider spider = Spider.create(sqlMeijuhui);
    	for (Map.Entry<String,String> entry : map.entrySet()) {  
    		Request request = new Request(entry.getValue());
        	request.putExtra("directoryId", entry.getKey());
        	spider.addRequest(request);
        } 
        spider.thread(1).run();
    }
    
    
}
