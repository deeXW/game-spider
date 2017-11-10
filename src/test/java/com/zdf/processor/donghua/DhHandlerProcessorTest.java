package com.zdf.processor.donghua;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.webspider.processor.donghua.DhHandlerProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class DhHandlerProcessorTest {
	@Autowired
	private DhHandlerProcessor dhHandlerProcessor;
	
	/**
	 * ================================= 下载资源 爬虫================================= 
	 */
	
	/**
	 * ================================= 视频资源 爬虫================================= 
	 */
		
	
	@Test
	public void m77dsDhNew(){
		dhHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
	}
	
	@Test
	public void kkkk(){
		dhHandlerProcessor.exe(MjWebsiteConstant.KKKK);
	}
}
