package com.huaiwang.starsky.common.vo;

//交易日志里面的类型
public class ScoreChangeType {

	public static final Integer RECEIVE = 1;// 收获作物获取金币
	public static final Integer DEVELOP_LAND = 2;// 土地开发使用金币
	public static final Integer GROW = 3;// 种植作物使用金币
	public static final Integer TRADE_PAY = 4;// 交易支出
	public static final Integer TRADE_RECEIVE = 5;// 交易获得
	public static final Integer SYS_PAY = 6;// 系统扣费
	public static final Integer SYS_RECEIVE = 7;// 系统赠送
}
