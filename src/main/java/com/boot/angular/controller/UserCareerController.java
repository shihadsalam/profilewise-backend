package com.boot.angular.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import com.boot.angular.model.ProfileGlimpseFields;
import com.boot.angular.model.ProfileGlimpseRecord;
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

	@PostMapping(path = { "/import-career" })
	public ResponseEntity<Message> importUserCareer(@RequestBody UserCareerDTO userCareerDTO) {
		JsonObject jsonObj = null;
		JsonArray jsonArray = null;
		UserProfile userProfile = null;
		String jsonStr = userCareerDTO.getJson();
		String username = userCareerDTO.getUsername();
		try {
			jsonArray = new JsonArray(jsonStr);
			userProfile = new UserProfile(username, null, jsonArray);
		} catch (Exception e) {
			try {
				jsonObj = new JsonObject(jsonStr);
				userProfile = new UserProfile(username, jsonObj, null);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.ok(new Message("", "Invalid JSON", ""));
			}
		}

		userProfile = userProfileService.addUserProfile(userProfile);
		if (null != userProfile) {
			return ResponseEntity.ok(new Message("Import Success for User " + username, "", ""));
		}

		return ResponseEntity.ok(new Message("", "Import Failed for User " + username, ""));
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = { "/get-imported-career/{username}" })
	public ResponseEntity<List<Map<String, Object>>> getUserProfile(@PathVariable("username") String username) {
		Optional<UserProfile> optUserProfile = userProfileService.findUserProfileById(username);
		UserProfile userProfile = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (optUserProfile.isPresent()) {
			userProfile = optUserProfile.get();
		}
		Object obj = userProfile.getData();
		if (obj instanceof Map<?, ?>) {
			mapList.add((Map<String, Object>) obj);
		} else if (obj instanceof List<?>) {
			mapList = (List<Map<String, Object>>) obj;
		}

		return ResponseEntity.ok(mapList);
	}

	@GetMapping(path = { "/get-glimpse-fields/{username}" })
	public ResponseEntity<List<String>> getProfileGlimpseField(@PathVariable("username") String username) {
		List<String> fieldLists = new ArrayList<String>();
		Optional<ProfileGlimpseFields> fields = userProfileService.findProfileGlimpseFieldsById(username);
		if (fields.isPresent()) {
			ProfileGlimpseFields glipmseFields = fields.get();
			return ResponseEntity.ok(populateNonEmptyFields(glipmseFields, fieldLists));
		}
		return null;
	}

	@GetMapping(path = { "/get-glimpse-records/{username}" })
	public Map<String, Object> getProfileGlimpseRecord(@PathVariable("username") String username) {
		Optional<ProfileGlimpseRecord> records = userProfileService.findProfileGlimpseRecordsById(username);
		if (records.isPresent()) {
			ProfileGlimpseRecord glipmseRecord = records.get();
			return glipmseRecord.getData().getMap();
		}
		return null;
	}

	@PostMapping(path = { "/add-glimpse-fields" })
	public Map<String, Object> addProfileGlimpseFields(@RequestBody ProfileGlimpseFields profileGlimpseFields) {
		Map<String, Object> glimpseRecordMap;
		String username = profileGlimpseFields.getId();
		if (validateGlimpseFields(profileGlimpseFields)) {
			glimpseRecordMap = new HashMap<String, Object>();
			try {
				userProfileService.addProfileGlimpseFields(profileGlimpseFields);
				Map<String, Object> existingRecordMap = getProfileGlimpseRecord(username);
				if (null != existingRecordMap && !existingRecordMap.isEmpty()) {
					populateGlimpseRecordMap(existingRecordMap, profileGlimpseFields, glimpseRecordMap);
				}
				else {
					populateEmptyGlimpseRecordMap(profileGlimpseFields, glimpseRecordMap);
				}
				ProfileGlimpseRecord glipmseRecord =  userProfileService.addProfileGlimpseRecord(new ProfileGlimpseRecord(username, 
						new JsonObject(glimpseRecordMap)));
				return glipmseRecord.getData().getMap();
				
			} catch (Exception e) {
				throw new RuntimeException("Operation Failed");
			}
		}
		return null;
	}

	private void populateGlimpseRecordMap(Map<String, Object> existingRecordMap, 
			ProfileGlimpseFields profileGlimpseFields, Map<String, Object> glimpseRecordMap) {
		for (String field : profileGlimpseFields.getFieldsList()) {
			if (!StringUtils.isEmpty(field)) {
				if (existingRecordMap.containsKey(field)) {
					glimpseRecordMap.put(field, existingRecordMap.get(field));
				}
				else {
					glimpseRecordMap.put(field, "");
				}
			}
		}
	}

	@PostMapping(path = { "/add-glimpse-record" })
	public ResponseEntity<Message> addProfileGlimpseRecord(@RequestBody String jsonData) {
		JsonObject jsonObj = null;
		ProfileGlimpseRecord glimpseRecord = null;
		try {
			jsonObj = new JsonObject(jsonData);
			String username = jsonObj.getString("id", "");
			jsonObj.remove("id");
			glimpseRecord = new ProfileGlimpseRecord(username, jsonObj);
			userProfileService.addProfileGlimpseRecord(glimpseRecord);
			return ResponseEntity.ok(new Message("Profile Glimpse added successfully for " + username, "", ""));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok(new Message("", "Error occured while saving the data : " + ex.getMessage(), ""));
		}
	}

	private boolean validateGlimpseFields(ProfileGlimpseFields profileGlimpseFields) {
		if (!isEmpty(profileGlimpseFields.getField1()) || !isEmpty(profileGlimpseFields.getField2())
				|| !isEmpty(profileGlimpseFields.getField3()) || !isEmpty(profileGlimpseFields.getField4())
				|| !isEmpty(profileGlimpseFields.getField5()) || !isEmpty(profileGlimpseFields.getField6())
				|| !isEmpty(profileGlimpseFields.getField7()) || !isEmpty(profileGlimpseFields.getField8())) {
			return true;
		}
		return false;
	}

	private List<String> populateNonEmptyFields(ProfileGlimpseFields glipmseFields, List<String> fieldLists) {
		if (!isEmpty(glipmseFields.getField1())) {
			fieldLists.add(glipmseFields.getField1());
		}
		if (!isEmpty(glipmseFields.getField2())) {
			fieldLists.add(glipmseFields.getField2());
		}
		if (!isEmpty(glipmseFields.getField3())) {
			fieldLists.add(glipmseFields.getField3());
		}
		if (!isEmpty(glipmseFields.getField4())) {
			fieldLists.add(glipmseFields.getField4());
		}
		if (!isEmpty(glipmseFields.getField5())) {
			fieldLists.add(glipmseFields.getField5());
		}
		if (!isEmpty(glipmseFields.getField6())) {
			fieldLists.add(glipmseFields.getField6());
		}
		if (!isEmpty(glipmseFields.getField7())) {
			fieldLists.add(glipmseFields.getField7());
		}
		if (!isEmpty(glipmseFields.getField8())) {
			fieldLists.add(glipmseFields.getField8());
		}
		return fieldLists;
	}

	private void populateEmptyGlimpseRecordMap(ProfileGlimpseFields profileGlimpseFields,
			Map<String, Object> glimpseRecordMap) {
		if (!isEmpty(profileGlimpseFields.getField1())) {
			glimpseRecordMap.put(profileGlimpseFields.getField1(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField2())) {
			glimpseRecordMap.put(profileGlimpseFields.getField2(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField3())) {
			glimpseRecordMap.put(profileGlimpseFields.getField3(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField4())) {
			glimpseRecordMap.put(profileGlimpseFields.getField4(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField5())) {
			glimpseRecordMap.put(profileGlimpseFields.getField5(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField6())) {
			glimpseRecordMap.put(profileGlimpseFields.getField6(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField7())) {
			glimpseRecordMap.put(profileGlimpseFields.getField7(), "");
		}
		if (!isEmpty(profileGlimpseFields.getField8())) {
			glimpseRecordMap.put(profileGlimpseFields.getField8(), "");
		}
	}

	private boolean isEmpty(String value) {
		return StringUtils.isEmpty(value);
	}

	@PostMapping(path = "/json-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		return ResponseEntity.ok("");
	}

}
