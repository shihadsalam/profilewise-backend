package com.profilewise.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

import com.profilewise.model.Message;
import com.profilewise.model.ProfileFieldId;
import com.profilewise.model.ProfileFields;
import com.profilewise.model.ProfileFieldsDTO;
import com.profilewise.model.ProfileRecordDTO;
import com.profilewise.model.ProfileRecordId;
import com.profilewise.model.ProfileRecords;
import com.profilewise.service.UserProfileService;

import io.vertx.core.json.JsonArray;
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
		Optional<ProfileFields> fields = getOptionalProfileField(username, type);
		if (fields.isPresent()) {
			ProfileFields profileFields = fields.get();
			return ResponseEntity.ok(profileFields.getNonEmptyFieldsList());
		}
		return null;
	}
	
	@GetMapping(path = { "/get-profile-fields-map/{username}" })
	public ResponseEntity<Map<String, List<String>>> getProfileFieldsAsMap(@PathVariable("username") String username) {
		Map<String, List<String>> mapData = new HashMap<String, List<String>>();
		List<ProfileFields> profileFieldList = new ArrayList<ProfileFields>();
		if (username.equals("ALL")) {
			profileFieldList = userProfileService.findAllProfileFields();
		}
		else {
			Optional<List<ProfileFields>> optfieldList = userProfileService.findProfileFieldsByUsername(username);
			if (optfieldList.isPresent()) {
				profileFieldList = optfieldList.get();
			}
		}
		if (null != profileFieldList && !profileFieldList.isEmpty()) {
			for (ProfileFields profileFields : profileFieldList) {
				mapData.put(profileFields.getId().getType(), profileFields.getNonEmptyFieldsList());
			}
			return ResponseEntity.ok(mapData);
		}

		return null;
	}
	
	@PostMapping(path = { "/add-profile-fields" })
	public ResponseEntity<Message> addProfileFields(@RequestBody ProfileFieldsDTO profileFieldsDTO) {
		ProfileFields profileFields = createProfileFields(profileFieldsDTO);
		if (profileFields.getFieldsList().stream().anyMatch(field -> !isEmpty(field))) {
			if (!isProfileFieldsExists(profileFields)) {
				userProfileService.addProfileFields(profileFields);
				return ResponseEntity.ok(new Message("Profile fields created successfully for " 
						+ profileFields.getId().getUsername(), ""));

			}
			else {
				return ResponseEntity.ok(new Message("", "Profile fields with type "+profileFields.getId().getType()+""
						+ " already exists"));
			}
		}
		else {
			return ResponseEntity.ok(new Message("", "Atleast one field should be non-empty"));
		}
	}
	
	@GetMapping(path = { "/get-profile-records/{username}" })
	public LinkedHashMap<String, Map<String, Object>> getProfileRecords(@PathVariable("username") String username) {
		LinkedHashMap<String, Object> innerMap = new LinkedHashMap<String, Object>();
		List<ProfileRecords> records = findProfileRecordsForUser(username);
		if (null != records) {
			LinkedHashMap<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
			for (ProfileRecords profileRecord : records) {
				innerMap = (LinkedHashMap<String, Object>) profileRecord.getData().getMap();
				String title = profileRecord.getId().getTitle();
				resultMap.put(title, innerMap);
			}
			return resultMap;
		}
		return null;
	}
	
	@GetMapping(path = { "/get-profile-records-titles/{username}" })
	public List<String> getProfileRecordsTitles(@PathVariable("username") String username) {
		List<String> list = new ArrayList<String>();
		List<ProfileRecords> records = findProfileRecordsForUser(username);
		if (null != records) {
			records.forEach(record -> list.add(record.getId().getTitle()));
			return list;
		}
		return null;
	}

	@PostMapping(path = { "/add-profile-records" })
	public ResponseEntity<Message> addProfileRecords(@RequestBody ProfileRecordDTO profileRecordDTO) {
		JsonObject jsonObj = null;
		JsonArray jsonArray = null;
		String jsonData = profileRecordDTO.getJson();
		try {
			jsonArray = new JsonArray(jsonData);
			return saveJsonArray(jsonArray, profileRecordDTO);
		} catch (Exception e) {
			try {
				jsonObj = new JsonObject(jsonData);
				return saveJsonObject(jsonObj, profileRecordDTO);
			} 
			catch (Exception ex) {
				return ResponseEntity.ok(new Message("", "Invalid JSON Format"));
			}
		}
	}
	
	@GetMapping("/get-fields-json/{username}/{type}")
	public String getFieldDataAsJson(@PathVariable("username") String username, 
			@PathVariable("type") String fieldType) throws Exception {
		Optional<ProfileFields> optional = getOptionalProfileField(username, fieldType);
		if (optional.isPresent()) {
			ProfileFields profileFields = optional.get();
			return getJsonForProfileFields(profileFields);
		}
		return null;
	}
	
	@GetMapping("/download-fields-json/{username}/{type}")
	public ResponseEntity<Resource> downloadFieldDataAsJson(@PathVariable("username") String username, 
			@PathVariable("type") String fieldType) throws Exception {
		Optional<ProfileFields> optional = getOptionalProfileField(username, fieldType);
		if (optional.isPresent()) {
			ProfileFields profileFields = optional.get();
			String jsonStr = getJsonForProfileFields(profileFields);
			byte[] byteArr = jsonStr.getBytes();
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentLength(byteArr.length);
			respHeaders.setContentType(MediaType.APPLICATION_JSON);
			respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment");
			ByteArrayResource resource = new ByteArrayResource(byteArr);
			return ResponseEntity.ok().headers(respHeaders).body(resource);
		}
		return null;
	}
	
	private ResponseEntity<Message> saveJsonObject(JsonObject jsonObject, ProfileRecordDTO profileRecordDTO) {
		ProfileRecords profileRecord = null;
		String username = profileRecordDTO.getUsername();
		String fieldType = profileRecordDTO.getFieldType();
		String title = jsonObject.getString(TITLE, "");
		if (isEmpty(title)) {
			return ResponseEntity.ok(new Message("", "Title value missing in the profile record data"));
		}
		else {
			jsonObject.remove(TITLE);
			profileRecord = new ProfileRecords(new ProfileRecordId(username, title), fieldType, jsonObject);
			if (validateWithProfileFields(jsonObject, username, fieldType)) {
				userProfileService.addProfileRecord(profileRecord);
				return ResponseEntity.ok(new Message("Profile records added successfully for " + username, ""));
			}
			else {
				return ResponseEntity.ok(new Message("", "JSON fields not matching with the selected field set: " 
						+ fieldType));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<Message> saveJsonArray(JsonArray jsonArr, ProfileRecordDTO profileRecordDTO) {
		List<ProfileRecords> profileRecords = new ArrayList<ProfileRecords>();
		List<Map<String, Object>> jsonObjList = jsonArr.getList();
		String username = profileRecordDTO.getUsername();
		String fieldType = profileRecordDTO.getFieldType();
		ProfileRecords profileRecord = null;
		if (null != jsonObjList && !jsonObjList.isEmpty()) {
			for(Map<String, Object> map : jsonObjList) {
				JsonObject jsonObject = new JsonObject(map);
				String title = jsonObject.getString(TITLE, "");
				if (isEmpty(title)) {
					return ResponseEntity.ok(new Message("", "Title value missing in the profile record data"));
				}
				else {
					jsonObject.remove(TITLE);
					profileRecord = new ProfileRecords(new ProfileRecordId(username, title), fieldType, jsonObject);
					if (validateWithProfileFields(jsonObject, username, fieldType)) {
						profileRecords.add(profileRecord);
					}
					else {
						return ResponseEntity.ok(new Message("", "JSON fields not matching with the selected field set: " 
								+ fieldType));
					}
				}
			}
			if (!profileRecords.isEmpty()) {
				userProfileService.addAllProfileRecord(profileRecords);
			}
			return ResponseEntity.ok(new Message("Profile records added successfully for " + username, ""));
		}
		else {
			return ResponseEntity.ok(new Message("", "Invalid JSON Format"));
		}
	}
	
	private List<ProfileRecords> findProfileRecordsForUser(String username) {
		Optional<List<ProfileRecords>> records = userProfileService.findProfileRecordsByUsername(username);
		if (records.isPresent() && !records.get().isEmpty()) {
			return records.get();
		}
		return null;
	}
	
	private ProfileFields createProfileFields(ProfileFieldsDTO profileFieldsDTO) {
		ProfileFieldId id = new ProfileFieldId(profileFieldsDTO.getUsername(), profileFieldsDTO.getType());
		ProfileFields profileFields = new ProfileFields(id, profileFieldsDTO.getField1(), profileFieldsDTO.getField2(),
				profileFieldsDTO.getField3(), profileFieldsDTO.getField4(), profileFieldsDTO.getField5(), 
				profileFieldsDTO.getField6(), profileFieldsDTO.getField7(), profileFieldsDTO.getField8(),
				profileFieldsDTO.getField9(), profileFieldsDTO.getField10());
		return profileFields;
	}
	
	private String getJsonForProfileFields(ProfileFields profileFields) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.put(TITLE, "");
		for (String field : profileFields.getNonEmptyFieldsList()) {
			jsonObject.put(field, "");
		}
		return jsonObject.encodePrettily();
	}

	private boolean validateWithProfileFields(JsonObject jsonObj, String username, String fieldType) {
		Optional<ProfileFields> optional = getOptionalProfileField(username, fieldType);
		if (optional.isPresent()) {
			ProfileFields profileFields = optional.get();
			Set<String> profileFieldSet = profileFields.getNonEmptyFieldsList().stream().collect(Collectors.toSet());
			if (jsonObj.fieldNames().equals(profileFieldSet)) {
				return true;
			}
			
		}
		return false;
	}
	
	private Optional<ProfileFields> getOptionalProfileField(String username, String fieldType) {
		return userProfileService.findProfileFieldsById(new ProfileFieldId(username, fieldType));
	}

	private boolean isProfileFieldsExists(ProfileFields profileFields) {
		Optional<List<ProfileFields>> optional = userProfileService.findProfileFieldsByUsername(
				profileFields.getId().getUsername());
		if (optional.isPresent()) {
			List<ProfileFields> fieldsList = optional.get();
			if(fieldsList.stream().anyMatch(field -> field.getId().getType().equals(profileFields.getId().getType()))) {
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
	
}