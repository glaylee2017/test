package com.huaiwang.starsky.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.pojo.User;
import com.huaiwang.starsky.service.manage.TradeService;
import com.huaiwang.starsky.service.manage.UserService;

//交易测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/applicationContext*.xml" })
public class tradeTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	public UserService service;
	@Autowired
	public TradeService tradeService;

	@Test
	public void test() {

		String msg = "";
		String receiveNum = "888";
		String nickname = "aaa";
		Integer tradeNum = 100;
		User user = service.queryUserByPhoneNum("999");
		try {
			// //二次校验是否登录
			// if(request.getSession(false)==null||request.getSession().getAttribute("user")==null){
			// msg = "登录超时!请先登录!";
			// return SysResult.build(SysResult.USER_UN_LOGIN, msg);
			// }
			// 二次非空校验
			// if(StringUtils.isEmpty(receiveNum)||StringUtils.isEmpty(phoneVCode)||StringUtils.isEmpty(nickname)||tradeNum==null){
			// msg = "请把信息填写完整!";
			// System.out.println(msg);
			// }
			// User user = (User) request.getSession().getAttribute("user");
			// SysResult rs =
			// phoneVerificaitonService.checkVCode(user.getPhoneNum(),
			// phoneVCode, 2);
			// if(rs.getStatus()!=200){
			// return rs;
			// }
			if(tradeNum%10!=0){
				msg = "交易金额必须是10的整数倍!";
				System.out.println(msg);
				return;
			}
			Integer id = user.getId();
			SysResult tradeRs = tradeService.toTrade(id, receiveNum, nickname, tradeNum);
			System.out.println(tradeRs);
			
		} catch (Exception e) {

			e.printStackTrace();
			msg = "系统繁忙,请稍后再试!";
			System.out.println(msg);
		}
	}
}
