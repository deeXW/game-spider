package com.zdf.processor.gj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.webspider.processor.gj.GjHandlerProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class GjHandlerProcessorTest {
	@Autowired
	private GjHandlerProcessor gjHandlerProcessor;
	
	/**
	 * ================================= 下载资源 爬虫================================= 
	 */
	
	/**
	 * ================================= 视频资源 爬虫================================= 
	 */
		
	//爬取更新的国剧 77电视
	@Test
	public void m77dsGj(){
		gjHandlerProcessor.exe(MjWebsiteConstant.M77DS);
	}
	
	@Test
	public void m77dsGjNew(){
		gjHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
	}
	
	//爬取更新的国剧 4k吧
	@Test
	public void kkkkGJ(){
		gjHandlerProcessor.exe(MjWebsiteConstant.KKKK);
	}
}
