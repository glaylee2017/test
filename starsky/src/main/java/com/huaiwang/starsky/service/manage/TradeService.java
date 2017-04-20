package com.huaiwang.starsky.service.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.common.vo.ScoreChangeType;
import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.pojo.ScoreLog;
import com.huaiwang.starsky.pojo.User;
import com.huaiwang.starsky.pojo.UserAccount;
//交易service
@Service
public class TradeService {

	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ScoreLogService scoreLogService;
	private String msg = "";

	/**
	 * 交易
	 * 
	 * @param id
	 *            交易发起者id
	 * @param receiveNum
	 *            交易接受者电话
	 * @param nickname
	 *            交易接受者昵称
	 * @param tradeNum
	 *            交易金额
	 * @return
	 */
	public synchronized SysResult toTrade(Integer id, String receiveNum, String nickname, Integer tradeNum) {
		// 根据交易双方查找其对应用户信息
		User receiveUser = userService.queryUserByPhoneNum(receiveNum);
		UserAccount account1 = userAccountService.queryAccountByUserId(id);
		UserAccount account2 = userAccountService.queryAccountByUserId(receiveUser.getId());
		// 判断发起交易者的可流通金币数是否够这次交易
		if (account1.getScore() < tradeNum) {
			msg = "对不起,您的可流通金币数不足!";
			return SysResult.build(SysResult.FAULT, msg);
		}
		// 判断交易接受者是否存在
		if (receiveUser == null) {
			msg = "目标账户不存在!";
			return SysResult.build(SysResult.FAULT, msg);
		}
		// 判断用户输入的昵称是否对应交易接受者的昵称
		if (!nickname.equals(receiveUser.getNickname())) {
			msg = "对方昵称有误!";
			return SysResult.build(SysResult.FAULT, msg);
		}
		// 判断交易接受者帐号是否是已激活(不是未激活或冻结)
		if (receiveUser.getState() != 1) {
			msg = "目标账户异常!中止交易!";
			return SysResult.build(SysResult.FAULT, msg);
		}

		// 分别根据两者的id查找各自的上级玩家id列表
		List<Integer> pList1 = userService.queryPidListById(id);
		List<Integer> pList2 = userService.queryPidListById(receiveUser.getId());
		// 获取限制交易的用户Id列表
		List<Integer> limitList = userService.queryLimitList();
		// 声明两个布尔变量,代表交易双方及其上线是否被限制交易了
		boolean flag1 = false;
		boolean flag2 = false;
		// 声明两个list,用来存放双方被限制交易的上级玩家id列表
		List<Integer> limitIds1 = new ArrayList<Integer>();
		List<Integer> limitIds2 = new ArrayList<Integer>();
		// 分别做两个for循环,判断交易双方的上级玩家中有没有存在于被限制交易列表中,若有,标记为限制交易,并把其上级id记录
		for (int i = 0; i < pList1.size(); i++) {
			for (int j = 0; j < limitList.size(); j++) {
				if (pList1.get(i) == limitList.get(j)) {
					flag1 = true;
					limitIds1.add(pList1.get(i));
				}
			}
		}
		for (int i = 0; i < pList2.size(); i++) {
			for (int j = 0; j < limitList.size(); j++) {
				if (pList2.get(i) == limitList.get(j)) {
					flag2 = true;
					limitIds2.add(pList2.get(i));
				}
			}
		}
		// 判断双方是否被限制交易
		if (!flag1 && !flag2) {// 两者都没限制交易,正常交易
			account1.setScore(account1.getScore() - tradeNum);
			account1.setTotalScore(account1.getTotalScore() - tradeNum);
			account2.setScore(account2.getScore() + tradeNum - (int) (tradeNum * 0.1));
			account2.setTotalScore(account2.getTotalScore() + tradeNum - (int) (tradeNum * 0.1));
			account1.setUpdated(new Date());
			account2.setUpdated(new Date());
			userAccountService.updateSelective(account1);
			userAccountService.updateSelective(account2);
			// 写日志 
			ScoreLog scoreLog = new ScoreLog();
			scoreLog.setUserId(id);
			scoreLog.setNum(0-tradeNum);
			scoreLog.setCreated(new Date());
			scoreLog.setType(ScoreChangeType.TRADE_PAY);
			scoreLogService.saveSelective(scoreLog);
			scoreLog.setId(null);
			scoreLog.setUserId(receiveUser.getId());
			scoreLog.setNum(tradeNum - (int) (tradeNum * 0.1));
			scoreLog.setType(ScoreChangeType.TRADE_RECEIVE);
			scoreLogService.saveSelective(scoreLog);
			msg = "交易成功!";
			return SysResult.build(SysResult.SUCCESS, msg);
		}
		// 若有人是被限制交易,则遍历各自的被限制玩家id表,若被限制的上级玩家是同一个,可以交易,若没有,则不进行交易
		for (int i = 0; i < limitIds1.size(); i++) {
			for (int j = 0; j < limitIds2.size(); j++) {
				if (limitIds1.get(i) == limitIds2.get(j)) {// 发现被限制的上级玩家是同一个,进行交易,并跳出循环
					account1.setScore(account1.getScore() - tradeNum);
					account1.setTotalScore(account1.getTotalScore() - tradeNum);
					account2.setScore(account2.getScore() + tradeNum - (int) (tradeNum * 0.1));
					account2.setTotalScore(account2.getTotalScore() + tradeNum - (int) (tradeNum * 0.1));
					account1.setUpdated(new Date());
					account2.setUpdated(new Date());
					userAccountService.updateSelective(account1);
					userAccountService.updateSelective(account2);
					// 写日志 
					ScoreLog scoreLog = new ScoreLog();
					scoreLog.setUserId(id);
					scoreLog.setNum(0-tradeNum);
					scoreLog.setCreated(new Date());
					scoreLog.setType(ScoreChangeType.TRADE_PAY);
					scoreLogService.saveSelective(scoreLog);
					scoreLog.setId(null);
					scoreLog.setUserId(receiveUser.getId());
					scoreLog.setNum(tradeNum - (int) (tradeNum * 0.1));
					scoreLog.setType(ScoreChangeType.TRADE_RECEIVE);
					scoreLogService.saveSelective(scoreLog);
					//返回结果
					msg = "交易成功!";
					return SysResult.build(SysResult.SUCCESS, msg);
				}
			}
		}
		// 这时说明两者不符合交易规则,不进行交易
		if (flag1) {
			msg = "交易双方不符合交易规则:您的帐号处于限制交易状态,只能与同门师兄弟进行交易,交易取消!";
		} else {
			msg = "交易双方不符合交易规则:对方帐号处于限制交易状态,交易取消!";
		}
		return SysResult.build(SysResult.FAULT, msg);
	}

}
