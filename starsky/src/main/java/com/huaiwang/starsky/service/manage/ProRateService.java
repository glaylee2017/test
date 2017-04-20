package com.huaiwang.starsky.service.manage;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaiwang.starsky.common.util.Properties;
import com.huaiwang.starsky.common.vo.SysResult;
import com.huaiwang.starsky.mapper.manage.ProRateMapper;
import com.huaiwang.starsky.pojo.ProRate;

@Service
public class ProRateService {
	Properties properties = new Properties("starsky");

	@Autowired
	private ProRateMapper proRateMapper;

	/*
	 * 产生生产率,最大产生率超出限制最大产生率（在配置文件中修改）,产生后存入数据库 minRate maxRate type 1地球 2月球 3火星
	 * minRate,mmaxRate都为0.0表示默认油系统自动产生生产率，date表示你想设置那天的日期
	 */
	public SysResult createRate(double minRate, double maxRate, Integer type, Date updated) {
		String sysRate = (String) properties.getValue("maxProRate");
		double proRate = Double.parseDouble(sysRate);
		double min = 0.0;
		double max = 0.0;
		int mintemp = 0;
		int maxtemp = 0;
		double doubletemp = 0.0;
		double result = 0.0;
		int temp = 0;
		Random random = new Random();
		ProRate pro = null;

		if (maxRate > proRate) {
			return SysResult.build(SysResult.FAULT, "超过最大生产率，请重新输入");
		}
		switch (type) {
		case 1:// 地球
			System.out.println("地球生产率开始产生--------------");
			if (minRate == 0.0 && maxRate == 0.0) {// 代表系统自动产生
				// 查询是否有今天的产生率 有就不在产生，无就产生
				ProRate tempPro = proRateMapper.findProByUpdated(1, new Date());
				if (tempPro == null) {
					String emin = (String) properties.getValue("earthmin");
					double earthmin = Double.parseDouble(emin);
					min = earthmin * 1000;// 百分之2
					String emax = (String) properties.getValue("earthmax");
					double earthmax = Double.parseDouble(emax);
					max = earthmax * 1000;// 百分之2.5
					mintemp = (int) min;
					maxtemp = (int) max;
				} else {
					System.out.println("地球生产率已存在，自动退出--------------");
					return SysResult.build(SysResult.FAULT, "今日生产率已存在");
				}
			} else {
				min = minRate * 1000;// 百分之2
				max = maxRate * 1000;// 百分之2.5
				mintemp = (int) min;
				maxtemp = (int) max;
			}
			while (temp < mintemp) {
				int num = random.nextInt(maxtemp);
				if (maxtemp > num && num > mintemp) {
					temp = num;
					break;
				}
			}
			doubletemp = temp;
			result = doubletemp / 1000;
			pro = new ProRate();
			pro.setCreated(new Date());
			pro.setMaxRate(maxRate);
			pro.setMinRate(minRate);
			pro.setRate(result);
			pro.setType(type);
			pro.setUpdated(updated);
			proRateMapper.insert(pro);
			System.out.println("地球生产率结束--------------");
			return SysResult.build(SysResult.SUCCESS, "生产率产生成功");
		case 2:// 月球
			System.out.println("月球生产率开始产生--------------");
			if (minRate == 0.0 && maxRate == 0.0) {
				ProRate tempPro = proRateMapper.findProByUpdated(2, new Date());
				if (tempPro == null) {
					String mmin = (String) properties.getValue("monmin");
					double monmin = Double.parseDouble(mmin);
					min = monmin * 1000;// 百分之2.5
					String mmax = (String) properties.getValue("monmax");
					double monmax = Double.parseDouble(mmax);
					max = monmax * 1000;// 百分之3
					mintemp = (int) min;
					maxtemp = (int) max;
				} else {
					System.out.println("月球生产率已存在，自动退出--------------");
					return SysResult.build(SysResult.FAULT, "今日生产率已存在");
				}
			} else {
				min = minRate * 1000;// 百分之2
				max = maxRate * 1000;// 百分之2.5
				mintemp = (int) min;
				maxtemp = (int) max;
			}
			while (temp < mintemp) {
				int num = random.nextInt(maxtemp);
				if (maxtemp > num && num > mintemp) {
					temp = num;
					break;
				}
			}
			doubletemp = temp;
			result = doubletemp / 1000;
			pro = new ProRate();
			pro.setCreated(new Date());
			pro.setMaxRate(maxRate);
			pro.setMinRate(minRate);
			pro.setRate(result);
			pro.setType(type);
			pro.setUpdated(updated);
			proRateMapper.insert(pro);
			System.out.println("月球生产率结束--------------");
			return SysResult.build(SysResult.SUCCESS, "生产率产生成功");
		case 3:// 火星
			System.out.println("火星生产率开始产生--------------");
			if (minRate == 0.0 && maxRate == 0.0) {
				ProRate tempPro = proRateMapper.findProByUpdated(3, new Date());
				if (tempPro == null) {
					String marmin = (String) properties.getValue("marsmin");
					double marsmin = Double.parseDouble(marmin);
					min = marsmin * 1000;// 百分之3
					String marmax = (String) properties.getValue("marsmax");
					double marsmax = Double.parseDouble(marmax);
					max = marsmax * 1000;// 百分之3.5
					mintemp = (int) min;
					maxtemp = (int) max;
				} else {
					System.out.println("火星生产率已存在，自动退出--------------");
					return SysResult.build(SysResult.FAULT, "今日生产率已存在");
				}
			} else {
				min = minRate * 1000;// 百分之2
				max = maxRate * 1000;// 百分之2.5
				mintemp = (int) min;
				maxtemp = (int) max;
			}
			while (temp < mintemp) {
				int num = random.nextInt(maxtemp);
				if (maxtemp > num && num > mintemp) {
					temp = num;
					break;
				}
			}
			doubletemp = temp;
			result = doubletemp / 1000;
			pro = new ProRate();
			pro.setCreated(new Date());
			pro.setMaxRate(maxRate);
			pro.setMinRate(minRate);
			pro.setRate(result);
			pro.setType(type);
			pro.setUpdated(updated);
			proRateMapper.insert(pro);
			System.out.println("火星生产率结束--------------");
			return SysResult.build(SysResult.SUCCESS, "生产率产生成功");
		}
		return SysResult.build(SysResult.FAULT, "数据有误，请重新输入");
	}

}
