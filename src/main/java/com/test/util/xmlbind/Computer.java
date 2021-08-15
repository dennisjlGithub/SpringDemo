package com.test.util.xmlbind;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Computer")
@XmlType(propOrder = {  "brandName", "price" })
public class Computer implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // 品牌名
    private String brandName;
    // 价格
    private double price;
 
    public Computer() {
        super();
    }
 
    public Computer( String brandName,double price) {
        super();
        this.brandName = brandName;
        this.price = price;
    }
 
    public String getBrandName() {
        return brandName;
    }
 
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    @Override
    public String toString() {
        return "Computer{" +
                "brandName='" + brandName + '\'' +
                ", price=" + price +
                '}';
    }
}