package com.profilewise.service;

import java.util.List;
import java.util.Optional;

import com.profilewise.model.ProfileFieldId;
import com.profilewise.model.ProfileFields;
import com.profilewise.model.ProfileRecordId;
import com.profilewise.model.ProfileRecords;

public interface UserProfileService {
	
	
	public Optional<ProfileFields> findProfileFieldsById(ProfileFieldId id);
	
	public Optional<List<ProfileFields>> findProfileFieldsByUsername(String username);
	
	public List<ProfileFields> findAllProfileFields();
	
	public Optional<List<ProfileRecords>> findProfileRecordsByUsername(String username);
	
	public Optional<ProfileRecords> findProfileRecordsById(ProfileRecordId id);
	
	public void addProfileFields(ProfileFields profileFields);
	
	public void addProfileRecord(ProfileRecords profileRecords);
	
	public void addAllProfileRecord(List<ProfileRecords> profileRecords);
	
	public void deleteAllProfileFields(List<ProfileFields> fields);
	
	public void deleteAllProfileRecords(List<ProfileRecords> records);

}
