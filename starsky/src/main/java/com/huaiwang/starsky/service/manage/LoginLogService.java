package com.huaiwang.starsky.service.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.mapper.manage.LoginLogMapper;
import com.huaiwang.starsky.pojo.LoginLog;
import com.huaiwang.starsky.service.common.BaseService;
//登录日志service
@Service
public class LoginLogService extends BaseService<LoginLog>{
	@Autowired
	private LoginLogMapper loginLogMapper;
}
