package com.huaiwang.starsky.service.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.mapper.manage.ScoreLogMapper;
import com.huaiwang.starsky.pojo.ScoreLog;
import com.huaiwang.starsky.service.common.BaseService;
//金币变动日志service
@Service
public class ScoreLogService extends BaseService<ScoreLog>{
	@Autowired
	private ScoreLogMapper scoreLogMapper;
}
