package com.huaiwang.starsky.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.pojo.User;
import com.huaiwang.starsky.service.manage.TradeService;
import com.huaiwang.starsky.service.web.PhoneVerificaitonService;


@Controller
@RequestMapping("/trade")
public class TradeController {
	private static final Logger log = Logger.getLogger(TradeController.class);
	private String msg = "";
	
	@Autowired
	public PhoneVerificaitonService phoneVerificaitonService;
	@Autowired
	private TradeService tradeService;
	//交易时获取验证码,每天不限次数
	@RequestMapping(value = "/getTrade",method = RequestMethod.GET)
	@ResponseBody
	public SysResult trade(String phone){		
		try {
			if (phone == null || "".equals(phone) || phone.length() != 11) {
				msg = "请输入正确的手机号";
				return SysResult.build(SysResult.FAULT, msg);
			}
			return phoneVerificaitonService.tradeCation(phone);
		} catch (Exception e) {	
			e.printStackTrace();
			log.error(e.getMessage());
			msg = "系统繁忙，请稍后重试";
			return SysResult.build(SysResult.FAULT,msg);
		}	
	}
	
	/**
	 * 发起交易
	 * @param request
	 * @param receiveNum 接受人电话
	 * @param phoneVCode 手机验证码
	 * @param nickname 接受人昵称
	 * @param tradeNum 交易金额
	 * @return
	 */
	@RequestMapping("/toTrade")
	@ResponseBody
	public SysResult toTrade(HttpServletRequest request,String receiveNum,String phoneVCode,String nickname,Integer tradeNum){
		try {
			//二次校验是否登录
			if(request.getSession(false)==null||request.getSession().getAttribute("user")==null){
				msg = "登录超时!请先登录!";
				return SysResult.build(SysResult.USER_UN_LOGIN, msg);
			}
			//二次非空校验
			if(StringUtils.isEmpty(receiveNum)||StringUtils.isEmpty(phoneVCode)||StringUtils.isEmpty(nickname)||tradeNum==null){
				msg = "请把信息填写完整!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			//校验交易金额是否为10的整数倍
			if(tradeNum%10!=0){
				msg = "交易金额必须是10的整数倍!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			User user = (User) request.getSession().getAttribute("user");
			SysResult rs = phoneVerificaitonService.checkVCode(user.getPhoneNum(), phoneVCode, 2);
			if(rs.getStatus()!=200){
				return rs;
			}
			Integer id = user.getId();
			SysResult tradeRs = tradeService.toTrade(id,receiveNum,nickname,tradeNum);
			return tradeRs;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "系统繁忙,请稍后再试!";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}
}
