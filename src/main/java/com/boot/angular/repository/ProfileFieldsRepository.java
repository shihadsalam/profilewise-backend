package com.boot.angular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileFieldId;
import com.boot.angular.model.ProfileFields;

@Repository
public interface ProfileFieldsRepository extends MongoRepository<ProfileFields, ProfileFieldId> { 
	
	public Optional<List<ProfileFields>> findAllByIdUsername(String username);
	
	public Optional<ProfileFields> findById(ProfileFieldId id);

}
