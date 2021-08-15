package com.test.web.form;

import java.util.List;
import java.util.Map;

public class OnlineForm {

	private String userName;
	
	private String jsonStr;
	
	private String city;
	
	private String inputText;
	
	//Single checkbox
	private boolean checkbox1 = true;
	private boolean checkbox2 = true;
	
	//Multiple checkboxes
	private String[] checkboxes;
	
	//Multiple checkboxes
	private String[] favoriteBooks;
	
	private Map<String, String> cityList;
	
	private byte[] inputFile;
	private String inputFileName;
	private String inputFileType;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	public Map<String, String> getCityList() {
		return cityList;
	}
	public void setCityList(Map<String, String> cityList) {
		this.cityList = cityList;
	}
	public byte[] getInputFile() {
		return inputFile;
	}
	public void setInputFile(byte[] inputFile) {
		this.inputFile = inputFile;
	}
	public String getInputFileName() {
		return inputFileName;
	}
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	public String getInputFileType() {
		return inputFileType;
	}
	public void setInputFileType(String inputFileType) {
		this.inputFileType = inputFileType;
	}
	public String[] getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(String[] checkboxes) {
		this.checkboxes = checkboxes;
	}
	public boolean isCheckbox1() {
		return checkbox1;
	}
	public void setCheckbox1(boolean checkbox1) {
		this.checkbox1 = checkbox1;
	}
	public boolean isCheckbox2() {
		return checkbox2;
	}
	public void setCheckbox2(boolean checkbox2) {
		this.checkbox2 = checkbox2;
	}
	public String[] getFavoriteBooks() {
		return favoriteBooks;
	}
	public void setFavoriteBooks(String[] favoriteBooks) {
		this.favoriteBooks = favoriteBooks;
	}
	
}
