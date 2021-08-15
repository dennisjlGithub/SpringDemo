package com.test.bean;

import java.util.HashMap;
import java.util.List;

public class ReportVO {

	private String reportID;

	private String reportFormat;

	private String datasource;

	private String splitFile;

	private String token;

	private String buzDate;

	private List<HashMap<String, String>> parameters;

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getSplitFile() {
		return splitFile;
	}

	public void setSplitFile(String splitFile) {
		this.splitFile = splitFile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBuzDate() {
		return buzDate;
	}

	public void setBuzDate(String buzDate) {
		this.buzDate = buzDate;
	}

	public List<HashMap<String, String>> getParameters() {
		return parameters;
	}

	public void setParameters(List<HashMap<String, String>> parameters) {
		this.parameters = parameters;
	}
	
	

}
