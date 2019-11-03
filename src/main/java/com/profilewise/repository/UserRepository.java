package com.profilewise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.profilewise.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> { 
	
	public User findByUsername(String username);

}
