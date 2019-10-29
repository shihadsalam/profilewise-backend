package com.boot.angular.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.angular.model.FormUser;
import com.boot.angular.model.User;
import com.boot.angular.repository.UserRepository;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService,UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	@Transactional
	public User addUser(User user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			users.add(user);
		}
		return users;
	}

	@Override
	@Transactional
	public List<String> findAllUserNames() {
		List<String> usernames = new ArrayList<>();
		for (User user : findAllUsers()) {
			usernames.add(user.getUsername());
		}
		return usernames;
	}

	@Override
	@Transactional
	public User findUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public User update(FormUser user) throws ParseException {
		User existingUser = findUserByUserName(user.getUsername());
		if (null != existingUser) {
			existingUser.update(user);
			existingUser = userRepository.save(existingUser);
			return existingUser;
		}
		return null;
	}
	
	@Override
	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findUserByUserName(username);
		if (null == user) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}
	
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

}
