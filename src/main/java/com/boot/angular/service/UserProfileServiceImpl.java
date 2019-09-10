package com.boot.angular.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.ProfileGlimpseFields;
import com.boot.angular.model.ProfileGlimpseRecord;
import com.boot.angular.model.UserProfile;
import com.boot.angular.repository.ProfileGlimpseFieldsRepository;
import com.boot.angular.repository.ProfileGlimpseRecordRepository;
import com.boot.angular.repository.UserProfileRepository;

@Service(value = "userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private ProfileGlimpseFieldsRepository profileGlimpseFieldsRepository;
	
	@Autowired
	private ProfileGlimpseRecordRepository profileGlimpseRecordRepository;
	

	@Override
	@Transactional
	public Optional<UserProfile> findUserProfileById(String id) {
		return userProfileRepository.findById(id);
	}
	
	@Override
	@Transactional
	public Optional<ProfileGlimpseFields> findProfileGlimpseFieldsById(String id) {
		return profileGlimpseFieldsRepository.findById(id);
	}

	@Override
	@Transactional
	public UserProfile addUserProfile(UserProfile userProfile) {
		return userProfileRepository.save(userProfile);
	}

	@Override
	@Transactional
	public void deleteUserProfile(UserProfile userProfile) {
		userProfileRepository.delete(userProfile);
	}

	@Override
	@Transactional
	public UserProfile updateUserProfile(UserProfile userProfile) {
		return userProfileRepository.save(userProfile);
	}

	@Override
	@Transactional
	public void addProfileGlimpseFields(ProfileGlimpseFields profileGlimpseFields) {
		profileGlimpseFieldsRepository.save(profileGlimpseFields);
	}

	@Override
	@Transactional
	public ProfileGlimpseRecord addProfileGlimpseRecord(ProfileGlimpseRecord profileGlimpseRecord) {
		return profileGlimpseRecordRepository.save(profileGlimpseRecord);
	}

	@Override
	@Transactional
	public Optional<ProfileGlimpseRecord> findProfileGlimpseRecordsById(String id) {
		return profileGlimpseRecordRepository.findById(id);
	}

}
