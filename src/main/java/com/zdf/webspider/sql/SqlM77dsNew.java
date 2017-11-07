package com.zdf.webspider.sql;

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

public class SqlM77dsNew implements PageProcessor {
    private final Logger             logger = Logger.getLogger(GjM77dsNewProcessor.class);

    
    
    private static final String DOMAIN = "http://m.77ds.net";

    private Map<Long,Long> maxSortMap = new HashMap<Long,Long>();
    
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("m.77ds.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,org_url,video_name,create_time,create_by)"; 
		
		
		if(page.getUrl().toString().contains("zaixianbofang")){
			Map mapExt = (Map)page.getRequest().getExtra("mapExt");
			Long directoryId = (Long) page.getRequest().getExtra("directoryId");
			//美剧片名
			String title = page.getHtml().css("title").regex("《.*》").toString();
			
			//****************** ykyun获取方式 ******************
			//获取集数名称
			List<String> titleList = page.getHtml().css("div#playbox p."+mapExt.get("code")+" + div").xpath("//a/@title").all();
			//获取js信息
			String jsPath = page.getHtml().css("div#wrapper div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			if(null == titleList || titleList.size() < 1){
				//****************** youku以及其他  获取方式 ******************
				titleList = page.getHtml().css("section.box div.serieslist div."+mapExt.get("code")+" + div").xpath("//a/@title").all();
				jsPath = page.getHtml().css("div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			}
	        String comPath = DOMAIN + jsPath;
	        Request request = new Request(comPath);
			page.addTargetRequest(request);
			request.putExtra("titleList", titleList);
			request.putExtra("title", title);
			request.putExtra("directoryId", directoryId);
			request.putExtra("mapExt", mapExt);
			//由于获取js的时候出现乱码，由于采用默认的编码格式导致，所以在调用js内容时，先设置下编码格式
			site.setCharset("GBK");
		}else{
			Map mapExt = (Map)page.getRequest().getExtra("mapExt");
			String title = (String) page.getRequest().getExtra("title");
			Long directoryId = (Long) page.getRequest().getExtra("directoryId");
			String res = page.getHtml().regex(MjWebsiteConstant.get77dsRegular1ByCode((String)mapExt.get("code"))).toString();
			if(null == res){
				res = page.getHtml().regex(MjWebsiteConstant.get77dsRegular2ByCode((String)mapExt.get("code"))).toString();
			}
			
			if("".equals(res) || null == res){
				System.out.println(title + " " + directoryId + " 获取js数据为空  连接: " + page.getUrl());
				page.setSkip(true);
			}
			//获取每集名称
			List<String> titleList = (List<String>) page.getRequest().getExtra("titleList");
			//从js获取主要关键参数
			//res = res.replace("云视频',[", "").replace("]]", "");
			String[] resArr = res.split(",");
			
			Integer curCount = resArr.length;
			
			if(null == resArr || resArr.length < 1){
	        	System.out.println(title + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
	        	page.setSkip(true);
	        }
			
			Pattern patternJishu = Pattern.compile("[0-9].*[0-9]|[0-9]");
			Pattern patternHtml = Pattern.compile("url.php\", (.+?)\\},");
			String post = null;
			for (int i = 0; i < resArr.length; i++) {
		    	String indexJiShu = null;
				
		    	Matcher m = patternJishu.matcher(titleList.get(i));
		    	while(m.find()) {
		    		indexJiShu = m.group();
		    	}
		    	
				if(null != indexJiShu && indexJiShu.lastIndexOf("-") != -1){
					indexJiShu = indexJiShu.substring(0,indexJiShu.lastIndexOf("-"));
				}
				
				Integer indexJi;
				try {
					indexJi = Integer.valueOf(indexJiShu);
				} catch (NumberFormatException e) {
					indexJi = 0;
				}



				String[] playData = resArr[i].split("\\$");
				
				String firstUrl = MjWebsiteConstant.get77dsFirstUrlByCode((String)mapExt.get("code"));
				String jsHtmlUrl = firstUrl + "?id="+ playData[1];
				String html = HttpTookit.doGetReferer(jsHtmlUrl, null, firstUrl, true);
				
				
		    	Matcher mHtml = patternHtml.matcher(html);
		    	if(mHtml.find()) {
		    		post = mHtml.group(1);
		    	}
		    	
		    	JSONObject json = new JSONObject();
		        Map<String,String> map = json.parseObject(post+"}", Map.class);
		        
		        StringBuffer sb = new StringBuffer();
		        for (String key : map.keySet()) {
		        	sb.append(key+"="+map.get(key)+"&");
		        }
		        
		        String orgUrl = MjWebsiteConstant.get77dsSecondUrlByCode((String)mapExt.get("code")) + "?" + sb.toString().substring(0,sb.toString().length()-1);
		        
		    	String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.M77DS_NEW+"', '"+orgUrl+"', '"+indexJi + "集', NOW(), 'admin');";
		    	
		    	System.out.println(sqlret);
			}
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	
	public static void main(String[] args) {
		
		Map<Long,String> map = new HashMap<Long,String>();
    	map.put(2263l, "http://m.77ds.net/dalu/datangrongyao/zaixianbofang-3-50.html");
    	
    	Map<String,String> mapExt = new HashMap<String,String>();
//    	mapExt.put("code", "ykyun");
//    	mapExt.put("code", "qiyi");
//    	mapExt.put("code", "youku");
//    	mapExt.put("code", "sohu");
//    	mapExt.put("code", "letv");
//    	mapExt.put("code", "hunantv");
    	mapExt.put("code", "qqlive");
    	
    	SqlM77dsNew sqlM77dsNew = new SqlM77dsNew();
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
