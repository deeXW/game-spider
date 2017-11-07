package com.zdf.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.bean.mj.MjDirectoryLinks;
import com.zdf.dao.mj.MjDirectoryLinksDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MjDirectoryLinksDaoTest {
	@Autowired
    private MjDirectoryLinksDao dao;
	
	@Test
	public void getLinksCount(){
		Long directoryId = 1232l;
		Integer ret = dao.getLinksCount(directoryId);
		System.out.println(ret);
	}
	
	@Test
	public void getVideoListByDirectoryId(){
		Long directoryId = 1359l;
		String isSubtitle = "1";
		List<MjDirectoryLinks> list = dao.getLinksListByParams(directoryId, isSubtitle);
		for (MjDirectoryLinks mjDirectoryLinks : list) {
			System.out.println(mjDirectoryLinks.toString());
		}
	}
	
	@Test
	public void getMaxSort(){
		Long directoryId = 1359l;
		String isSubtitle = "1";
		Long ret = dao.getMaxSort(directoryId, isSubtitle);
		System.out.println(ret);
	}
	
	@Test
	public void insert(){
		MjDirectoryLinks mjDirectoryLinks = new MjDirectoryLinks();
		mjDirectoryLinks.setSort(1);
		mjDirectoryLinks.setDirectoryId(2335l);
		mjDirectoryLinks.setDownloadName("权欲.Power.2014.S04E01.720p.WEBRip.x264.双语字幕.深影字幕组.mp4");
		mjDirectoryLinks.setDownloadLink("ed2k://|file|权欲.Power.2014.S04E01.720p.WEBRip.x264.双语字幕.深影字幕组.mp4|602704174|71555ed52f668aac62d6a01731b322e1|h=en73ksc5hqsdd6suhe4zngu4izs2cfch|/");

		mjDirectoryLinks.setDownloadName720("");
		mjDirectoryLinks.setDownloadLink720("");

		mjDirectoryLinks.setIsDistinguish("0");
		mjDirectoryLinks.setIsSubtitle("1");
		mjDirectoryLinks.setPixel("11");
		boolean flag = dao.insert(mjDirectoryLinks);
		System.out.println(flag);
	}
	
	@Test
	public void update(){
		
		MjDirectoryLinks mjDirectoryLinks = new MjDirectoryLinks();
		mjDirectoryLinks.setId(35832l);
		mjDirectoryLinks.setDownloadName("test-update");
		mjDirectoryLinks.setDownloadLink("baidu");
		boolean flag = dao.update(mjDirectoryLinks);
		System.out.println(flag);
	}
}
