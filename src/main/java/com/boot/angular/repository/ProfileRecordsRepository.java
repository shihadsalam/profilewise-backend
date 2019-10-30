package com.boot.angular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileRecordId;
import com.boot.angular.model.ProfileRecords;

@Repository
public interface ProfileRecordsRepository extends MongoRepository<ProfileRecords, ProfileRecordId> { 
	
	public Optional<List<ProfileRecords>> findAllByIdUsername(String username);
	
	public Optional<ProfileRecords> findById(ProfileRecordId id);

}
