package com.profilewise.service;

import java.text.ParseException;
import java.util.List;

import com.profilewise.model.FormUser;
import com.profilewise.model.User;

public interface UserService {
	
	public List<User> findAllUsers();
	
	public List<String> findAllUserNames();
	
	public User addUser(User user);

	public User findUserByUserName(String username);

	public void deleteUser(User user);
	
	public User update(FormUser user) throws ParseException;
	
	public User updateUser(User user);

}
