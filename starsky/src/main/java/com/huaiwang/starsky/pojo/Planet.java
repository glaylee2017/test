package com.huaiwang.starsky.pojo;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//星球对象
@Table(name = "tb_planet")
public class Planet extends BasePojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id
	private Integer farmNum;// 星球已开垦土地数
	private Integer maxFarmNum;// 星球最大开垦土地数
	private Integer level;// 星球等级

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFarmNum() {
		return farmNum;
	}

	public void setFarmNum(Integer farmNum) {
		this.farmNum = farmNum;
	}

	public Integer getMaxFarmNum() {
		return maxFarmNum;
	}

	public void setMaxFarmNum(Integer maxFarmNum) {
		this.maxFarmNum = maxFarmNum;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
