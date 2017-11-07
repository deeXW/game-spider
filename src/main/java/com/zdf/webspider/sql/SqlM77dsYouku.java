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

import com.alibaba.fastjson.JSONObject;
import com.zdf.util.HttpTookit;
import com.zdf.util.MjWebsiteConstant;

public class SqlM77dsYouku implements PageProcessor {
    private final Logger             logger = Logger.getLogger(SqlM77dsYouku.class);

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
			//youku
			List<String> titleList = page.getHtml().css("div#playbox p.youku + div").xpath("//a/@title").all();
			String jsPath = page.getHtml().css("div#wrapper div.player").xpath("//script[@type=text/javascript]/@src").toString();
			if(null == titleList || titleList.size() < 1){
				titleList = page.getHtml().css("section.box div.serieslist div.youku + div").xpath("//a/@title").all();
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
			//youku
			String res = page.getHtml().regex("优酷.*youku']],").toString();
			if(null == res){
				res = page.getHtml().regex("优酷.*youku']]],").toString();
			}
			res = res.replace("优酷',[", "").replace("]],", "").replace("]]],", "");
			System.out.println(res);
			//sohu
//			String res = page.getHtml().regex("'搜狐'.*]]],").toString();
//			res = res.replace("'搜狐',[", "").replace("]]],", "");
			
			String[] resArr = res.split(",");
			List<String> titleList = (List<String>) page.getRequest().getExtra("titleList");
			String title = (String) page.getRequest().getExtra("title");
			System.out.println("-- " + title);
			for (int i = 0; i < resArr.length; i++) {
				String ss = resArr[i].substring(resArr[i].indexOf("$")+1,resArr[i].lastIndexOf("$"));
				String jsHtmlUrl = "http://vip.niubaitu.com:778/youkuvip.php?id="+ ss;
				String html = HttpTookit.doGetReferer(jsHtmlUrl, null, MjWebsiteConstant.M77DS_VIDEO_DOMAIN, true);
				
		    	Pattern pattern = Pattern.compile("url.php\", (.+?)\\},");
		    	Matcher m = pattern.matcher(html);
		    	String post = null;
		    	if(m.find()) {
		    		post = m.group(1);
		    	}
				
//				System.out.println(post+"}");
				JSONObject json = new JSONObject();
		        Map<String,String> map = json.parseObject(post+"}", Map.class);
		        
		        StringBuffer sb = new StringBuffer();
		        for (String key : map.keySet()) {
		        	sb.append(key+"="+map.get(key)+"&");
		        }
//		        System.out.println(sb.toString().substring(0,sb.toString().length()-1));
		        String url = "http://vip.niubaitu.com:778/url.php?"+sb.toString().substring(0,sb.toString().length()-1);
		        
		        
				Pattern pattern2 = Pattern.compile("[^0-9]");
		    	Matcher m2 = pattern2.matcher(titleList.get(i));
		    	String indexJiShu = m2.replaceAll("");
		    	String oneTitle = null;
		    	if("".equals(indexJiShu) || null == indexJiShu){
		    		oneTitle = titleList.get(i);
		    	}else{
		    		oneTitle = null != titleList && titleList.size()>0? Integer.valueOf(indexJiShu) + "集": String.format("%02d", i+1) + "集";
		    	}
				
				String sqlret = sql + "VALUES (" + i +","+directoryId+", '"+MjWebsiteConstant.M77DS+"_js', '"+url+"', '"+oneTitle+"', NOW(), 'admin');";
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
    	map.put("2167", "http://m.77ds.net/dalu/jipinjiading/zaixianbofang-0-0.html");
    	
    	SqlM77dsYouku sqlM77ds = new SqlM77dsYouku();
    	Spider spider = Spider.create(sqlM77ds);
    	for (Map.Entry<String,String> entry : map.entrySet()) {  
    		Request request = new Request(entry.getValue());
        	request.putExtra("directoryId", entry.getKey());
        	spider.addRequest(request);
        } 
        spider.thread(1).run();
        
    }
}
