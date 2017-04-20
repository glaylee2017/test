package com.huaiwang.starsky.service.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.mapper.manage.ActiveCodeMapper;
import com.huaiwang.starsky.pojo.ActiveCode;
import com.huaiwang.starsky.service.common.BaseService;
//激活码操作service
@Service
public class ActiveCodeService extends BaseService<ActiveCode>{
	@Autowired
	private ActiveCodeMapper activeCodeMapper;
	public ActiveCode queryActiveCode(@Param("phoneNum")String phoneNum,@Param("activeCode")String activeCode){
		return activeCodeMapper.queryActiveCode(phoneNum, activeCode);
	}
}
