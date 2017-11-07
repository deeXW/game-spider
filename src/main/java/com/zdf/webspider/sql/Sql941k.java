package com.zdf.webspider.sql;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.util.EscapeUnescape;
import com.zdf.util.MjWebsiteConstant;

public class Sql941k implements PageProcessor {
    private final Logger             logger = Logger.getLogger(Sql941k.class);

    private StringBuffer             sb     = null;


    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.941k.win")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

	@Override
	public void process(Page page) {
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,video_url,video_name,create_time,create_by)"; 
		
		String directoryId = (String) page.getRequest().getExtra("directoryId");
		//美剧片名
		String title = page.getHtml().css("title").regex("《.*》").toString();
		
		String date = page.getHtml().css("div.player div.main").regex("\\(.*)\\").toString();
		if(null != date && !"".equals(date)){
			date = date.replace("(", "").replace("'", "");
			date = EscapeUnescape.unescape(date);
		}
		//总的集数
		String[] totalJi = date.split("#");
		
		System.out.println("-- " + title);
		String sjUrl = "http://q673815913.pw/kx.php?vid=%s&type=mp4";
		for (int i = 0; i < totalJi.length; i++) {
			String[] res = totalJi[i].split("\\$");
			String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.J941K+"', '"+String.format(sjUrl, res[1])+"', '"+res[0].replace("第", "")+"', NOW(), 'admin');";
			System.out.println(sqlret);
		}
		
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
//    	map.put("", "");
    	map.put("2144", "http://www.941k.win/?m=vod-play-id-1250-src-1-num-10.html");
    	
    	Sql941k sql941k = new Sql941k();
    	Spider spider = Spider.create(sql941k);
    	for (Map.Entry<String,String> entry : map.entrySet()) {  
    		Request request = new Request(entry.getValue());
        	request.putExtra("directoryId", entry.getKey());
        	spider.addRequest(request);
        } 
        spider.thread(1).run();
        
    }
}
