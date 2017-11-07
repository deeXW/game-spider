package com.zdf.webspider.sql;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.MjWebsiteConstant.Website4kba;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

@Component
public class SqlkkkkbaKKWuVideos implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlkkkkbaKKWuVideos.class);

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(300);

    private String domain = "http://www.kkkkba.com";
    @Override
    public void process(Page page) {
    	List<String> modulelinks = page.getHtml().css("#detail-list").xpath("//div[@class='play-list-box']").all();
    	String directoryId = (String) page.getRequest().getExtra("directoryId");
    	String mouleType = (String) page.getRequest().getExtra("mouleType");
    	
    	if(!page.getUrl().toString().contains("html")){
    		String title = page.getHtml().css("title").regex("《.*》").toString();
    		logger.info("--"+title);
    		for (String modulelink : modulelinks) {
        		if(modulelink.contains(mouleType)){
        			List<String> links = new Html(modulelink).css("div.content").xpath("//a/@href").all();
        			List<String> names = new Html(modulelink).css("div.content").xpath("//a/text()").all();
        			
        			Request request = null;
        			for (int i = links.size() - 1; i > -1 ; i--) {
//        				logger.info(names.get(i) + " -- "+ domain + links.get(i));
        				request = new Request(domain + links.get(i));
        				request.putExtra("mouleType", mouleType);
        				request.putExtra("directoryId", directoryId);
        				request.putExtra("indexJi", names.get(i).replace("第", ""));
        				page.addTargetRequest(request);
    				}
        		}
    		}
    	}else{
    		String indexJi = (String) page.getRequest().getExtra("indexJi");
    		String directoryId_in = (String) page.getRequest().getExtra("directoryId");
    		String mouleType_in = (String) page.getRequest().getExtra("mouleType");
    		String type = MjWebsiteConstant.get4kbaTypeByCode(mouleType_in);
    		
    		String src = page.getHtml().css("#play-focus").xpath("//iframe/@src").toString();
    		src = src.substring(0,src.indexOf(MjWebsiteConstant.get4kbaSubByCode(mouleType_in)));
    		
    		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_url,video_name,create_time,create_by)"; 

			String sqlret = sql + "VALUES (" + indexJi.replace("集", "") +","+directoryId_in+", '"+type+"', '"+src+"', '"+src.replace("player3", "player5")+"', '"+indexJi + "', NOW(), 'admin');";
    		logger.info(sqlret);
    	}

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	//播放连接列表页
    	Request request = new Request("http://www.kkkkba.com/Europe/shandianxiadisanji/");
    	request.putExtra("directoryId", "259");
//    	request.putExtra("mouleType", Website4kba.KKKK_YOUYUN.getCode());
    	request.putExtra("mouleType", Website4kba.KKKK_ACFUN.getCode());
    	
    	SqlkkkkbaKKWuVideos sqlkkkkbaVideos = new SqlkkkkbaKKWuVideos();
        Spider spider = Spider.create(sqlkkkkbaVideos);
        spider.thread(1).addRequest(request).run();
    }
}
