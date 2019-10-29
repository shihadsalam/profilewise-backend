package com.boot.angular.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import io.vertx.core.json.JsonObject;

@Document(collection = "profile_records")
public class ProfileRecords {

	@Id
	private String id;
	private String title;
	private String fieldType;
	private JsonObject data;

	public ProfileRecords() {

	}

	public ProfileRecords(String id, String title, String fieldType, JsonObject data) {
		this.id = id;
		this.title = title;
		this.fieldType = fieldType;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

}
