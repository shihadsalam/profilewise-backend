package com.boot.angular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileRecords;

@Repository
public interface ProfileRecordsRepository extends MongoRepository<ProfileRecords, String> { 
	
	public Optional<List<ProfileRecords>> findAllById(String id);
	
	public Optional<ProfileRecords> findByIdAndTitle(String id, String title);

}
