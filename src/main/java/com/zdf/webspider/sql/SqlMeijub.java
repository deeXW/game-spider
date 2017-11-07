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

public class SqlMeijub implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlMeijub.class);

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
		//这些 code从列表也获取
//		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("第1集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjQ2MA==");
		map.put("第2集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjQ1Ng==");
		map.put("第3集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjQ0NA==");
		map.put("第4集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjgwOA==");
		map.put("第5集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjg2NA==");
		map.put("第6集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMzgwMA==");
		map.put("第7集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMzgyNA==");
		map.put("第8集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjQ0OA==");
		map.put("第9集","https://hi.dcfcv.com/nyk/acfun.php?id=CODcyMjQ1Mg==");
		map.put("第10集","https://hi.dcfcv.com/nyk/acfun.php?id=CODk3NTUzNg==");
    	
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_name,create_time,create_by)"; 
		Pattern pattern = Pattern.compile("url.php\", (.+?)\\},");
		JSONObject json = new JSONObject();
		int i = 0;
		long directoryId = 2171l;
		for (String key : map.keySet()) {
			i++;
			StringBuffer sb = new StringBuffer();
			String html = HttpTookit.doGetReferer(map.get(key), null, "http://meijub.bceapp.com", true);
			
	    	Matcher m = pattern.matcher(html);
	    	String post = null;
	    	if(m.find()) {
	    		post = m.group(1);
	    	}
	    	Map<String,String> mapJson = json.parseObject(post+"}", Map.class);
	    	
	    	for (String keyJson : mapJson.keySet()) {
	        	sb.append(keyJson+"="+mapJson.get(keyJson)+"&");
	        }
	    	String url = "https://hi.dcfcv.com/nyk/url.php?"+sb.toString().substring(0,sb.toString().length()-1);
//	    	System.out.println(url);
	    	String sqlret = sql + "VALUES (" + i +","+directoryId+", 'meijub_new', '"+url+"', '"+key+"', NOW(), 'admin');";
	    	System.out.println(sqlret);
        }
		
    }
}
