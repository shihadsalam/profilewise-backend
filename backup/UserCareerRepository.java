package com.boot.angular.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boot.angular.model.User;
import com.boot.angular.model.UserCareer;

@Repository
public interface UserCareerRepository extends CrudRepository<UserCareer, Long> { 
	
	public UserCareer findByUser(User user);

}
