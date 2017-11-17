package com.zdf.dao.dfwd;

import com.zdf.entity.dfwd.EcmsNewsData1Entity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhongdifeng on 2017/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-dao-test.xml"})
public class EcmsNewsData1DaoTest {
    @Autowired
    private EcmsNewsData1Dao dao;

    @Test
    public void getById(){
        EcmsNewsData1Entity entity = dao.getById(76);
        System.out.println(entity.toString());
    }


    @Test
    public void insert(){
        EcmsNewsData1Entity entity = new EcmsNewsData1Entity();
        entity.setId(1);
        entity.setClassid(2);
        entity.setInfotags("111");
        entity.setNewstext("111");
        entity.setWriter("625u");

        int ret = dao.insert(entity);
        System.out.println(ret);

    }

}
