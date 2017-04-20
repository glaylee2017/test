package com.huaiwang.starsky.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//工作表对象
@Table(name = "tb_work")
public class Work extends BasePojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id
	private Integer userId;// 用户id
	private Integer planetId;// 用户所属星球id
	private Integer farmId;// 用户拥有的种植地Id

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

	public Integer getPlanetId() {
		return planetId;
	}

	public void setPlanetId(Integer planetId) {
		this.planetId = planetId;
	}

	public Integer getFarmId() {
		return farmId;
	}

	public void setFarmId(Integer farmId) {
		this.farmId = farmId;
	}

}
