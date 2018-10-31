package com.boot.angular.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> { 
	
	public User findByUsername(String username);

}
