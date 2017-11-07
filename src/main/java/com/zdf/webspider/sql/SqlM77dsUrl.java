package com.zdf.webspider.sql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSONObject;
import com.zdf.util.HttpTookit;

public class SqlM77dsUrl implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlM77dsUrl.class);

    private StringBuffer             sb     = null;

    private static final String DOMAIN = "http://m.77ds.net";

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("m.77ds.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) { }

	@Override
	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("1集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQzNg==");
		map.put("2集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQzMg==");
		map.put("3集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQyOA==");
		map.put("4集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQyNA==");
		map.put("5集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQyMA==");
		map.put("6集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODE3NDk2OA==");
		map.put("7集","https://api.niubaitu.com/youkuvip.php?id=XMTgzODAxNjQxNg==");
		map.put("8集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjYxNjA4NA==");
		map.put("9集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjYyNzE0NA==");
		map.put("10集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjYzNzg3Mg==");
		map.put("11集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjY0NzIwOA==");
		map.put("12集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjY1NTU5Mg==");
		map.put("13集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjE3ODc2MA==");
		map.put("14集","https://api.niubaitu.com/youkuvip.php?id=XMTg0NjIxODgzMg==");
		map.put("15集","https://api.niubaitu.com/youkuvip.php?id=XMTg1Mzg2NDg0OA==");
		map.put("16集","https://api.niubaitu.com/youkuvip.php?id=XMTg1MzkwMDQ0NA==");
		map.put("17集","https://api.niubaitu.com/youkuvip.php?id=XMTg1MzkzMjkwOA==");
		map.put("18集","https://api.niubaitu.com/youkuvip.php?id=XMTg1Mzk2MTA0MA==");
		map.put("19集","https://api.niubaitu.com/youkuvip.php?id=XMTg1Mzk4NjQ4OA==");
		map.put("20集","https://api.niubaitu.com/youkuvip.php?id=XMTg1NDAxMDA5Ng==");
		map.put("21集","https://api.niubaitu.com/youkuvip.php?id=XMTg1NDAzMzEzMg==");
		map.put("22集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NTYzMTczMg==");
		map.put("23集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NTk0NjQ2NA==");
		map.put("24集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NTk5MzY0NA==");
		map.put("25集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NjA0NDEwNA==");
		map.put("26集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NjE4NjUyMA==");
		map.put("27集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NjIzNDIwNA==");
		map.put("28集","https://api.niubaitu.com/youkuvip.php?id=XMTg2NjMxMjk4OA==");
    	
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_name,create_time,create_by)"; 
		Pattern pattern = Pattern.compile("url.php\", (.+?)\\},");
		JSONObject json = new JSONObject();
		int i = 0;
		long directoryId = 2167l;
		for (String key : map.keySet()) {
			i++;
			StringBuffer sb = new StringBuffer();
			String html = HttpTookit.doGetReferer(map.get(key), null, "https://api.niubaitu.com", true);
			
	    	Matcher m = pattern.matcher(html);
	    	String post = null;
	    	if(m.find()) {
	    		post = m.group(1);
	    	}
	    	Map<String,String> mapJson = json.parseObject(post+"}", Map.class);
	    	
	    	for (String keyJson : mapJson.keySet()) {
	        	sb.append(keyJson+"="+mapJson.get(keyJson)+"&");
	        }
	    	String url = "https://api.niubaitu.com/url.php?"+sb.toString().substring(0,sb.toString().length()-1);
//	    	System.out.println(url);
	    	String sqlret = sql + "VALUES (" + i +","+directoryId+", 'm77ds_new', '"+url+"', '"+key+"', NOW(), 'admin');";
	    	System.out.println(sqlret);
        }
		
    }
}
