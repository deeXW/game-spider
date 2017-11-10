package com.zdf.dao.dfwd;

import com.zdf.entity.EcmsNewsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhongdifeng on 2017/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-dao-test.xml"})
public class EcmsNewsDaoTest {
    @Autowired
    private EcmsNewsDao dao;



    @Test
    public void getById(){
        EcmsNewsEntity ecmsNewsEntity = dao.getById(90);
    }
}
