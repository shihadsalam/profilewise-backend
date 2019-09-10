package com.boot.angular.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import io.vertx.core.json.JsonObject;

@Document(collection = "profile_glimpse")
public class ProfileGlimpseRecord {

	@Id
	private String id;
	private JsonObject data;

	public ProfileGlimpseRecord() {

	}

	public ProfileGlimpseRecord(String id, JsonObject data) {
		this.id = id;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

}
