package com.boot.angular.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.angular.model.FormUser;
import com.boot.angular.model.User;
import com.boot.angular.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/users" })
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = { "/signup" })
	public User create(@RequestBody FormUser formUser) throws ParseException {
		User user = createUserEntity(formUser);
		return userService.addUser(user);
	}

	private User createUserEntity(FormUser formUser) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date dob = sdf.parse(formUser.getDob());
		return new User(formUser.getFirstName(), formUser.getLastName(), dob, formUser.getUsername(), 
				formUser.getPassword(), formUser.getEmail(), formUser.getCountry(), formUser.getIsAdmin());
	}

	@PutMapping(path = { "/edit-user" })
	public User update(@RequestBody FormUser user) throws ParseException {
		return userService.update(user);
	}

	@GetMapping(path = { "/{username}" })
	public User findUser(@PathVariable("username") String username) {
		return userService.findUserByUserName(username);
	}

	@GetMapping(path = { "/get-all-usernames" })
	public List<String> findUserNames() {
		return userService.findAllUserNames();
	}

	@DeleteMapping(path = { "/{username}" })
	public User delete(@PathVariable("username") String username) {
		User user = findUser(username);
		userService.deleteUser(user);
		return user;
	}

	@GetMapping
	public List<User> findAllUsers() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByUserName(currentUser);
		if (user.getIsAdmin()) {
			return userService.findAllUsers();
		}
		return Collections.singletonList(user);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> assertionException(final IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
