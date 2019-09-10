package com.boot.angular.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileGlimpseFields;

@Repository
public interface ProfileGlimpseFieldsRepository extends MongoRepository<ProfileGlimpseFields, String> { 
	
	public Optional<ProfileGlimpseFields> findById(String id);

}
