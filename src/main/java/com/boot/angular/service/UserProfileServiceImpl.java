package com.boot.angular.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.UserProfile;
import com.boot.angular.repository.UserProfileRepository;

@Service(value = "userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	

	@Override
	@Transactional
	public Optional<UserProfile> findById(String id) {
		return userProfileRepository.findById(id);
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

}
