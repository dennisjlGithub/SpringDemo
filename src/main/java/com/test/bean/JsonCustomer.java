package com.test.bean;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class JsonCustomer {
	private String engName;

	@JsonFormat(pattern = "yyyy-MMM-dd")
	private LocalDate birthday;

	private int age;

	//@JsonIgnore
	private String cnName;
	
	private String sex;
	
	private Map<String, String> data = new HashMap<>();

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
		data.put("engName", engName);
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
		data.put("cnName", cnName);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
