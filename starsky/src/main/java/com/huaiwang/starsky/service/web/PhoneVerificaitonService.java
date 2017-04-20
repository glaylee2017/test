package com.huaiwang.starsky.service.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.common.util.VerificationCode;
import com.huaiwang.starsky.common.vo.SmsSingleSenderResult;
import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.mapper.manage.PhoneVerificationMapper;
import com.huaiwang.starsky.mapper.manage.UserMapper;
import com.huaiwang.starsky.pojo.PhoneVerificaion;
import com.huaiwang.starsky.pojo.User;

//手机验证码
@Service
public class PhoneVerificaitonService {

	@Autowired
	public PhoneVerificationMapper phoneVerificationMapper;
	@Autowired
	private UserMapper userMapper;

	private final static VerificationCode verification = new VerificationCode();

	// 注册时获取验证码，一天只允许三次
	public synchronized SysResult sendMsg(String phone) {
		if (phone == null || "".equals(phone) || phone.length() != 11) {
			return SysResult.build(SysResult.FAULT, "数据格式有误！");
		}
		// 查询该手机号码是否已经存在
		Integer num = userMapper.queryUserByPhoneExit(phone);
		if (num != 0) {
			return SysResult.build(SysResult.FAULT, "该手机已存在,请更换手机号");
		}
		// 拿到全部关于该手机号的全部发送记录，取第一条出来对比当前时间，是否过期,过期重新获取，不过期就通过
		// 查询该手机号今天总共有几条验证码对象
		List<PhoneVerificaion> pvList = phoneVerificationMapper.queryUserByPhone(phone, 0);

		if (pvList.size() != 0) {
			Date d = new Date();
			// 拿过期时间和当前时间对比，
			int t = (int) ((d.getTime() - pvList.get(0).getUpdated().getTime()) / 1000);
			if (t < 0) {
				 return SysResult.build(SysResult.FAULT, "请勿频繁索取");
			}
			// 便利所获取的验证码，查看今天是否超过三次
			if (pvList.size() > 2) {
				return SysResult.build(SysResult.FAULT, "您当天获取超过三次，请明天再次获取");
			}
		}

		String msgphone = "+86" + phone;
		String code = verification.getvcode();
		String msm = code + "为您的验证码，请于2分钟内填写。如非本人操作，请忽略本短信。";
		SmsSingleSenderResult singleSenderResult = verification.sensms(msgphone, msm);
		if (singleSenderResult == null) {
			return SysResult.build(SysResult.FAULT, "短信发送失败！");
		}
		if (singleSenderResult.result != 0) {
			return SysResult.build(SysResult.FAULT, singleSenderResult.errMsg);
		}

		// 成功发送存入数据库
		PhoneVerificaion phoneVerification = new PhoneVerificaion();
		phoneVerification.setPhone(phone);
		phoneVerification.setCode(code);
		phoneVerification.setStype(0);
		phoneVerification.setCreated(new Date());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, +2);
		phoneVerification.setUpdated(c.getTime());
		phoneVerificationMapper.insert(phoneVerification);

