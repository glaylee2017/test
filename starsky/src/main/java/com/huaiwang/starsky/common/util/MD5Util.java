package com.huaiwang.starsky.common.util;
import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Util {
	
	public static void main(String[] args) {
		
		/*source  明文密码
		 *salt    盐   作料自己定义
		 *hashIterations  哈希次数
		 */
		Md5Hash md5Hash = new Md5Hash("admin","admin", 3);
		System.out.println(md5Hash);	
	}
	
	
	public static String getMd5Hash(String userName,String password){
		Md5Hash md5Hash = new Md5Hash(password,userName, 3);
		return md5Hash.toString();
	}
}
