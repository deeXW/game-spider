package com.zdf.dao.dfwd;

import com.zdf.entity.dfwd.EcmsNewsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by zhongdifeng on 2017/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-dao-test.xml"})
public class EcmsNewsDaoTest {
    @Autowired
    private EcmsNewsDao dao;

    @Test
    public void inset(){
        EcmsNewsEntity ecmsNewsEntity = new EcmsNewsEntity();
        Date date = new Date();
        long time = date.getTime()/1000;
        ecmsNewsEntity.setTitle("测试一");
        ecmsNewsEntity.setTruetime((int)time);
        ecmsNewsEntity.setLastdotime((int)time);
        ecmsNewsEntity.setNewstime((int)time);
        ecmsNewsEntity.setUserid(1);
        ecmsNewsEntity.setUsername("spider");

        ecmsNewsEntity.setFmimg("");
        int ret = dao.insert(ecmsNewsEntity);
        System.out.println(ecmsNewsEntity.getId());

        EcmsNewsEntity ecmsNewsEntityUpdate = new EcmsNewsEntity();
        ecmsNewsEntityUpdate.setId(ecmsNewsEntity.getId());
        ecmsNewsEntityUpdate.setTitleurl("/news/"+ecmsNewsEntity.getId()+".html");
        ecmsNewsEntityUpdate.setFilename(ecmsNewsEntity.getId().toString());
        int retUpdate = dao.update(ecmsNewsEntityUpdate);
        System.out.println(retUpdate);
    }



    @Test
    public void getById(){
        EcmsNewsEntity ecmsNewsEntity = dao.getById(90);
        System.out.println(ecmsNewsEntity.toString());
    }


}
