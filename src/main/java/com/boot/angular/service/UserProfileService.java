package com.boot.angular.service;

import java.util.Optional;

import com.boot.angular.model.UserProfile;

public interface UserProfileService {
	
	public Optional<UserProfile> findById(String id);
	
	public UserProfile addUserProfile(UserProfile userProfile);

	public void deleteUserProfile(UserProfile userProfile);
	
	public UserProfile updateUserProfile(UserProfile userProfile);
	
}
