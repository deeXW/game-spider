package com.zdf.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdf.interceptor.ValidateInterceptor;
import com.zdf.util.HttpTookit;

/**
 * @author dee
 *
 */
@Controller
@RequestMapping(value = "/videoCi")
public class VideoChangeIpController {
	private final Logger             logger = Logger.getLogger(VideoChangeIpController.class);
	@RequestMapping(value = "/url1")
	@ResponseBody
	public String url1(HttpServletRequest request,Model model) throws Exception {
		if(!validateIp(request)){
			logger.info("url1");
		}
		String url = request.getParameter("url");
		String refererSite = request.getParameter("refererSite");
		String isWap = request.getParameter("isWap");
		
		String resUrlOrg = HttpTookit.doGetReferer(url, "utf-8", refererSite,"1".equals(isWap)? true:false);
        return URLEncoder.encode(resUrlOrg,"utf-8");
	}
	
	
	@RequestMapping(value = "/url2")
	@ResponseBody
	public String url2(HttpServletRequest request,Model model) throws Exception {
		if(!validateIp(request)){
			logger.info("url1");
		}
		String url = request.getParameter("url");
		String refererSite = request.getParameter("refererSite");
		String isWap = request.getParameter("isWap");
		
		Map<String,String> heads = new HashMap<String,String>();
		heads.put(":authority", "h5vv.video.qq.com");
		heads.put("Referer", refererSite);
		
		String resUrlOrg = HttpTookit.doGetHeads(url, heads, null, "1".equals(isWap)? true:false);
        return URLEncoder.encode(resUrlOrg,"utf-8");
    }
	
	private boolean validateIp(javax.servlet.http.HttpServletRequest request){
		String ip = getRemoteHost(request);
		logger.info(ip);
		return true;
	}
	
	private String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
}
