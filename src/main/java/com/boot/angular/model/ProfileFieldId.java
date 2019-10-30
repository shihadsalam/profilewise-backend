package com.boot.angular.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class ProfileFieldId implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private String username;
    private String type;
 
    public ProfileFieldId() {
    	
    }
 
    public ProfileFieldId(String username, String type) {
		this.username = username;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileFieldId)) return false;
        ProfileFieldId that = (ProfileFieldId) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getType(), that.getType());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getType());
    }
}
