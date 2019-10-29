package com.boot.angular.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.ProfileFields;
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
	public Optional<ProfileFields> findProfileFieldsByIdAndType(String id, String type) {
		return profileFieldsRepository.findByIdAndType(id, type);
	}
	
	@Override
	@Transactional
	public Optional<List<ProfileFields>> findProfileFieldsById(String id) {
		return profileFieldsRepository.findAllById(id);
	}

	@Override
	@Transactional
	public Optional<List<ProfileRecords>> findProfileRecordsById(String id) {
		return profileRecordsRepository.findAllById(id);
	}

	@Override
	@Transactional
	public Optional<ProfileRecords> findProfileRecordsByIdAndTitle(String id, String title) {
		return profileRecordsRepository.findByIdAndTitle(id, title);
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
