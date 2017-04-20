package com.huaiwang.starsky.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;


// org.json 第三方库请自行下载编译，或者在以下链接下载使用 jdk 1.7 的版本
// http://share.weiyun.com/630a8c65e9fd497f3687b3546d0b839e
import org.json.JSONObject;

import com.huaiwang.starsky.common.vo.SmsSingleSenderResult;
 
public class SmsSingleSender {
	int appid;
	String appkey;
    String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendisms";
	
	SmsSenderUtil util = new SmsSenderUtil();

	public SmsSingleSender(int appid, String appkey) throws Exception {
		this.appid = appid;
		this.appkey = appkey;
	}

	/**
	 * 普通单发短信接口，明确指定内容，如果有多个签名，请在内容中以【】的方式添加到信息内容中，否则系统将使用默认签名
	 * @param type 短信类型，0 为普通短信，1 营销短信
	 * @param nationCode 国家码，如 86 为中国
	 * @param phoneNumber 不带国家码的手机号
	 * @param msg 信息内容，必须与申请的模板格式一致，否则将返回错误
	 * @param extend 扩展码，可填空
	 * @param ext 服务端原样返回的参数，可填空
	 * @return {@link}SmsSingleSenderResult
	 * @throws Exception
	 */
	public SmsSingleSenderResult send(
			int type,
			String phoneNumber,
			String msg,
			String extend,
			String ext) throws Exception {
		
		// 校验 type 类型
		if (0 != type && 1 != type) {
			throw new Exception("type " + type + " error");
		}
		if (null == extend) {
			extend = "";
		}		
		if (null == ext) {
			ext = "";
		}

		// 按照协议组织 post 请求包体
        long random = util.getRandom();
        long curTime = System.currentTimeMillis()/1000;

		JSONObject data = new JSONObject();

//        JSONObject tel = new JSONObject();
//        tel.put("nationcode", nationCode);
//        tel.put("mobile", phoneNumber);

        data.put("type", type);
        data.put("msg", msg);
        
        String sigstr = String.format(
        		"appkey=%s&random=%d&time=%d&tel=%s",
        		appkey, random, curTime, phoneNumber);
//        System.out.println("sigstr:"+sigstr);
        
        data.put("sig", util.strToHash(sigstr));
        data.put("tel", phoneNumber);
        data.put("time", curTime);
        data.put("extend", extend);
        data.put("ext", ext);

        // 与上面的 random 必须一致
		String wholeUrl = String.format("%s?sdkappid=%d&random=%d", url, appid, random);
//		System.out.println("wholeUrl:" + wholeUrl);
        HttpURLConnection conn = util.getPostHttpConn(wholeUrl);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        wr.write(data.toString());
        wr.flush();
        
//        System.out.println(data.toString());

        // 显示 POST 请求返回的内容
        StringBuilder sb = new StringBuilder();
        int httpRspCode = conn.getResponseCode();
        SmsSingleSenderResult result;
        if (httpRspCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONObject json = new JSONObject(sb.toString());
            result = util.jsonToSmsSingleSenderResult(json);
        } else {
        	result = new SmsSingleSenderResult();
        	result.result = httpRspCode;
        	result.errMsg = "http error " + httpRspCode + " " + conn.getResponseMessage();
        }
        
        return result;
	}

}
