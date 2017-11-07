package com.zdf.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.bean.mj.MjDirectory;
import com.zdf.dao.mj.MjDirectoryDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MjDirectoryDaoTest {
	@Autowired
    private MjDirectoryDao dao;
	
	@Test
	public void testUpdate(){
		MjDirectory mjDirectory = new MjDirectory();
		mjDirectory.setId(2335l);
		mjDirectory.setUpdateCount("3");
		boolean flag = dao.update(mjDirectory);
		System.out.println(flag);
	}

	@Test
	public void testGetById(){
		MjDirectory mjDirectory = dao.getById(2334L);
		System.out.println(mjDirectory.getUpdateCount());
	}
}
