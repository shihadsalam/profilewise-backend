package com.boot.angular.model;

public class ProfileRecordDTO {

	private String username;
	private String fieldType;
	private String json;

	public ProfileRecordDTO() {

	}

	public ProfileRecordDTO(String username, String fieldType, String json) {
		this.username = username;
		this.fieldType = fieldType;
		this.json = json;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
