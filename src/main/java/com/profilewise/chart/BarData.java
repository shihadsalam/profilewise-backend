package com.profilewise.chart;

import java.util.List;

public class BarData {
	
	private List<String> labels;
	
	private List<BarDataset> dataSets;

	public BarData() {
		super();
	}

	public BarData(List<String> labels) {
		this.labels = labels;
	}

	public BarData(List<String> labels, List<BarDataset> dataSets) {
		this.labels = labels;
		this.dataSets = dataSets;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<BarDataset> getDataSets() {
		return dataSets;
	}

	public void setDataSets(List<BarDataset> dataSets) {
		this.dataSets = dataSets;
	}
	
}
