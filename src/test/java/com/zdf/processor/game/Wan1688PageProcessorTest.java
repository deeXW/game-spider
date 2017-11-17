package com.zdf.processor.game;

import com.zdf.webspider.game.Wan1688PageProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dee on 2017/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class Wan1688PageProcessorTest {
    @Autowired
    private Wan1688PageProcessor wan1688PageProcessor;

    @Test
    public void test(){
        wan1688PageProcessor.handleLinks(wan1688PageProcessor);
    }

}
