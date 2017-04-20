package com.huaiwang.starsky.mapper.manage;

import com.huaiwang.starsky.common.mapper.SysMapper;
import com.huaiwang.starsky.pojo.UserAccount;

public interface UserAccountMapper extends SysMapper<UserAccount>{
	/**
	 * 根据用户id查询其帐号信息
	 * @param id
	 * @return
	 */
	public UserAccount queryAccountByUserId(Integer userId);

}
