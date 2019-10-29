package com.boot.angular.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.ProfileFields;

@Repository
public interface ProfileFieldsRepository extends MongoRepository<ProfileFields, String> { 
	
	public Optional<List<ProfileFields>> findAllById(String id);
	
	public Optional<ProfileFields> findByIdAndType(String id, String type);

}
