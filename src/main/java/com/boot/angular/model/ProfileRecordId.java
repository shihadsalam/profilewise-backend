package com.boot.angular.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class ProfileRecordId implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private String username;
    private String title;
 
    public ProfileRecordId() {
    	
    }
 
    public ProfileRecordId(String username, String title) {
		this.username = username;
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileRecordId)) return false;
        ProfileRecordId that = (ProfileRecordId) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getTitle(), that.getTitle());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getTitle());
    }
}
