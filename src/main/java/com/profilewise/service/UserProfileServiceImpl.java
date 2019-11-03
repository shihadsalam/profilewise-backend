package com.profilewise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profilewise.model.ProfileFieldId;
import com.profilewise.model.ProfileFields;
import com.profilewise.model.ProfileRecordId;
import com.profilewise.model.ProfileRecords;
import com.profilewise.repository.ProfileFieldsRepository;
import com.profilewise.repository.ProfileRecordsRepository;

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
	public List<ProfileFields> findAllProfileFields() {
		return profileFieldsRepository.findAll();
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
	
	@Override
	@Transactional
	public void deleteAllProfileFields(List<ProfileFields> fields) {
		profileFieldsRepository.deleteAll(fields);
	}
	
	@Override
	@Transactional
	public void deleteAllProfileRecords(List<ProfileRecords> records) {
		profileRecordsRepository.deleteAll(records);
	}
	
}
