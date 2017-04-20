package com.huaiwang.starsky.common.util;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 解析properties配置文件
 * @author deno
 *
 */
public class Properties {
	private static Log log = LogFactory.getLog(Properties.class);
	public static ResourceBundle RESOURCE_BUNDLE;
	public Properties(String confName) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(confName);
	}
	/**
	 * 根据key获取值，key不存在则返回null
	 * @param key
	 * @return
	 */
	public static Object getValue(String key) {
		try {
			return RESOURCE_BUNDLE.getObject(key);
		} catch(Exception e) {
			log.warn("读取配置文件出错：" + e.getMessage());
			return null;
		}
	}

}