package com.boot.angular.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.boot.angular.model.ProfileFields;
import com.boot.angular.model.ProfileRecords;
import com.boot.angular.service.UserProfileService;
import com.google.gson.Gson;

import io.vertx.core.json.JsonObject;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/user-profile" })
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;
	
	private static final String TITLE = "Title";

	
	@GetMapping(path = { "/get-profile-fields/{username}/{type}" })
	public ResponseEntity<List<String>> getProfileFields(@PathVariable("username") String username, 
			@PathVariable("type") String type) {
		Optional<ProfileFields> fields = userProfileService.findProfileFieldsByIdAndType(username, type);
		if (fields.isPresent()) {
			ProfileFields profileFields = fields.get();
			return ResponseEntity.ok(profileFields.getNonEmptyFieldsList());
		}
		return null;
	}
	
	@PostMapping(path = { "/add-profile-fields" })
	public ResponseEntity<Message> addProfileFields(@RequestBody ProfileFields profileFields) {
		if (profileFields.getFieldsList().stream().anyMatch(field -> !isEmpty(field))) {
			if (!isProfileFieldsExists(profileFields)) {
				userProfileService.addProfileFields(profileFields);
				return ResponseEntity.ok(new Message("Profile fields created successfully for " 
						+ profileFields.getId(), "", ""));

			}
			else {
				return ResponseEntity.ok(new Message("", "Profile fields with type "+profileFields.getType()+""
						+ " already exists", ""));
			}
		}
		else {
			return ResponseEntity.ok(new Message("", "Atleast one field should be non-empty", ""));
		}
	}
	
	@GetMapping(path = { "/get-profile-records/{username}" })
	public Map<String, Map<String, Object>> getProfileRecords(@PathVariable("username") String username) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String,Object>>();
		Map<String, Object> innerMap = new HashMap<String, Object>();
		Optional<List<ProfileRecords>> records = userProfileService.findProfileRecordsById(username);
		if (records.isPresent()) {
			for (ProfileRecords profileRecord : records.get()) {
				innerMap = profileRecord.getData().getMap();
				String title = (String) innerMap.get(TITLE);
				innerMap.remove(TITLE);
				resultMap.put(title, innerMap);
			}
			return resultMap;
		}
		
		return null;
	}

	@PostMapping(path = { "/add-profile-records" })
	public ResponseEntity<Message> addProfileRecords(@RequestBody String jsonData) {
		JsonObject jsonObj = null;
		ProfileRecords profileRecord = null;
		try {
			jsonObj = new JsonObject(jsonData);
			String username = jsonObj.getString("id", "");
			String title = jsonObj.getString(TITLE, "");
			String fieldType = jsonObj.getString("fieldType", "");
			if (isEmpty(title)) {
				return ResponseEntity.ok(new Message("", "'Title' field missing in the profile record data", ""));
			}
			else if (isEmpty(fieldType)) {
				return ResponseEntity.ok(new Message("", "Field type missing in the profile record data", ""));
			}
			else {
				jsonObj.remove("id");
				jsonObj.remove(TITLE);
				jsonObj.remove("fieldType");
				profileRecord = new ProfileRecords(username, title, fieldType, jsonObj);
				if (validateWithProfileFields(jsonObj, username, fieldType)) {
					userProfileService.addProfileRecord(profileRecord);
					return ResponseEntity.ok(new Message("Profile records added successfully for " + username, "", ""));
				}
				else {
					return ResponseEntity.ok(new Message("", "Mismatched record fields with the defined field set: " 
							+fieldType, ""));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok(new Message("", "Error occured while saving the data : " + ex.getMessage(), ""));
		}
	}
	
	@GetMapping("/download-fields-json/{username}/{type}")
	public ResponseEntity<byte[]> downloadFieldDataAsJson(@PathVariable("username") String username, 
			@PathVariable("type") String fieldType) throws Exception {
		Optional<ProfileFields> optional = userProfileService.findProfileFieldsByIdAndType(username, fieldType);
		if (optional.isPresent()) {
			ProfileFields profileFields = optional.get();
			String jsonStr = new Gson().toJson(profileFields.getNonEmptyFieldsList());
			byte[] byteArr = jsonStr.getBytes();
			String fileName = fieldType+"_"+username+".json";
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentLength(byteArr.length);
			respHeaders.setContentType(MediaType.APPLICATION_JSON);
			respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			return new ResponseEntity<byte[]>(byteArr, respHeaders, HttpStatus.OK);
		}
		return null;
	}

	private boolean validateWithProfileFields(JsonObject jsonObj, String username, String fieldType) {
		Optional<ProfileFields> optional = userProfileService.findProfileFieldsByIdAndType(username, fieldType);
		if (optional.isPresent()) {
			ProfileFields profileFields = optional.get();
			Set<String> profileFieldSet = profileFields.getNonEmptyFieldsList().stream().collect(Collectors.toSet());
			if (jsonObj.fieldNames().equals(profileFieldSet)) {
				return true;
			}
			
		}
		return false;
		
	}

	private boolean isProfileFieldsExists(ProfileFields profileFields) {
		Optional<List<ProfileFields>> optional = userProfileService.findProfileFieldsById(profileFields.getId());
		if (optional.isPresent()) {
			List<ProfileFields> fieldsList = optional.get();
			if(fieldsList.stream().anyMatch(field -> field.getType().equals(profileFields.getType()))) {
				return true;
			}
		}
		return false;
	}

	private boolean isEmpty(String value) {
		return StringUtils.isEmpty(value);
	}

	@PostMapping(path = "/json-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		return ResponseEntity.ok("");
	}
	
	
	// ---- Old Code ----
	
	/*	
	@PostMapping(path = { "/import" })
	public ResponseEntity<Message> importUserProfile(@RequestBody ProfileDTO userCareerDTO) {
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
	@GetMapping(path = { "/get-imported-profile/{username}" })
	public ResponseEntity<List<Map<String, Object>>> getUserProfile(@PathVariable("username") String username) {
		Optional<UserProfile> optUserProfile = userProfileService.findUserProfileById(username);
		UserProfile userProfile = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (optUserProfile.isPresent()) {
			userProfile = optUserProfile.get();
			Object obj = userProfile.getData();
			if (obj instanceof Map<?, ?>) {
				mapList.add((Map<String, Object>) obj);
			} else if (obj instanceof List<?>) {
				mapList = (List<Map<String, Object>>) obj;
			}
			return ResponseEntity.ok(mapList);
		}
		
		return null;
	}
	
	private void populateFieldRecordsMap(Map<String, Object> existingProfileRecordMap, 
			ProfileFields profileFields, Map<String, Object> profileRecordMap) {
		for (String field : profileFields.getFieldsList()) {
			if (!StringUtils.isEmpty(field)) {
				if (existingProfileRecordMap.containsKey(field)) {
					profileRecordMap.put(field, existingProfileRecordMap.get(field));
				}
				else {
					profileRecordMap.put(field, "");
				}
			}
		}
	}
	
	private void populateEmptyProfileRecordMap(ProfileFields profileFields,
			Map<String, Object> profileRecordMap) {
		if (!isEmpty(profileFields.getField1())) {
			profileRecordMap.put(profileFields.getField1(), "");
		}
		if (!isEmpty(profileFields.getField2())) {
			profileRecordMap.put(profileFields.getField2(), "");
		}
		if (!isEmpty(profileFields.getField3())) {
			profileRecordMap.put(profileFields.getField3(), "");
		}
		if (!isEmpty(profileFields.getField4())) {
			profileRecordMap.put(profileFields.getField4(), "");
		}
		if (!isEmpty(profileFields.getField5())) {
			profileRecordMap.put(profileFields.getField5(), "");
		}
		if (!isEmpty(profileFields.getField6())) {
			profileRecordMap.put(profileFields.getField6(), "");
		}
		if (!isEmpty(profileFields.getField7())) {
			profileRecordMap.put(profileFields.getField7(), "");
		}
		if (!isEmpty(profileFields.getField8())) {
			profileRecordMap.put(profileFields.getField8(), "");
		}
		if (!isEmpty(profileFields.getField9())) {
			profileRecordMap.put(profileFields.getField9(), "");
		}
		if (!isEmpty(profileFields.getField10())) {
			profileRecordMap.put(profileFields.getField10(), "");
		}
	}

*/

}
