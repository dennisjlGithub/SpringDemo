package com.test.util.xmlbind;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
 
    @XmlAttribute(name = "xmnl")
    private final String xmnl="www.baidu.com";
 
    // 用户Id
    @XmlElement(name = "UserId")
    private int userId;
    // 用户名
    @XmlElement(name = "UserName")
    private String userName;
    // 用户密码
    @XmlElement(name = "Password")
    private String password;
    // 用户生日
    @XmlElement(name = "Birthday")
    private Date birthday;
    // 用户钱包
    @XmlElement(name = "Money")
    private double money;
    //用户地址
    @XmlElementWrapper(name = "AddressList")
    @XmlElement(name = "Address")
    private List<Address> addressList;
    //用户电脑
    @XmlElement(name = "Computers")
    private List<Computer> computers;
 
    public List<Computer> getComputers() {
        return computers;
    }
 
    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }
 
    public List<Address> getAddressList() {
        return addressList;
    }
 
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
 
    public User() {
        super();
    }
 
    public User(int userId, String userName, String password, Date birthday,
                double money) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.money = money;
    }
 
    public int getUserId() {
        return userId;
    }
 
    public void setUserId(int userId) {
        this.userId = userId;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Date getBirthday() {
        return birthday;
    }
 
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
 
    public double getMoney() {
        return money;
    }
 
    public void setMoney(double money) {
        this.money = money;
    }
 
    @Override
    public String toString() {
        return "User{" +
                "xmnl='" + xmnl + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", money=" + money +
                ", addressList=" + addressList +
                ", computers=" + computers +
                '}';
    }
}