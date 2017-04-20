package com.huaiwang.starsky.service.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.mapper.manage.UserAccountMapper;
import com.huaiwang.starsky.pojo.UserAccount;
import com.huaiwang.starsky.service.common.BaseService;

@Service
public class UserAccountService extends BaseService<UserAccount> {
	@Autowired
	private UserAccountMapper userAccountMapper;
	/**
	 * 根据用户id查询其账户信息
	 * @param id
	 * @return
	 */
	public UserAccount queryAccountByUserId(Integer userId) {

		return userAccountMapper.queryAccountByUserId(userId);
	}

}
