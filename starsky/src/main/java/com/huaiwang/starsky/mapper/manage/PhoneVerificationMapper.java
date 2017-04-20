package com.huaiwang.starsky.mapper.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huaiwang.starsky.common.mapper.SysMapper;
import com.huaiwang.starsky.pojo.PhoneVerificaion;

public interface PhoneVerificationMapper extends SysMapper<PhoneVerificaion> {
	/*
	 * 根据手机号,状态码查询存在几个对象 phone 手机号 stype 状态码0注册，1更改手机号，2交易,3找回密码
	 */
	public List<PhoneVerificaion> queryUserByPhone(@Param("phone") String phone, @Param("stype") Integer stype);

	// 通过手机号，验证码查询对象
	public List<PhoneVerificaion> queryPVcode(@Param("phone") String phone, @Param("code") String code,
			@Param("stype") Integer stype);

	// 将昨天的注册验证码隐藏状态改为5
	public void updateStype();
}
