package com.huaiwang.starsky.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//金币日志
@Table(name = "tb_score_log")
public class ScoreLog extends BasePojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;// 主键id
	private Integer userId;// 用户id
	private Integer num;// 金币变动数目
	private Integer type;// 类型:1为收获2为开发土地使用3为种植使用4为交易支出5为交易获取6为系统收费7为系统赠送

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
