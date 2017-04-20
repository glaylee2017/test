package com.huaiwang.starsky.exception;

public class StarskyException extends Exception{
	private String Msg;

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}
	
}
