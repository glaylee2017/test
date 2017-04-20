package com.huaiwang.starsky.controller.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaiwang.starsky.common.util.IpUtils;
import com.huaiwang.starsky.common.util.MD5Util;
import com.huaiwang.starsky.common.util.VerificationCode;
import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.pojo.ActiveCode;
import com.huaiwang.starsky.pojo.LoginLog;
import com.huaiwang.starsky.pojo.User;
import com.huaiwang.starsky.service.manage.ActiveCodeService;
import com.huaiwang.starsky.service.manage.LoginLogService;
import com.huaiwang.starsky.service.manage.UserService;
import com.huaiwang.starsky.service.web.PhoneVerificaitonService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PhoneVerificaitonService phoneVerificaitonService;
	@Autowired
	private ActiveCodeService activeCodeService;
	@Autowired
	private LoginLogService loginLogService;
	private static final Logger log = Logger.getLogger(UserController.class);
	private String msg = "";

	@RequestMapping("/regist")
	@ResponseBody
	public SysResult regist(User user, HttpServletRequest request, String phoneVCode) {
		try {
			// SysResult rs =
			// phoneVerificaitonService.checkVCode(user.getPhoneNum(),
			// phoneVCode,0);//获取用户输入的验证码并校验
			// if(rs.getStatus()!=200){//验证码不正确,直接返回错误信息(包含非空校验)
			// return rs;
			// }
			if (StringUtils.isEmpty(user.getPhoneNum())) {// 二次非空检验帐号
				msg = "帐号(手机号)不能为空!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if (StringUtils.isEmpty(user.getPassword())) {// 二次非空校验密码
				msg = "密码不能为空!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if (StringUtils.isEmpty(user.getActiveCode().getCode())) {// 二次非空校验激活码
				msg = "激活码不能为空!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			// 二次检查密码格式
			if (!user.getPassword().matches("^([A-Z]|[a-z]|[0-9]){6,20}$")) {
				msg = "密码格式不正确!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			// 二次判断用户是否已经存在
			if (userService.queryUserByPhoneNum(user.getPhoneNum()) != null) {
				msg = "该用户已存在!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			// 二次判断昵称是否已存在
			if (userService.queryUserByNickname(user.getNickname()) != null) {
				msg = "该昵称已被使用!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			// 根据手机跟激活码获取对应的验证码对象
			ActiveCode activeCode = activeCodeService.queryActiveCode(user.getPhoneNum(),
					user.getActiveCode().getCode());
			if (activeCode == null) {
				msg = "激活码输入错误或未绑定手机号!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if (activeCode.getState() == 1) {
				msg = "激活码已被使用!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			user.setActiveCode(activeCode);
			userService.saveUser(user);
			msg = "恭喜您注册成功！";
			return SysResult.build(SysResult.SUCCESS, msg);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "系统正忙，请稍后重试";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}

	// 注册时获取验证码，一天只允许三次
	@RequestMapping(value = "/getVCode", method = RequestMethod.GET)
	@ResponseBody
	public SysResult getVCode(String phoneNum) {
		try {
			if (phoneNum != null) {
				if (phoneNum.length() != 11) {
					return SysResult.build(SysResult.FAULT, "手机长度不对，请重新输入");
				}
				return phoneVerificaitonService.sendMsg(phoneNum);
			}
			msg = "请输入正确的手机号";
			return SysResult.build(SysResult.FAULT, msg);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "系统正忙，请稍后重试";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}

	@RequestMapping("tologin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/login")
	@ResponseBody
	public SysResult login(User user, HttpServletRequest request) {
		try {
			User cur_user = userService.queryUserByPhoneNum(user.getPhoneNum());
			// 判断帐号(电话号码是否存在)
			if (StringUtils.isEmpty(cur_user.getPhoneNum())) {
				msg = "帐号不存在!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if (cur_user.getState() == 2) {
				msg = "帐号已被冻结,详情请联系客服!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			String pw = MD5Util.getMd5Hash(user.getPhoneNum(), user.getPassword());
			if (cur_user.getPassword().equals(pw)) {
				msg = "登录成功!";
				request.getSession().setAttribute("user", cur_user);
				String ip = IpUtils.getRemoteHost(request);
				// 写登录日志
				LoginLog loginLog = new LoginLog();
				loginLog.setLoginTime(new Date());
				loginLog.setPhoneNum(cur_user.getPhoneNum());
				loginLog.setIp(ip);
				loginLogService.saveSelective(loginLog);
				return SysResult.build(SysResult.SUCCESS, msg, cur_user);
			} else {
				msg = "密码错误!";
				return SysResult.build(SysResult.FAULT, msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "网络异常,请稍后再试!";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}

	// 找回登陆密码发送验证码
	@RequestMapping(value = "/saveLogcode", method = RequestMethod.GET)
	@ResponseBody
	public SysResult saveLogcode(String phoneNum) {
		try {
			if (phoneNum == null) {
				msg = "请输入正确的手机号";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if (!VerificationCode.checkPhone(phoneNum)) {
				msg = "请输入正确的手机号";
				return SysResult.build(SysResult.FAULT, msg);
			}
			return phoneVerificaitonService.saveLogcode(phoneNum);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "网络异常,请稍后再试!";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}

	// 修改密码入口
	@RequestMapping("/updatePw")
	@ResponseBody
	public SysResult updatePw(String phoneNum, String password, String phoneVCode) {
		try {
			// 校验用户验证码是否正确
			// SysResult rs = phoneVerificaitonService.checkVCode(phoneNum,
			// phoneVCode, 3);
			// if (rs.getStatus() != 200) {
			// return rs;
			// }
			// 二次检查密码格式
			if (!password.matches("^([A-Z]|[a-z]|[0-9]){6,20}$")) {
				msg = "密码格式不正确!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			// 再度确认手机号是否存在
			User user = userService.queryUserByPhoneNum(phoneNum);
			if (user == null) {
				msg = "用户不存在，请确认账号是否正确";
				return SysResult.build(SysResult.FAULT, msg);
			}
			userService.updatePw(phoneNum, password);
			msg = "修改密码成功!";
			return SysResult.build(SysResult.SUCCESS, msg);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "网络异常,请稍后再试!";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}
	@RequestMapping("/checkNickname")
	@ResponseBody
	public SysResult checkNickname(String nickname){
		try {
			if(nickname == null){
				msg = "请输入昵称!";
				return SysResult.build(SysResult.FAULT, msg);
			}
			if(userService.queryUserByNickname(nickname)==null){
				msg = "恭喜您该昵称可用!";
				return SysResult.build(SysResult.SUCCESS, msg);
			}else{
				msg = "对不起,该昵称已被占用!";
				return SysResult.build(SysResult.FAULT, msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			msg = "系统繁忙,请稍后再试!";
			return SysResult.build(SysResult.FAULT, msg);
		}
	}
}
