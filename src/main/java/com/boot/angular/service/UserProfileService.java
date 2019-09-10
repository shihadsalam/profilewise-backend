package com.boot.angular.service;

import java.util.Optional;

import com.boot.angular.model.ProfileGlimpseFields;
import com.boot.angular.model.ProfileGlimpseRecord;
import com.boot.angular.model.UserProfile;

public interface UserProfileService {
	
	public Optional<UserProfile> findUserProfileById(String id);
	
	public Optional<ProfileGlimpseFields> findProfileGlimpseFieldsById(String id);
	
	public Optional<ProfileGlimpseRecord> findProfileGlimpseRecordsById(String id);
	
	public UserProfile addUserProfile(UserProfile userProfile);
	
	public void addProfileGlimpseFields(ProfileGlimpseFields profileGlimpseFields);
	
	public ProfileGlimpseRecord addProfileGlimpseRecord(ProfileGlimpseRecord profileGlimpseRecord);

	public void deleteUserProfile(UserProfile userProfile);
	
	public UserProfile updateUserProfile(UserProfile userProfile);
	
}
