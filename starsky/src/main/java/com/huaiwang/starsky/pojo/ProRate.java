package com.huaiwang.starsky.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//生产率
@Table(name = "tb_pro_rate")
public class ProRate extends BasePojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;       //主键
	private double minRate;   //最小生产率
	private double maxRate;   //最大生产率
	private double rate;      //当天生产率
	private Integer type;     //类型0地球 1月球 2火星
	
	public ProRate() {
	}
	public ProRate(Integer id, double minRate, double maxRate, double rate, Integer type) {
		super();
		this.id = id;
		this.minRate = minRate;
		this.maxRate = maxRate;
		this.rate = rate;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getMinRate() {
		return minRate;
	}
	public void setMinRate(double minRate) {
		this.minRate = minRate;
	}
	public double getMaxRate() {
		return maxRate;
	}
	public void setMaxRate(double maxRate) {
		this.maxRate = maxRate;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
