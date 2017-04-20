package com.huaiwang.starsky.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huaiwang.starsky.service.manage.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/applicationContext*.xml" })
public class Demo extends AbstractJUnit4SpringContextTests {
	@Autowired
	private UserService userService;
	@Test
	public void haha(){
		Integer payUid = 26;
		
	}
}
