package com.profilewise.chart;

import java.math.BigDecimal;
import java.util.List;

public class RadarDataset {

	private String label;
	
	private List<BigDecimal> data;

	public RadarDataset() {
		super();
	}

	public RadarDataset(String label, List<BigDecimal> data) {
		super();
		this.label = label;
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<BigDecimal> getData() {
		return data;
	}

	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
	
}
