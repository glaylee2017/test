package com.huaiwang.starsky.pojo;

import java.util.Date;

/**
 * pojo基类
 * 包含创建时间和更新时间，其他pojo都继承它
 *  @author Administrator
 *
 */
public class BasePojo {
	private Date created;//创建时间
	private Date updated;//更新时间
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
