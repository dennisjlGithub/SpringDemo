package com.test.util.xmlbind;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {
        "city",
        "province",
})
public class Address implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private String province;
 
    private String city;
 
    public String getProvince() {
        return province;
    }
 
    public void setProvince(String province) {
        this.province = province;
    }
 
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
 
    public Address(String province, String city) {
        this.province = province;
        this.city = city;
    }
 
    public Address() {
    }
}