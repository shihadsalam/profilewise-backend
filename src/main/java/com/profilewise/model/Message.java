package com.profilewise.model;

public class Message {

	private String msg;
	private String errorMsg;

	public Message() {

	}

	public Message(String msg, String errorMsg) {
		this.msg = msg;
		this.errorMsg = errorMsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
