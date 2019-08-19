package com.boot.angular.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.UserProfile;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> { 
	
	public Optional<UserProfile> findById(String id);

}
