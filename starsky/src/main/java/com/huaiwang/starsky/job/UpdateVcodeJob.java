package com.huaiwang.starsky.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.huaiwang.starsky.mapper.manage.PhoneVerificationMapper;

public class UpdateVcodeJob extends QuartzJobBean {
	private static final Logger log = Logger.getLogger(UpdateVcodeJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("定时删除昨天注册验证码开始------------");
			ApplicationContext applicationContext = 
				(ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
			// 利用joda提供工具類對時間處理，每個1天來觸發這個任務
			applicationContext.getBean(PhoneVerificationMapper.class).updateStype();
			System.out.println("定时删除昨天注册验证码结束------------");
		} catch (Exception e) {
			System.out.println("定时删除昨天注册验证码异常退出------------");
			log.warn("定时删除昨天注册验证码异常退出--------------");
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
