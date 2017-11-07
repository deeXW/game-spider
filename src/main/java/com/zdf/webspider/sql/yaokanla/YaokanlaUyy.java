package com.zdf.webspider.sql.yaokanla;

import com.zdf.webspider.processor.gj.GjM77dsNewProcessor;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class YaokanlaUyy implements PageProcessor {
    private final Logger             logger = Logger.getLogger(YaokanlaUyy.class);

    
    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
    
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("yaokanla.com");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String sql = "UPDATE mj_directory_video set type='iframe',org_url='http://jiexi.112509.top/uyy.php?url=";
		Long directoryId = (Long)page.getRequest().getExtra("directoryId");
		Map mapExt = (Map)page.getRequest().getExtra("mapExt");

		//正则解析
		String cs = page.getHtml().regex("mac_url='(.+?)';").toString();
		String[] css = cs.split("#");
		System.out.println("-- " + directoryId);

		for (int i = 0; i < css.length; i++) {
			try {
				String url = URLDecoder.decode(css[i],"utf-8");
//				System.out.println(url);
				System.out.println(sql + url +"&type=syun' WHERE type = 'yaokanla' and directory_id ="+directoryId + " and video_name = '"+ (i + 1) +"集';");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		for (String string : css) {

		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	
	public static void main(String[] args) {
		
		Map<Long,String> map = new HashMap<Long,String>();
		map.put(1962l,	"http://yaokanla.com/vod-play-id-26504-src-1-num-1.html");
    	
    	Map<String,String> mapExt = new HashMap<String,String>();
    	mapExt.put("code", "tyy");
    	
    	YaokanlaUyy sqlM77dsNew = new YaokanlaUyy();
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
