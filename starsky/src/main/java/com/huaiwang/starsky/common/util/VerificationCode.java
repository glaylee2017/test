package com.huaiwang.starsky.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huaiwang.starsky.common.vo.SmsSingleSenderResult;

public class VerificationCode{
	
	//短信参数
	private int appid = 1400027467;
	private String appkey = "a5c3cefefba5ce43f262af83671b6559";

	/**
	 * 获取一个6位数随机验证码
	 * @return
	 */
	public String getvcode(){
        Random random = new Random();
        String vcode = "";
        for(int i=0; i<6; i++){
        	int s = random.nextInt(9);
        	vcode += s;
        }
        return vcode;
	}
	
	//发送短信
	public SmsSingleSenderResult sensms(String phoneNumber, String fsmsg){
		// 初始化单发
    	try {
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
	    	SmsSingleSenderResult singleSenderResult;
	    	
	    	singleSenderResult = singleSender.send(0, phoneNumber, fsmsg, "", "");
	    	return singleSenderResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
	
	//判断手机号是否符合要求
	public  static final boolean checkPhone(String phone){
		//String regExp  = "^[1][3,4,5,7,8][0-9]{9}$";
		Properties properties = new Properties("starsky");
		String value = (String) properties.getValue("phoneregExp");
		Pattern p = Pattern.compile(value);  
        Matcher m = p.matcher(phone);  
        return m.matches(); 
	}
	
}
