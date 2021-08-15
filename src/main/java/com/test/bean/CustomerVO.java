package com.test.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerVO {
	private int seq;
	private String userName;
	private String sex;
	private int age;
	private String idNum;
	private String phoneNum;
	private String email;
	private Date modifyTime;
	private String state;
	private Date createTime;
    private BigDecimal salary;
	
	public String toString() {
		return "seq=" + seq + ", " + 
				"userName=" + userName + ", " + 
				"sex=" + sex + ", " + 
				"idNum=" + idNum + ", " + 
				"phoneNum=" + phoneNum + ", " + 
				"email=" + email + ", " + 
				"modifyTime=" + modifyTime + ", " + 
				"state=" + state + ", " + 
				"createTime=" + this.getCreateTime() + ", " + 
				"salary=" + this.getSalary() + ".";
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	
}
