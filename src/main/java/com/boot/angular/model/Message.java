package com.boot.angular.model;

public class Message {

	private String msg;
	private String errorMsg;
	private String deleteMsg;

	public Message() {

	}

	public Message(String msg, String errorMsg, String deleteMsg) {
		this.msg = msg;
		this.errorMsg = errorMsg;
		this.deleteMsg = deleteMsg;
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

	public String getDeleteMsg() {
		return deleteMsg;
	}

	public void setDeleteMsg(String deleteMsg) {
		this.deleteMsg = deleteMsg;
	}
	
}
