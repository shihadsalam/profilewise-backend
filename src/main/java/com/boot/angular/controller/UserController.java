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
import com.boot.angular.model.Message;
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
		user.getUserContact().setUser(user);
		return userService.addUser(user);
	}
	
	@PostMapping(path = { "/assign-reportee" })
	public ResponseEntity<Message> assignReportee(@RequestBody FormUser formUser) {
		String username = formUser.getUsername();
		User currentUser = getCurrentUser();
		if (username.equals(currentUser.getUsername())) {
			return ResponseEntity.ok(new Message("", "Cannot assign Self as Reportee", ""));
		}
		else {
			if (currentUser.getIsSupervisor()) {
				User userToAssign = userService.findUserByUserName(username);
				currentUser.getReportees().add(userToAssign);
				userService.updateUser(currentUser);
				userToAssign.setSupervisor(currentUser);
				userService.updateUser(userToAssign);
			}
			else {
				return ResponseEntity.ok(new Message("", "User " + currentUser.getUsername() + " is not a Supervisor", ""));
			}
			
		}
		return ResponseEntity.ok(new Message("Reportee assigned for " + currentUser.getUsername(), "", ""));
	}
	
	@PostMapping(path = { "/remove-reportee" })
	public ResponseEntity<Message> removeReportee(@RequestBody FormUser formUser) throws ParseException {
		String username = formUser.getUsername();
		User currentUser = getCurrentUser();
		if (currentUser.getIsSupervisor()) {
			User assignedUser = userService.findUserByUserName(username);
			currentUser.getReportees().removeIf(rep -> rep.getUsername().equals(username));
			userService.updateUser(currentUser);
			assignedUser.setSupervisor(null);
			userService.updateUser(assignedUser);
		}
		else {
			return ResponseEntity.ok(new Message("", "User " + currentUser.getUsername() + " is not a Supervisor", ""));
		}
		return ResponseEntity.ok(new Message("Reportee removed from " + currentUser.getUsername(), "", ""));
	}

	private User createUserEntity(FormUser formUser) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date dob = sdf.parse(formUser.getDob());
		return new User(formUser.getFirstName(), formUser.getLastName(), formUser.getGender(), dob, formUser.getUsername(), 
				formUser.getPassword(), formUser.getUserRole(), formUser.getIsSupervisor(), formUser.getUserContact());
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
		if (username.equals(getCurrentUser().getUsername())) {
			throw new RuntimeException("Self delete not allowed !");
		}
		else {
			User user = findUser(username);
			userService.deleteUser(user);
			return user;
		}
	}

	@GetMapping
	public List<User> findAllUsers() {
		User user = getCurrentUser();
		if (user.getIsSupervisor()) {
			return userService.findAllUsers();
		}
		return Collections.singletonList(user);
	}
	
	private User getCurrentUser() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.findUserByUserName(currentUser);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> assertionException(final IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
