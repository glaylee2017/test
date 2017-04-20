package com.huaiwang.starsky.mapper.manage;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huaiwang.starsky.common.mapper.SysMapper;
import com.huaiwang.starsky.pojo.User;

public interface UserMapper extends SysMapper<User>{
	/**
	 * 保存玩家信息
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * 通过玩家的手机号码（帐号）查找玩家信息
	 * @param phoneNum
	 */
	public User queryUserByPhoneNum(String phoneNum);
	/**
	 * 根据玩家Id查找其上级玩家Id列表
	 * @param id
	 * @return
	 */
	public List<Integer> queryPlistById(Integer id);
	
	
	/*
	 * 通过手机号查询该手机号是否存在
	 */
	public void queryPhoneNumExit();
		
	public Integer queryUserByPhoneExit(String phoneNum);
	/**
	 * 保存玩家的上级Id到上级列表
	 * @param id
	 * @param pList
	 */
	public void saveUserPid(@Param("id")Integer id,@Param("pIds") List<Integer> pList,@Param("created") Date date);
	/**
	 * 修改玩家密码
	 * @param phoneNum 帐号
	 * @param password	新密码
	 */
	public void updatePw(@Param("phoneNum")String phoneNum,@Param("password") String password);
	/**
	 * 根据玩家Id查找其对应的上级玩家id列表
	 * @param id 玩家id
	 * @return
	 */
	public List<Integer> queryPidListById(Integer id);
	/**
	 * 获取限制交易用户id表
	 * @return
	 */
	public List<Integer> queryLimitList();
	/**
	 * 根据昵称查找用户
	 * @param nickname
	 * @return
	 */
	public User queryUserByNickname(String nickname);

}
