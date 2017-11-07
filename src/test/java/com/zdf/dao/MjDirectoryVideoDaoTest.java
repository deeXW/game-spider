package com.zdf.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.bean.mj.MjDirectoryVideo;
import com.zdf.dao.mj.MjDirectoryVideoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MjDirectoryVideoDaoTest {
	@Autowired
    private MjDirectoryVideoDao dao;
	
	@Test
	public void getVideoListByDirectoryId(){
		Long directoryId = 603l;
		List<MjDirectoryVideo> list = dao.getVideoListByDirectoryId(directoryId);
		for (MjDirectoryVideo mjDirectoryVideo : list) {
			System.out.println(mjDirectoryVideo.toString());
		}
	}
	
	@Test
	public void getMaxSort(){
		Long ret = dao.getMaxSort(60311l);
		System.out.println(ret);
	}
	
	@Test
	public void insert(){
		MjDirectoryVideo mjDirectoryVideo = new MjDirectoryVideo();
		mjDirectoryVideo.setDirectoryId(999l);
		mjDirectoryVideo.setType("99");
		mjDirectoryVideo.setSort(99l);
		mjDirectoryVideo.setOrgUrl("111");
		mjDirectoryVideo.setVideoUrl("111");
		mjDirectoryVideo.setVideoName("111");
		boolean flag = dao.insert(mjDirectoryVideo);
		System.out.println(flag);
	}
}
