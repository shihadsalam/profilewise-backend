package com.profilewise.model;

public class AuthData {

	private String token;
	private User currentUser;
	private String errorMsg;

	public AuthData() {

	}

	public AuthData(String token, User currentUser, String errorMsg) {
		this.token = token;
		this.currentUser = currentUser;
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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
}
