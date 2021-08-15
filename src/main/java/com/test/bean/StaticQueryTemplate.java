package com.test.bean;

import java.util.List;

public class StaticQueryTemplate {

	private String reportID;
	private String description;
	private List<StaticQueryParameter> staticQueryParameter;

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StaticQueryParameter> getStaticQueryParameter() {
		return staticQueryParameter;
	}

	public void setStaticQueryParameter(List<StaticQueryParameter> staticQueryParameter) {
		this.staticQueryParameter = staticQueryParameter;
	}

}
