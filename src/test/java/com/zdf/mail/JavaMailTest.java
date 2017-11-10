package com.zdf.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JavaMailTest {
	@Autowired
	private JavaMail javaMail;
	
	@Test
	public void doSendHtmlEmail(){
		javaMail.doSendHtmlEmail("邮件主题", "邮件内容", "woikmj@163.com");
	}
}
