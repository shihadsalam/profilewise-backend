package com.boot.angular.model;

public class AuthData {

	private String token;
	private String errorMsg;

	public AuthData() {

	}

	public AuthData(String token, String errorMsg) {
		this.token = token;
		this.errorMsg = errorMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
