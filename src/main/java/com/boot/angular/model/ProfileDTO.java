package com.boot.angular.model;

public class ProfileDTO {

	private String username;
	private String json;

	public ProfileDTO() {

	}

	public ProfileDTO(String username, String json) {
		this.username = username;
		this.json = json;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
