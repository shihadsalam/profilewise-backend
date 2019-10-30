package com.boot.angular.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.ProfileFieldId;
import com.boot.angular.model.ProfileFields;
import com.boot.angular.model.ProfileRecordId;
import com.boot.angular.model.ProfileRecords;
import com.boot.angular.repository.ProfileFieldsRepository;
import com.boot.angular.repository.ProfileRecordsRepository;

@Service(value = "userProfileService")
public class UserProfileServiceImpl implements UserProfileService {

	
	@Autowired
	private ProfileFieldsRepository profileFieldsRepository;
	
	@Autowired
	private ProfileRecordsRepository profileRecordsRepository;

	
	@Override
	@Transactional
	public Optional<ProfileFields> findProfileFieldsById(ProfileFieldId id) {
		return profileFieldsRepository.findById(id);
	}
	
	@Override
	@Transactional
	public Optional<List<ProfileFields>> findProfileFieldsByUsername(String username) {
		return profileFieldsRepository.findAllByIdUsername(username);
	}

	@Override
	@Transactional
	public Optional<List<ProfileRecords>> findProfileRecordsByUsername(String username) {
		return profileRecordsRepository.findAllByIdUsername(username);
	}

	@Override
	@Transactional
	public Optional<ProfileRecords> findProfileRecordsById(ProfileRecordId id) {
		return profileRecordsRepository.findById(id);
	}

	@Override
	@Transactional
	public void addProfileFields(ProfileFields profileFields) {
		profileFieldsRepository.save(profileFields);
	}

	@Override
	@Transactional
	public void addProfileRecord(ProfileRecords profileRecords) {
		profileRecordsRepository.save(profileRecords);
	}

	@Override
	@Transactional
	public void addAllProfileRecord(List<ProfileRecords> profileRecords) {
		profileRecordsRepository.saveAll(profileRecords);
	}
	
}
