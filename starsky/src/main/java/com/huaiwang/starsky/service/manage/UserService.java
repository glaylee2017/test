package com.huaiwang.starsky.service.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.common.util.MD5Util;
import com.huaiwang.starsky.mapper.manage.UserMapper;
import com.huaiwang.starsky.pojo.ActiveCode;
import com.huaiwang.starsky.pojo.User;
import com.huaiwang.starsky.pojo.UserAccount;
import com.huaiwang.starsky.service.common.BaseService;

@Service
public class UserService extends BaseService<User>{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ActiveCodeService activeCodeService;
	public void saveUser(User user) {
		user.setState(1);// 设置初始状态为已激活
		user.setPassword(MD5Util.getMd5Hash(user.getPhoneNum(), user.getPassword()));// 将明文密码加密后设置回user
		user.setCreated(new Date());// 设置注册时间
		user.setIsChangeP(0);// 设置是否更改过上级（师傅）的初始状态为未更改过
		user.setIsSend(0);// 设置是否已发送实体皮具的初始状态为未发放
		user.setLevel(1);// 设置玩家初始等级，全部都为1，表示在地球
		UserAccount account = new UserAccount();//创建用户账户对象
		switch (user.getActiveCode().getType()) {// 根据用户等级设置初始积分
		case 1:
			account.setScore(500);
			break;
		case 2:
			account.setScore(900);
			break;
		case 3:
			account.setScore(1200);
			break;
		}
		account.setCreated(new Date());//设置创建日期		
		account.setTotalScore(account.getScore());//设置玩家初始总积分
		userMapper.saveUser(user);// 保存玩家信息到玩家表中
		account.setUserId(user.getId());//设置玩家Id
		account.setState(1);//保存玩家状态,已激活
		userAccountService.saveSelective(account);//保存玩家账户信息到玩家账户表中
		Integer id = user.getId();
		List<Integer> pList = new ArrayList<Integer>();
		pList.add(id);// 将注册的玩家id添加到其自身的上级列表中
		// 保存玩家的上级Id列表
		userMapper.saveUserPid(id, pList, new Date());
		//更改激活码的状态,从未使用(0)变为已使用(1)
		ActiveCode activeCode = user.getActiveCode();
		activeCode.setState(1);
		activeCodeService.updateSelective(activeCode);
	}

	// 通过用户帐号(电话)查找用户信息
	public User queryUserByPhoneNum(String phoneNum) {
		return userMapper.queryUserByPhoneNum(phoneNum);
	}


	//修改密码
	public void updatePw(String phoneNum, String password) {
		password = MD5Util.getMd5Hash(phoneNum, password);
		userMapper.updatePw(phoneNum,password);
		
	}
	//根据用户id查找其上级玩家id列表
	public List<Integer> queryPidListById(Integer id) {
		
		return userMapper.queryPidListById(id);
	}
	//获取限制交易用户表
	public List<Integer> queryLimitList() {
		
		return userMapper.queryLimitList();
	}

	public User queryUserByNickname(String nickname) {
		
		return userMapper.queryUserByNickname(nickname);
	}

}
