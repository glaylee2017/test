package com.huaiwang.starsky.controller.manage;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.controller.web.UserController;
import com.huaiwang.starsky.service.manage.ProRateService;

@Controller
@RequestMapping("/pro")
public class ProRateController {
	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	public ProRateService proRateService;
	
	//后台手动输入生产率,输入例0.0025
	@RequestMapping(value = "/produceRate",method=RequestMethod.POST)
	@ResponseBody
	public SysResult produceRate(double min,double max,Integer type,Date date){
		try {
			if(type != null && date != null){
				return proRateService.createRate(min, max, type, date);
			}
			return SysResult.build(SysResult.FAULT, "数据有误，请重新输入");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return SysResult.build(SysResult.FAULT, "网络异常，请稍后重新操作");
		}
		
	}
	
	
	
}
