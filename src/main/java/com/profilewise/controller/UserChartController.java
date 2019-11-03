package com.profilewise.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profilewise.chart.BarData;
import com.profilewise.chart.BarDataset;
import com.profilewise.chart.DoughnutData;
import com.profilewise.chart.PieData;
import com.profilewise.chart.RadarData;
import com.profilewise.chart.RadarDataset;
import com.profilewise.model.ProfileFieldId;
import com.profilewise.model.ProfileFields;
import com.profilewise.model.ProfileRecordId;
import com.profilewise.model.ProfileRecords;
import com.profilewise.service.UserProfileService;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/user-chart" })
public class UserChartController {

	@Autowired
	private UserProfileService userProfileService;

	@GetMapping(path = { "/get-bar-chart/{reportee}/{type}" })
	public ResponseEntity<BarData> getBarChartData(@PathVariable("reportee") String reportee,
			@PathVariable("type") String type) {
		BarData barData = null;
		List<ProfileRecords> records = findProfileRecordsForUser(reportee);
		if (null != records) {
			List<String> titles = new ArrayList<String>();
			List<String> fields = new ArrayList<String>();
			LinkedHashMap<String, List<BigDecimal>> dataSetMap = new LinkedHashMap<String, List<BigDecimal>>();
			Optional<ProfileFields> profField = getOptionalProfileField(reportee, type);
			if (profField.isPresent()) {
				fields = profField.get().getNonEmptyFieldsList();
			}
			fields.forEach(field -> dataSetMap.put(field, new ArrayList<BigDecimal>()));
			records.forEach(record -> titles.add(record.getId().getTitle()));
			
			barData = new BarData(titles);
			for (ProfileRecords profileRecord : records) {
				for(String field : fields) {
					try {
						String value = (String) profileRecord.getData().getMap().get(field);
						BigDecimal dataVal = new BigDecimal(value);
						dataSetMap.get(field).add(dataVal);
					}
					catch(Exception ex) {
						// Skip the value and iterate next
					}
				}
			}
			if (!dataSetMap.isEmpty()) {
				List<BarDataset> barDataSets = new ArrayList<BarDataset>();
				for (Map.Entry<String, List<BigDecimal>> entry : dataSetMap.entrySet()) {
					BarDataset barDataSet = new BarDataset(entry.getKey(), entry.getValue());
					barDataSets.add(barDataSet);
				}
				barData.setDataSets(barDataSets);
			}
		}
		return ResponseEntity.ok(barData);
	}
	
	@GetMapping(path = { "/get-doughnut-chart/{reportee}/{recordSet}" })
	public ResponseEntity<DoughnutData> getDoughnutChartData(@PathVariable("reportee") String reportee,
			@PathVariable("recordSet") String recordSet) {
		DoughnutData doughnutData = null;
		List<String> labels = new ArrayList<String>();
		List<BigDecimal> values = new ArrayList<BigDecimal>();
		ProfileRecords record = findProfileRecords(reportee, recordSet);
		if(null != record) {
			for(Map.Entry<String, Object> map: record.getData().getMap().entrySet()) {
				labels.add(map.getKey());
				try {
					String value = (String) map.getValue();
					BigDecimal dataVal = new BigDecimal(value);
					values.add(dataVal);
				}
				catch(Exception ex) {
					// Skip the value and iterate next
				}
			}
			doughnutData = new DoughnutData(labels, values);
		}
		return ResponseEntity.ok(doughnutData);
	}
	
	@GetMapping(path = { "/get-radar-chart/{reportee}/{type}" })
	public ResponseEntity<RadarData> getRadarChartData(@PathVariable("reportee") String reportee,
			@PathVariable("type") String type) {
		RadarData radarData = null;
		List<ProfileRecords> records = findProfileRecordsForUser(reportee);
		if (null != records) {
			List<String> titles = new ArrayList<String>();
			List<String> fields = new ArrayList<String>();
			LinkedHashMap<String, List<BigDecimal>> dataSetMap = new LinkedHashMap<String, List<BigDecimal>>();
			Optional<ProfileFields> profField = getOptionalProfileField(reportee, type);
			if (profField.isPresent()) {
				fields = profField.get().getNonEmptyFieldsList();
			}
			fields.forEach(field -> dataSetMap.put(field, new ArrayList<BigDecimal>()));
			records.forEach(record -> titles.add(record.getId().getTitle()));
			
			radarData = new RadarData(titles);
			for (ProfileRecords profileRecord : records) {
				for(String field : fields) {
					try {
						String value = (String) profileRecord.getData().getMap().get(field);
						BigDecimal dataVal = new BigDecimal(value);
						dataSetMap.get(field).add(dataVal);
					}
					catch(Exception ex) {
						// Skip the value and iterate next
					}
				}
			}
			if (!dataSetMap.isEmpty()) {
				List<RadarDataset> radarDataSets = new ArrayList<RadarDataset>();
				for (Map.Entry<String, List<BigDecimal>> entry : dataSetMap.entrySet()) {
					RadarDataset radarDataSet = new RadarDataset(entry.getKey(), entry.getValue());
					radarDataSets.add(radarDataSet);
				}
				radarData.setDataSets(radarDataSets);
			}
		}
		return ResponseEntity.ok(radarData);
	}
	
	@GetMapping(path = { "/get-pie-chart/{reportee}/{recordSet}" })
	public ResponseEntity<PieData> getPieChartData(@PathVariable("reportee") String reportee,
			@PathVariable("recordSet") String recordSet) {
		PieData pieData = null;
		List<String> labels = new ArrayList<String>();
		List<BigDecimal> values = new ArrayList<BigDecimal>();
		ProfileRecords record = findProfileRecords(reportee, recordSet);
		if(null != record) {
			for(Map.Entry<String, Object> map: record.getData().getMap().entrySet()) {
				labels.add(map.getKey());
				try {
					String value = (String) map.getValue();
					BigDecimal dataVal = new BigDecimal(value);
					values.add(dataVal);
				}
				catch(Exception ex) {
					// Skip the value and iterate next
				}
			}
			pieData = new PieData(labels, values);
		}
		return ResponseEntity.ok(pieData);
	}

	private ProfileRecords findProfileRecords(String username, String title) {
		Optional<ProfileRecords> records = userProfileService.findProfileRecordsById
				(new ProfileRecordId(username, title));
		if (records.isPresent()) {
			return records.get();
		}
		return null;
	}
	
	private List<ProfileRecords> findProfileRecordsForUser(String username) {
		Optional<List<ProfileRecords>> records = userProfileService.findProfileRecordsByUsername(username);
		if (records.isPresent() && !records.get().isEmpty()) {
			return records.get();
		}
		return null;
	}
	
	private Optional<ProfileFields> getOptionalProfileField(String username, String fieldType) {
		return userProfileService.findProfileFieldsById(new ProfileFieldId(username, fieldType));
	}
}