		return SysResult.build(SysResult.SUCCESS, "发送成功！");
	}

	/*
	 * 验证码过期及正确性
	 * phoneNum 手机号
	 * phoneVCode 验证码
	 * stype 状态码 0是注册验证码 1是更改手机号验证码 2是交易验证码
	 */
	public SysResult checkVCode(String phoneNum, String phoneVCode,Integer stype) {
		if (phoneNum.length() != 11 && phoneVCode.length() != 6) {
			return SysResult.build(SysResult.FAULT, "手机号长度或者验证码长度不正确");
		}
		if (verification.checkPhone(phoneNum)) {
			List<PhoneVerificaion> ph = phoneVerificationMapper.queryPVcode(phoneNum, phoneVCode,stype);
			if(ph.size() != 0){
				Date d = new Date();
				int time = (int) ((d.getTime() - ph.get(0).getUpdated().getTime()) / 1000);
				if (time > 0) {
					return SysResult.build(SysResult.FAULT, "验证码超时，请重新获取");
				}
				return SysResult.build(SysResult.SUCCESS, "恭喜，验证通过");
			}
		}
		return SysResult.build(SysResult.FAULT, "请输入正确的手机号或验证码");
	}

	// 交易获取验证码，一天不限次数
	public synchronized SysResult tradeCation(String phone) {
		//逻辑思维，获取验证码后，状态为2直接存入数据库
		if (phone == null || "".equals(phone) || phone.length() != 11) {
			return SysResult.build(SysResult.FAULT, "请输入正确的手机号");
		}

		String msgphone = "+86" + phone;
		String code = verification.getvcode();
		String msm = code + "为您的验证码，请于2分钟内填写。如非本人操作，请忽略本短信。";
		SmsSingleSenderResult singleSenderResult = verification.sensms(msgphone, msm);
		if (singleSenderResult == null) {
			return SysResult.build(SysResult.FAULT, "短信发送失败！");
		}
		if (singleSenderResult.result != 0) {
			return SysResult.build(SysResult.FAULT, singleSenderResult.errMsg);
		}

		// 成功发送存入数据库
		PhoneVerificaion phoneVerification = new PhoneVerificaion();
		phoneVerification.setPhone(phone);
		phoneVerification.setCode(code);
		phoneVerification.setStype(2);
		phoneVerification.setCreated(new Date());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, +2);
		phoneVerification.setUpdated(c.getTime());
		phoneVerificationMapper.insert(phoneVerification);

		return SysResult.build(SysResult.SUCCESS, "发送成功！");
	}
	
	// 找回密码时获取验证码，一天只允许三次
	public synchronized SysResult saveLogcode(String phone) {
			if (phone == null || "".equals(phone) || phone.length() != 11) {
				return SysResult.build(SysResult.FAULT, "数据格式有误！");
			}
			// 查询该手机号码是否已经存在
			User user = userMapper.queryUserByPhoneNum(phone);
			if (user == null) {
				return SysResult.build(SysResult.FAULT, "账号不存在,请核对后重新输入");
			}
			// 拿到全部关于该手机号的全部发送记录，取第一条出来对比当前时间，是否过期,过期重新获取，不过期就通过
			// 查询该手机号今天总共有几条验证码对象，stype 状态码0注册，1更改手机号，2交易,3找回密码
			List<PhoneVerificaion> pvList = phoneVerificationMapper.queryUserByPhone(phone, 3);

			if (pvList.size() != 0) {
				Date d = new Date();
				// 拿过期时间和当前时间对比，
				int t = (int) ((d.getTime() - pvList.get(0).getUpdated().getTime()) / 1000);
				if (t < 0) {
					 return SysResult.build(SysResult.FAULT, "请勿频繁索取");
				}
				// 便利所获取的验证码，查看今天是否超过三次
				if (pvList.size() > 2) {
					return SysResult.build(SysResult.FAULT, "您当天获取超过三次，请明天再次获取");
				}
			}

			String msgphone = "+86" + phone;
			String code = verification.getvcode();
			String msm = code + "为您的验证码，请于2分钟内填写。如非本人操作，请忽略本短信。";
			SmsSingleSenderResult singleSenderResult = verification.sensms(msgphone, msm);
			if (singleSenderResult == null) {
				return SysResult.build(SysResult.FAULT, "短信发送失败！");
			}
			if (singleSenderResult.result != 0) {
				return SysResult.build(SysResult.FAULT, singleSenderResult.errMsg);
			}

			// 成功发送存入数据库
			PhoneVerificaion phoneVerification = new PhoneVerificaion();
			phoneVerification.setPhone(phone);
			phoneVerification.setCode(code);
			phoneVerification.setStype(3);
			phoneVerification.setCreated(new Date());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, +2);
			phoneVerification.setUpdated(c.getTime());
			phoneVerificationMapper.insert(phoneVerification);

			return SysResult.build(SysResult.SUCCESS, "发送成功！");
		}

}
