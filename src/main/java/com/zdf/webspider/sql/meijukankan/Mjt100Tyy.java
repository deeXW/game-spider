package com.zdf.webspider.sql.meijukankan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSONObject;
import com.zdf.bean.mj.MjDirectory;
import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.service.mj.MjDirectoryService;
import com.zdf.service.mj.MjDirectoryVideoService;
import com.zdf.util.HttpTookit;
import com.zdf.util.MjWebsiteConstant;
import com.zdf.webspider.processor.gj.GjM77dsNewProcessor;

public class Mjt100Tyy implements PageProcessor {
    private final Logger             logger = Logger.getLogger(GjM77dsNewProcessor.class);

    
    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
    
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.mjt100.com");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String sql = "UPDATE mj_directory_video set type='mjt_mp4',org_url='";
		Long directoryId = (Long)page.getRequest().getExtra("directoryId");
		Map mapExt = (Map)page.getRequest().getExtra("mapExt");
		String cs = page.getHtml().regex("VideoInfoList=\"(.+?)\"").toString();
		String[] css = cs.split("\\$\\$\\$");
		System.out.println("-- " + directoryId);
		for (String string : css) {
			if(string.contains((String)mapExt.get("code"))){
				String[] csss = string.split("\\$\\$");
				String[] cssss = csss[1].split("\\#");
				for (String string2 : cssss) {
					String[] lasts = string2.split("\\$");
//					System.out.println(lasts[1]);
					
					System.out.println(sql + lasts[1] +"' WHERE type = 'meijuhui' and directory_id ="+directoryId + " and video_name = '"+lasts[0].replace("第", "")+"';");
				}
			}
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	
	public static void main(String[] args) {
		
		Map<Long,String> map = new HashMap<Long,String>();
		map.put(796l,	"http://www.mjt100.com/mjtj/2401/play-4-0.html");
		map.put(797l,	"http://www.mjt100.com/mjtj/2402/play-4-0.html");
		map.put(798l,	"http://www.mjt100.com/mjtj/2403/play-4-0.html");
		map.put(799l,	"http://www.mjt100.com/mjtj/2404/play-4-0.html");
		map.put(800l,	"http://www.mjt100.com/mjtj/2405/play-4-0.html");
		map.put(801l,	"http://www.mjt100.com/mjtj/2406/play-4-0.html");
		map.put(802l,	"http://www.mjt100.com/mjtj/2407/play-4-0.html");

    	
    	Map<String,String> mapExt = new HashMap<String,String>();
    	mapExt.put("code", "tyy");
    	
    	Mjt100Tyy sqlM77dsNew = new Mjt100Tyy();
    	Spider spider = Spider.create(sqlM77dsNew);
    	for (Map.Entry<Long,String> entry : map.entrySet()) {  
    		Request request = new Request(entry.getValue());
        	request.putExtra("directoryId", entry.getKey());
        	request.putExtra("mapExt", mapExt);
        	spider.addRequest(request);
        } 
        spider.thread(1).run();
        
        
	}
	
}
