package com.zdf.webspider.sql;

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

import com.zdf.util.MjWebsiteConstant;

public class SqlM77ds implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlM77ds.class);

    private StringBuffer             sb     = null;

    private static final String DOMAIN = "http://m.77ds.net";

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("m.77ds.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

	@SuppressWarnings("unchecked")
	@Override
	public void process(Page page) {
		String sql = "INSERT INTO mj_directory_video(sort,directory_id,type,video_url,video_name,create_time,create_by)"; 
		
		if(page.getUrl().toString().contains("zaixianbofang")){
			String directoryId = (String) page.getRequest().getExtra("directoryId");
			//美剧片名
			String title = page.getHtml().css("title").regex("《.*》").toString();
			//获取集数名称
			//ykyun
			List<String> titleList = page.getHtml().css("div#playbox p.ykyun + div").xpath("//a/@title").all();
			String jsPath = page.getHtml().css("div#wrapper div.player").xpath("//script[@type=text/javascript]/@src").toString();
			if(null == titleList || titleList.size() < 1){
				titleList = page.getHtml().css("section.box div.serieslist div.ykyun + div").xpath("//a/@title").all();
				jsPath = page.getHtml().css("div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			}
			//sohu
			//List<String> titleList = page.getHtml().css("div#playbox").xpath("//a/@title").all();
			//获取js信息 注释的是从客户端获取 再用的方法pc和客户端都可以获取
			//String jsPath = page.getHtml().css("div#wrapper div.playvideo div.player").xpath("//script[@type=text/javascript]/@src").toString();
			
	        String comPath = DOMAIN + jsPath;
	        Request request = new Request(comPath);
			page.addTargetRequest(request);
			request.putExtra("titleList", titleList);
			request.putExtra("title", title);
			request.putExtra("directoryId", directoryId);
			//由于获取js的时候出现乱码，由于采用默认的编码格式导致，所以在调用js内容时，先设置下编码格式
			site.setCharset("GBK");
		}else{
			String directoryId = (String) page.getRequest().getExtra("directoryId");
			//ykyun
			String res = page.getHtml().regex("云视频.*ykyun']],").toString();
			if(null == res){
				res = page.getHtml().regex("云视频.*ykyun']]],").toString();
			}
			res = res.replace("云视频',[", "").replace("]],", "");
			//sohu
//			String res = page.getHtml().regex("'搜狐'.*]]],").toString();
//			res = res.replace("'搜狐',[", "").replace("]]],", "");
			
			String[] resArr = res.split(",");
			List<String> titleList = (List<String>) page.getRequest().getExtra("titleList");
			String title = (String) page.getRequest().getExtra("title");
			System.out.println("-- " + title);
			for (int i = 0; i < resArr.length; i++) {
				Pattern pattern = Pattern.compile("[^0-9]");
		    	Matcher m = pattern.matcher(titleList.get(i));
		    	String indexJiShu = m.replaceAll("");
		    	String oneTitle = null;
		    	if("".equals(indexJiShu) || null == indexJiShu){
		    		oneTitle = titleList.get(i);
		    	}else{
		    		oneTitle = null != titleList && titleList.size()>0? Integer.valueOf(indexJiShu) + "集": String.format("%02d", i+1) + "集";
		    	}
				//System.out.println(resArr[i]);
				String ss = resArr[i].substring(resArr[i].indexOf("$")+1,resArr[i].lastIndexOf("$")+6);
				String videoUrl = MjWebsiteConstant.M77DS_VIDEO_DOMAIN + "?url="+ss.replace("$", "_");
//				System.out.println(titleList.get(i) + "-->" +resultRes);
				
				String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.M77DS+"', '"+videoUrl+"', '"+oneTitle+"', NOW(), 'admin');";
		    	System.out.println(sqlret);
			}
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
//    	map.put("", "");
    	map.put("596", "http://m.77ds.net/oumei/xingshizouroudiqiji/zaixianbofang-0-0.html");
    	
    	SqlM77ds sqlM77ds = new SqlM77ds();
    	Spider spider = Spider.create(sqlM77ds);
    	for (Map.Entry<String,String> entry : map.entrySet()) {  
    		Request request = new Request(entry.getValue());
        	request.putExtra("directoryId", entry.getKey());
        	spider.addRequest(request);
        } 
        spider.thread(1).run();
        
    }
}
