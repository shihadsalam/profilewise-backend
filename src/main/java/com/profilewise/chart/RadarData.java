package com.profilewise.chart;

import java.util.List;

public class RadarData {
	
	private List<String> labels;
	
	private List<RadarDataset> dataSets;

	public RadarData() {
		super();
	}

	public RadarData(List<String> labels) {
		this.labels = labels;
	}

	public RadarData(List<String> labels, List<RadarDataset> dataSets) {
		this.labels = labels;
		this.dataSets = dataSets;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<RadarDataset> getDataSets() {
		return dataSets;
	}

	public void setDataSets(List<RadarDataset> dataSets) {
		this.dataSets = dataSets;
	}
	
}
