package com.boot.angular.service;

import java.text.ParseException;
import java.util.List;

import com.boot.angular.model.FormUser;
import com.boot.angular.model.User;

public interface UserService {
	
	public List<User> findAllUsers();
	
	public List<String> findAllUserNames();
	
	public User addUser(User user);

	public User findUserByUserName(String username);

	public void deleteUser(User user);
	
	public User update(FormUser user) throws ParseException;
	
	public User updateUser(User user);

}
