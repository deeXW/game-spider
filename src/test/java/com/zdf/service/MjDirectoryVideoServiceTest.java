package com.zdf.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.service.mj.MjDirectoryVideoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class MjDirectoryVideoServiceTest {
	@Autowired
	private MjDirectoryVideoService service;
	
	@Test
	public void getVideoListByDirectoryId(){
		Long directoryId = 603l;
		List<MjDirectoryVideo> list = service.getVideoListByDirectoryId(directoryId);
		for (MjDirectoryVideo mjDirectoryVideo : list) {
			System.out.println(mjDirectoryVideo.toString());
		}
	}
	
	@Test
	public void getVideoMapByDirectoryId(){
		Long directoryId = 603l;
		Map<Integer,MjDirectoryVideo> map = service.getVideoMapByDirectoryId(directoryId);
		for (Integer key : map.keySet()) {  
			System.out.println("Key = " + key + " bean = " + map.get(key).toString());  
		} 
	}
}
