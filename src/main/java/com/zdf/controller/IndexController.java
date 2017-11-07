package com.zdf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	/**
	 * 访问默认首页处理方式一 
	 * 在web.xml中
	 * 加入一个映射
	 * <servlet-mapping>  
	 * 	<servlet-name>dy</servlet-name>  
	 * 	<url-pattern>/index</url-pattern>  
	 * </servlet-mapping>
	 * 
	 * <welcome-file-list>
	 *	<welcome-file>index</welcome-file>
	 * </welcome-file-list>
	 */
	@RequestMapping(value = "/index")
    public String index(Model model) throws Exception {
        return "screen/index";
    }
	
	
	//访问默认首页处理方式二
//	@RequestMapping(value = "/")
//	public String index2(Model model) throws Exception {
//		model.addAttribute("title", "天天美剧 | 美剧排行榜,2015好看的美剧推荐");
//		model.addAttribute("description", "天天美剧网提供最新的美剧高清看,2014好看的美剧资讯情报预告,最好经典美剧推荐,2013热门美剧排行榜各类美剧下载吧的网站。");
//		model.addAttribute("keywords", "美剧排行榜2014、天天美剧");
//		return "screen/index";
//	}
	
	
	
}
