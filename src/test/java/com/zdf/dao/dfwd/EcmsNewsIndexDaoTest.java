package com.zdf.dao.dfwd;

import com.zdf.entity.dfwd.EcmsNewsIndexEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by zhongdifeng on 2017/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-dao-test.xml"})
public class EcmsNewsIndexDaoTest {
    @Autowired
    private EcmsNewsIndexDao dao;

    @Test
    public void insert(){
        EcmsNewsIndexEntity entity = new EcmsNewsIndexEntity();
        Date date = new Date();
        long time = date.getTime()/1000;

        entity.setId(1);
        entity.setClassid(2);
        entity.setChecked(0);
        entity.setHavehtml(1);
        entity.setNewstime((int)time);
        entity.setTruetime((int)time);
        entity.setLastdotime((int)time);
        int ret = dao.insert(entity);
        System.out.println(ret);
    }
}
