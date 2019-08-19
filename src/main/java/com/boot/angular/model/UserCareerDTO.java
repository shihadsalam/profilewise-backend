package com.boot.angular.model;

public class UserCareerDTO {

	private String username;
	private String json;
	private UserCareer userCareer;

	public UserCareerDTO() {

	}

	public UserCareerDTO(String username, String json, UserCareer userCareer) {
		this.username = username;
		this.userCareer = userCareer;
		this.json = json;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserCareer getUserCareer() {
		return userCareer;
	}

	public void setUserCareer(UserCareer userCareer) {
		this.userCareer = userCareer;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
