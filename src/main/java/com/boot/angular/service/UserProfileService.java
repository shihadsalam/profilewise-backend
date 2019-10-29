package com.boot.angular.service;

import java.util.List;
import java.util.Optional;

import com.boot.angular.model.ProfileFields;
import com.boot.angular.model.ProfileRecords;

public interface UserProfileService {
	
	
	public Optional<ProfileFields> findProfileFieldsByIdAndType(String id, String type);
	
	public Optional<List<ProfileFields>> findProfileFieldsById(String id);
	
	public Optional<List<ProfileRecords>> findProfileRecordsById(String id);
	
	public Optional<ProfileRecords> findProfileRecordsByIdAndTitle(String id, String title);
	
	public void addProfileFields(ProfileFields profileFields);
	
	public void addProfileRecord(ProfileRecords profileRecords);
	
	public void addAllProfileRecord(List<ProfileRecords> profileRecords);

}
