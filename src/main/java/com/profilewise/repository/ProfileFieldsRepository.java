package com.profilewise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.profilewise.model.ProfileFieldId;
import com.profilewise.model.ProfileFields;

@Repository
public interface ProfileFieldsRepository extends MongoRepository<ProfileFields, ProfileFieldId> { 
	
	public Optional<List<ProfileFields>> findAllByIdUsername(String username);
	
	public Optional<ProfileFields> findById(ProfileFieldId id);

}
