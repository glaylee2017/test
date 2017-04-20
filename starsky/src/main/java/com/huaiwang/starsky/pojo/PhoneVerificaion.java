package com.huaiwang.starsky.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_pv")
public class PhoneVerificaion extends BasePojo{
	//手机验证码表
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String phone;//手机号码
	private String code;//验证码
	private int stype;//短信类型（0注册 1修改手机号 2交易）
	
	public PhoneVerificaion(){};

	public PhoneVerificaion(int id, String phone, String code, int stype) {
		super();
		this.id = id;
		this.phone = phone;
		this.code = code;
		this.stype = stype;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getStype() {
		return stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
