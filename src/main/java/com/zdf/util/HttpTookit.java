package com.zdf.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * 
 * @author mcSui
 * 
 */
public class HttpTookit {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
    private static Logger logger = Logger.getLogger(HttpTookit.class);
    
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000)
                .setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
        		.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .build();
        
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET);
    }

    public static String doPost(String url, Map<String, String> params) throws Exception {
        return doPost(url, params, CHARSET);
    }

    /**
     * HTTP Get 获取内容
     * 
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params,
            String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(
                        params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :"
                        + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     * 
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     * @throws Exception 
     */
    public static String doPost(String url, Map<String, String> params,
            String charset) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
            	httpPost.abort();
            	throw new RuntimeException("HttpClient,error status code :"
	                      + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	throw e;
        }
    }

    
    public static String doPostReferer(String url, Map<String, String> params,
            String charset,String referer, boolean userAgent) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Referer", referer);
            if(userAgent){
            	httpPost.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
    		}
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	throw e;
        }
    }
    
    
    public static String doGetReferer(String url,String charset,String referer,boolean userAgent) {
    	if (StringUtils.isBlank(url)) {
    		return null;
    	}
    	if(null == charset || "".equals(charset)){
        	charset = "utf-8";
        }
    	try {
    		HttpGet httpGet = new HttpGet(url);
    		httpGet.setHeader("Referer", referer);
    		if(userAgent){
    			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
    		}
    		CloseableHttpResponse response = httpClient.execute(httpGet);
    		int statusCode = response.getStatusLine().getStatusCode();
    		if (statusCode != 200) {
    			httpGet.abort();
    			throw new RuntimeException("HttpClient,error status code :"
    					+ statusCode);
    		}
    		HttpEntity entity = response.getEntity();
    		String result = null;
    		if (entity != null) {
    			result = EntityUtils.toString(entity, "utf-8");
    		}
    		EntityUtils.consume(entity);
    		response.close();
    		return result;
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	return null;
    }
    
    
    public static String doGetHeads(String url,Map<String, String> heads,String charset,boolean userAgent) {
    	if (StringUtils.isBlank(url)) {
    		return null;
    	}
    	if(null == charset || "".equals(charset)){
        	charset = "utf-8";
        }
    	try {
    		HttpGet httpGet = new HttpGet(url);
//    		httpGet.setHeader("Referer", referer);
    		for (Map.Entry<String, String> entry : heads.entrySet()) {
    			httpGet.setHeader(entry.getKey(), entry.getValue());
			}
    		
    		if(userAgent){
    			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
    		}
    		CloseableHttpResponse response = httpClient.execute(httpGet);
    		int statusCode = response.getStatusLine().getStatusCode();
    		if (statusCode != 200) {
    			httpGet.abort();
    			throw new RuntimeException("HttpClient,error status code :"
    					+ statusCode);
    		}
    		HttpEntity entity = response.getEntity();
    		String result = null;
    		if (entity != null) {
    			result = EntityUtils.toString(entity, "utf-8");
    		}
    		EntityUtils.consume(entity);
    		response.close();
    		return result;
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	return null;
    }
    
    
    public static String doPostHeads(String url, Map<String, String> params, Map<String, String> heads,
    		String charset, boolean userAgent) throws Exception {
    	if (StringUtils.isBlank(url)) {
    		return null;
    	}
    	try {
    		List<NameValuePair> pairs = null;
    		if (params != null && !params.isEmpty()) {
    			pairs = new ArrayList<NameValuePair>(params.size());
    			for (Map.Entry<String, String> entry : params.entrySet()) {
    				String value = entry.getValue();
    				if (value != null) {
    					pairs.add(new BasicNameValuePair(entry.getKey(), value));
    				}
    			}
    		}
    		HttpPost httpPost = new HttpPost(url);
    		for (Map.Entry<String, String> entry : heads.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
    		
    		if(userAgent){
    			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
    		}
    		if (pairs != null && pairs.size() > 0) {
    			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
    		}
    		CloseableHttpResponse response = httpClient.execute(httpPost);
    		HttpEntity entity = response.getEntity();
    		String result = null;
    		if (entity != null) {
    			result = EntityUtils.toString(entity, "utf-8");
    		}
    		EntityUtils.consume(entity);
    		response.close();
    		return result;
    	} catch (Exception e) {
    		throw e;
    	}
    }
    
    public static void main(String[] args) throws Exception {
//    	Map<String, String> params = new HashMap<String,String>();
//    	params.put("id", "4259097");
//    	params.put("type", "acfun");
//    	params.put("siteuser", "123");
//    	params.put("md5", "9cb8b22ddfafb507049418d2911aabe7");
//    	String s = doPostReferer("http://www.meijub.com/mdparse/url.php", params, null, "http://www.meijub.com",true);
    	
//    	String s = doGetReferer("https://603088291.duapp.com/parse3.php?xmlurl=Qm0c96EpS9X6l6N4alEyTkE9PQO0O0OO0O0O.acku&tm=1479607824&sign=97ce78daa46b4d07cde62fa6a0cf00de&userlink=http%3A%2F%2Fwww.kkkkba.com%2FEurope%2Fxingshizouroudiqiji%2Fplayer-2-3.html", "utf-8", "https://603088291.duapp.com/player3.php?vid=Qm0c96EpS9X6l6N4alEyTkE9PQO0O0OO0O0O~2cf871a3.acku&?Next=javascript:alert(%27%E5%B7%B2%E7%BB%8F%E6%98%AF%E6%9C%80%E5%90%8E%E4%B8%80%E9%9B%86%EF%BC%81%27);",false);
//    	System.out.println(s);
    	
    	//直接调用原始这个链接，去获取m3u8链接,手机端直接调用
//		String url = "http://go.niubaitu.com/url.php?id=http://www.mgtv.com/b/291526/3687633.html&type=mgtv&siteuser=123&md5=6bf26eab1a09280710799c9f59106fb4";
//		String[] urlArr = url.split("\\?");
//		Map<String, String> map = new HashMap<String,String>();
//		String[] paramArr = urlArr[1].split("&");
//		for (String string : paramArr) {
//			String ps1 = string.substring(0, string.indexOf("="));
//			String ps2 = string.substring(string.indexOf("=")+1,string.length() );
//			map.put(ps1, ps2);
//		}
//		
//		String s = doPostReferer(urlArr[0], map, null, "http://go.niubaitu.com/url.php",false);
//		System.out.println(s);
		
//		JSONObject json = JSONObject.parseObject(s);
//		String obj = json.getString("url");
//		System.out.println(obj);
    	
    	
//    	String s = doGetReferer("http://high.eeeqi.cn/v.php?siteid=34&id=98&p=aj0wJm09NyZmPTIzLjAuMCZyPWh0dHAlM0ElMkYlMkZ3d3cuNWlrbWouY29tJTJGaW5kZXgmaz0mdT1odHRwJTNBJTJGJTJGd3d3LjVpa21qLmNvbSUyRmluZGV4JnJlcz0xOTIweDEwODAmdD0lRTYlODglOTElRTclODglQjElRTclOUMlOEIlRTclQkUlOEUlRTUlODklQTclMjAlN0MlMjAlRTUlQTUlQkQlRTclOUMlOEIlRTclOUElODQlRTclQkUlOEUlRTUlODklQTclMjAlN0MlMjAlRTclQkUlOEUlRTUlODklQTclRTQlQjglOEIlRTglQkQlQkQlMjAlN0MlMjAlRTclQkUlOEUlRTUlODklQTclRTYlOEUlOTIlRTglQTElOEMlMjAlN0MlMjAlRTUlQTQlQTklRTUlQTQlQTklRTclQkUlOEUlRTUlODklQTcmbD16aC1DTiZjPTEmc2U9Mg==&l=d3d3LjVpa21qLmNvbQ==", "utf-8", "http://high.eeeqi.cn/v.php",false);
//    	System.out.println(s);
    	
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("url", "https://yun.zzgjjy.com/parse.php?xmlurl=null&id=44923_1_0&tm=1490108690&sign=d875c134fcc54aa464b2a0ed1af8f477&e2=15520893");
    	map.put("refererSite", "http://www.kkkkba.com/");
    	map.put("isWap", "1");
//    	String url = "http://localhost:8090/webspider/videoCi/url1";
    	String url = "http://123.206.184.12:8889/webspider/videoCi/url1";
    	String ret = doPost(url, map);
    	
    	System.out.println(URLDecoder.decode(ret, "utf-8"));
    }
}