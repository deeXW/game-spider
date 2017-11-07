package com.zdf.processor.hj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.webspider.processor.hj.HjHandlerProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HjHandlerProcessorTest {
	@Autowired
	private HjHandlerProcessor hjHandlerProcessor;
	
	/**
	 * ================================= 下载资源 爬虫================================= 
	 */
	
	/**
	 * ================================= 视频资源 爬虫================================= 
	 */
		
	//爬取更新的韩剧 77电视
	@Test
	public void m77dsHj(){
		hjHandlerProcessor.exe(MjWebsiteConstant.M77DS);
	}
	
	@Test
	public void m77dsHjNew(){
		hjHandlerProcessor.exe(MjWebsiteConstant.M77DS_NEW);
	}
}
