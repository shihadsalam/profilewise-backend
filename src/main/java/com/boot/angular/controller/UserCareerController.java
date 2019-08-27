package com.boot.angular.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boot.angular.model.Message;
import com.boot.angular.model.User;
import com.boot.angular.model.UserCareer;
import com.boot.angular.model.UserCareerDTO;
import com.boot.angular.model.UserProfile;
import com.boot.angular.service.UserProfileService;
import com.boot.angular.service.UserService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/user-career" })
public class UserCareerController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProfileService userProfileService;

	@PostMapping(path = { "/create-career" })
	public User create(@RequestBody UserCareerDTO userCareerDTO) throws ParseException {
		User user = userService.findUserByUserName(userCareerDTO.getUsername());
		UserCareer userCareer = userCareerDTO.getUserCareer();
		UserCareer existingUserCareer = user.getUserCareer();
		if (null != existingUserCareer) {
			userCareer = existingUserCareer.update(userCareer);
		}		
		userCareer.setUser(user);
		user.setUserCareer(userCareer);
		user = userService.updateUser(user);
		return user;
	}

	@GetMapping(path = { "/{username}" })
	public UserCareer findByUser(@PathVariable("username") String username) {
		User user = userService.findUserByUserName(username);
		return user.getUserCareer();
	}

	@PostMapping(path = {"/import-career"})
	public ResponseEntity<Message> importUserCareer(@RequestBody UserCareerDTO userCareerDTO) {
		JsonObject jsonObj = null;
		JsonArray jsonArray = null;
		UserProfile userProfile = null;
		String jsonStr = userCareerDTO.getJson();
		String username = userCareerDTO.getUsername();
		try {
			jsonArray = new JsonArray(jsonStr);
			userProfile = new UserProfile(username, null, jsonArray);
		}
		catch (Exception e) {
			try {
				jsonObj = new JsonObject(jsonStr);
				userProfile = new UserProfile(username, jsonObj, null);
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.ok(new Message("", "Invalid JSON!", ""));
			}
		}
		
		userProfile = userProfileService.addUserProfile(userProfile);
		if (null != userProfile) {
			return ResponseEntity.ok(new Message("Import Success for User "+username, "", ""));
		}
		
		return ResponseEntity.ok(new Message("", "Import Failed for User "+username, ""));
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path = { "/get-imported-career/{username}" })
	public ResponseEntity<List<Map<String, Object>>> getUserProfile(@PathVariable("username") String username) {
		Optional<UserProfile> optUserProfile = userProfileService.findById(username);
		UserProfile userProfile = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		if (optUserProfile.isPresent()) {
			userProfile = optUserProfile.get();
		}
		Object obj = userProfile.getData();
		if (obj instanceof Map<?, ?>) {
			mapList.add((Map<String, Object>) obj);
		}
		else if(obj instanceof List<?>) {
			mapList = (List<Map<String, Object>>) obj;
		}
		
		return ResponseEntity.ok(mapList);
	}

	@PostMapping(path = "/json-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		return ResponseEntity.ok("");
	}

}
