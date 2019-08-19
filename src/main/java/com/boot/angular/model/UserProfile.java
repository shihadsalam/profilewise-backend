package com.boot.angular.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Document(collection = "user_profile")
public class UserProfile {
    
	@Id
    private String id;
    private Object data;
    
    public UserProfile() {
    	
    }
    
    public UserProfile(String id, Object data) {
		this.id = id;
		this.data = data;
	}

	public UserProfile(String id, JsonObject jsonObject, JsonArray jsonArray) {
		this.id = id;
		if (null != jsonObject) {
			setData(jsonObject); 
		}
		else {
			setData(jsonArray);
		}
	}
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// data field can be a string
    public void setData(String str) {
        this.data = str;
    }
    
    // data field can be a {}
    public void setData(JsonObject jsonObject) {
    	this.data = new BasicDBObject(jsonObject.getMap());
    }
    
    // data can be a []
    @SuppressWarnings("unchecked")
	public void setData(JsonArray jsonArray) {
        BasicDBList list = new BasicDBList();
        list.addAll(jsonArray.getList());
        this.data = list;
    }
    
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
