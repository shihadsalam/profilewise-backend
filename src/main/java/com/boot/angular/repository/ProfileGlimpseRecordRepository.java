package com.boot.angular.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileGlimpseRecord;

@Repository
public interface ProfileGlimpseRecordRepository extends MongoRepository<ProfileGlimpseRecord, String> { 
	
	public Optional<ProfileGlimpseRecord> findById(String id);

}
