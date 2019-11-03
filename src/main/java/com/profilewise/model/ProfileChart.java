package com.profilewise.model;

import java.util.LinkedHashMap;
import java.util.List;

public class ProfileChart {

	private List<String> chartTitles;
	private LinkedHashMap<String, List<Object>> chartData;

	public ProfileChart() {

	}

	public ProfileChart(List<String> chartTitles) {
		this.chartTitles = chartTitles;
	}

	public List<String> getChartTitles() {
		return chartTitles;
	}

	public void setChartTitles(List<String> chartTitles) {
		this.chartTitles = chartTitles;
	}

	public LinkedHashMap<String, List<Object>> getChartData() {
		return chartData;
	}

	public void setChartData(LinkedHashMap<String, List<Object>> chartData) {
		this.chartData = chartData;
	}

}
