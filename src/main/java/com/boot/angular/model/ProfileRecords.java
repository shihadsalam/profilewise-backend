package com.boot.angular.model;

import javax.persistence.EmbeddedId;

import org.springframework.data.mongodb.core.mapping.Document;

import io.vertx.core.json.JsonObject;

@Document(collection = "profile_records")
public class ProfileRecords {

	@EmbeddedId
	private ProfileRecordId id;
	private String fieldType;
	private JsonObject data;

	public ProfileRecords() {

	}

	public ProfileRecords(ProfileRecordId id, String fieldType, JsonObject data) {
		this.id = id;
		this.fieldType = fieldType;
		this.data = data;
	}

	public ProfileRecordId getId() {
		return id;
	}

	public void setId(ProfileRecordId id) {
		this.id = id;
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
