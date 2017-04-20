package com.huaiwang.starsky.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//种植地对象
@Table(name = "tb_farm")
public class Farm extends BasePojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id
	private Integer num;// 种植地编号
	private Integer notReNum;// 种植地还没收获的作物数量
	private Integer growNum;// 种植地上在种植的作物数量
	private Integer maxGrowNum;// 种植地最大种植数量
	private Integer level;// 种植地等级,与用户等级,星球等级对应
	private Integer totalNum;// 种植地总共产出过的作物数量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getNotReNum() {
		return notReNum;
	}

	public void setNotReNum(Integer notReNum) {
		this.notReNum = notReNum;
	}

	public Integer getGrowNum() {
		return growNum;
	}

	public void setGrowNum(Integer growNum) {
		this.growNum = growNum;
	}

	public Integer getMaxGrowNum() {
		return maxGrowNum;
	}

	public void setMaxGrowNum(Integer maxGrowNum) {
		this.maxGrowNum = maxGrowNum;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

}
