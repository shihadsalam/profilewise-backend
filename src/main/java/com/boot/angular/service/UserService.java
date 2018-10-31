package com.boot.angular.service;

import java.util.List;

import com.boot.angular.model.User;

public interface UserService {
	
	public List<User> findAllUsers();
	
	public List<String> findAllUserNames();
	
	public User addUser(User User);

	public User findUserByUserName(String username);

	public void deleteUser(User User);
	
	public User update(User User);
	
}
