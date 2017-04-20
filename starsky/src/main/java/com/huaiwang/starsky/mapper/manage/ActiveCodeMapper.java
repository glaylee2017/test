package com.huaiwang.starsky.mapper.manage;

import org.apache.ibatis.annotations.Param;

import com.huaiwang.starsky.common.mapper.SysMapper;
import com.huaiwang.starsky.pojo.ActiveCode;

public interface ActiveCodeMapper extends SysMapper<ActiveCode>{
	public ActiveCode queryActiveCode(@Param("phoneNum")String phoneNum,@Param("activeCode")String activeCode);
}
