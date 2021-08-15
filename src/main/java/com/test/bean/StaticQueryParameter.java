package com.test.bean;

import java.util.List;

public class StaticQueryParameter {

	private String type;
	private List<String> name;
	private List<String> label;
	private String isCompulsory;
	private List<String> data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public String getIsCompulsory() {
		return isCompulsory;
	}

	public void setIsCompulsory(String isCompulsory) {
		this.isCompulsory = isCompulsory;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
