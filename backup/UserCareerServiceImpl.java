package com.boot.angular.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.UserCareer;
import com.boot.angular.repository.UserCareerRepository;

@Service(value = "userCareerService")
public class UserCareerServiceImpl implements UserCareerService {
	
	@Autowired
	private UserCareerRepository userCareerRepository;
	
	@Override
	@Transactional
	public UserCareer createOrUpdateUserCareer(UserCareer userCareer) {
		return userCareerRepository.save(userCareer);
	}
	
}
