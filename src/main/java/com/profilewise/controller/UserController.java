package com.profilewise.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

import com.profilewise.model.FormUser;
import com.profilewise.model.Message;
import com.profilewise.model.ProfileFields;
import com.profilewise.model.ProfileRecords;
import com.profilewise.model.User;
import com.profilewise.service.UserProfileService;
import com.profilewise.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/users" })
@ControllerAdvice
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProfileService userProfileService;
	

	@PostMapping(path = { "/signup" })
	public User create(@RequestBody FormUser formUser) throws ParseException {
		User user = createUserEntity(formUser);
		user.getUserContact().setUser(user);
		return userService.addUser(user);
	}
	
	@GetMapping(path = { "get-reportees/{username}" })
	public List<User> findReportees(@PathVariable("username") String username) {
		User user = userService.findUserByUserName(username);
		return user.getReportees();
	}

	
	@PostMapping(path = { "/assign-reportee" })
	public ResponseEntity<Message> assignReportee(@RequestBody FormUser formUser) {
		String username = formUser.getUsername();
		User currentUser = getCurrentUser();
		if (username.equals(currentUser.getUsername())) {
			return ResponseEntity.ok(new Message("", "Cannot assign Self as Reportee"));
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
				return ResponseEntity.ok(new Message("", "User " + currentUser.getUsername() + " is not a Supervisor"));
			}
			
		}
		return ResponseEntity.ok(new Message("Reportee assigned for " + currentUser.getUsername(), ""));
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
			return ResponseEntity.ok(new Message("", "User " + currentUser.getUsername() + " is not a Supervisor"));
		}
		return ResponseEntity.ok(new Message("Reportee removed from " + currentUser.getUsername(), ""));
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
	public ResponseEntity<Message> delete(@PathVariable("username") String username) {
		if (username.equals(getCurrentUser().getUsername())) {
			return ResponseEntity.ok(new Message("" , "Self delete not allowed"));
		}
		else {
			User user = findUser(username);
			if(null != user.getReportees() && !user.getReportees().isEmpty()) {
				return ResponseEntity.ok(new Message("" , "User " + user.getUsername() + " has assigned reportees"));
			}
			else if (null != user.getSupervisor()) {
				return ResponseEntity.ok(new Message("" , "User " + user.getUsername() + " is a reportee"));
			}
			else {
				deleteUser(user);
				return ResponseEntity.ok(new Message("User " + user.getUsername() + " deleted succesfully" , ""));
			}
		}
	}
	
	@GetMapping
	public List<User> findAllUsers() {
		User user = getCurrentUser();
		if (user.getIsSupervisor()) {
			List<User> users = userService.findAllUsers().stream().filter(userObj -> 
				(null != userObj.getSupervisor() && userObj.getSupervisor().equals(user)) || 
				(null == userObj.getSupervisor())).collect(Collectors.toList());
			return users;
		}
		return Collections.singletonList(user);
	}
	
	@GetMapping(path = {"get-authorized-users"})
	public List<User> getAuthorizedUsers() {
		User currentUser = getCurrentUser();
		List<User> users = findAllUsers().stream().filter(user -> currentUser.getUserRole().getAuthorizedRoleNames().
				contains(user.getUserRole().getRoleName())).collect(Collectors.toList());
		
		return users;
	}
	
	private void deleteUser(User user) {
		String username = user.getUsername();
		Optional<List<ProfileFields>> optionalFields = userProfileService.findProfileFieldsByUsername(username);
		if(optionalFields.isPresent()) {
			userProfileService.deleteAllProfileFields(optionalFields.get());
		}
		Optional<List<ProfileRecords>> optionalRecords = userProfileService.findProfileRecordsByUsername(username);
		if(optionalRecords.isPresent()) {
			userProfileService.deleteAllProfileRecords(optionalRecords.get());
		}
		userService.deleteUser(user);
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
