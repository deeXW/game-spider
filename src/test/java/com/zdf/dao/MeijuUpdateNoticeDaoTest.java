package com.zdf.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.dao.mj.MeijuUpdateNoticeDao;
import com.zdf.util.JuTypeUtil.JuType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MeijuUpdateNoticeDaoTest {
    @Autowired
    private MeijuUpdateNoticeDao dao;

    @Test
    public void updateCount() {
        MeijuUpdateNotice meijuUpdateNotice = new MeijuUpdateNotice();
        meijuUpdateNotice.setNowUpdateCount("4");
        meijuUpdateNotice.setId(182l);
        boolean flag = dao.updateCount(meijuUpdateNotice);
        System.out.println(flag);
    }

    @Test
    public void getMeijuUpdateNoticeListByType() {
        List<MeijuUpdateNotice> list = dao.getMeijuUpdateNoticeListByType("1",JuType.MEIJU.getCode());
        for (MeijuUpdateNotice meijuUpdateNotice : list) {
            System.out.println(meijuUpdateNotice.toString());
        }
    }
}
