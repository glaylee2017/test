package com.huaiwang.starsky.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.huaiwang.starsky.service.manage.ProRateService;

public class ProduceRateJob extends QuartzJobBean {
	private static final Logger log = Logger.getLogger(ProduceRateJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// 利用joda提供工具類對時間處理，每個1天來觸發這個任務
			System.out.println("定时产生生产率开始------------");
			ApplicationContext applicationContext = 
				(ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
			applicationContext.getBean(ProRateService.class).createRate(0.0, 0.0, 1, new Date());
			applicationContext.getBean(ProRateService.class).createRate(0.0, 0.0, 2, new Date());
			applicationContext.getBean(ProRateService.class).createRate(0.0, 0.0, 3, new Date());
			System.out.println("定时产生生产率完成------------");
		} catch (Exception e) {
			System.out.println("定时产生生产率异常退出---------");
			log.warn("定时产生生产率异常退出--------------");
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
