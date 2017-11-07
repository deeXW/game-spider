package com.zdf.processor.mj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.util.MjWebsiteConstant;
import com.zdf.webspider.processor.mj.HandleProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HandlerProcessorTest {
	@Autowired
	private HandleProcessor handleProcessor;
	
	/**
	 * ================================= 下载资源 爬虫================================= 
	 */
	//爬取更新的美剧 cn163
	@Test
	public void cn163(){
		handleProcessor.exe(MjWebsiteConstant.CN163);
	}
	
	
	//爬取更新的美剧 美剧天天
	@Test
	public void meijutt(){
		handleProcessor.exe(MjWebsiteConstant.MEIJUTT);
	}
	
	//爬取更新的美剧 字幕组
//	@Test
//	public void zimuzu(){
//		handleProcessor.exe(MjWebsiteConstant.ZIMUZU);
//	}
	/**
	 * ================================= 视频资源 爬虫================================= 
	 */
	//爬取在线视频 美剧汇 
	@Test
	public void meijuHui(){
		handleProcessor.exe(MjWebsiteConstant.MEIJUHUI);
	}
		
	//爬取更新的美剧 77电视
	@Test
	public void m77dsMj(){
		handleProcessor.exe(MjWebsiteConstant.M77DS);
	}
	
	//爬取更新的美剧 77电视
	@Test
	public void m77dsMjNew(){
		handleProcessor.exe(MjWebsiteConstant.M77DS_NEW);
	}
	
	//爬取更新的美剧 4k吧
	@Test
	public void kkkkMj(){
		handleProcessor.exe(MjWebsiteConstant.KKKK);
	}
	
//	@Test
//	public void mj941k(){
//		handleProcessor.exe(MjWebsiteConstant.J941K);
//	}
}
