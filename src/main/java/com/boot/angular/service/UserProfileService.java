package com.boot.angular.service;

import java.util.List;
import java.util.Optional;

import com.boot.angular.model.ProfileFieldId;
import com.boot.angular.model.ProfileFields;
import com.boot.angular.model.ProfileRecordId;
import com.boot.angular.model.ProfileRecords;

public interface UserProfileService {
	
	
	public Optional<ProfileFields> findProfileFieldsById(ProfileFieldId id);
	
	public Optional<List<ProfileFields>> findProfileFieldsByUsername(String username);
	
	public Optional<List<ProfileRecords>> findProfileRecordsByUsername(String username);
	
	public Optional<ProfileRecords> findProfileRecordsById(ProfileRecordId id);
	
	public void addProfileFields(ProfileFields profileFields);
	
	public void addProfileRecord(ProfileRecords profileRecords);
	
	public void addAllProfileRecord(List<ProfileRecords> profileRecords);

}
