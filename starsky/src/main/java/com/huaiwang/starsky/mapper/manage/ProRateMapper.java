package com.huaiwang.starsky.mapper.manage;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.huaiwang.starsky.common.mapper.SysMapper;
import com.huaiwang.starsky.pojo.ProRate;

public interface ProRateMapper extends SysMapper<ProRate> {

	// 根据时间查找对象
	public ProRate findProByUpdated(@Param("type") Integer type, @Param("data") Date date);
}
