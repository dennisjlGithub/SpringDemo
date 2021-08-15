package com.test.bean;

import java.util.List;

public class Customer {

	private String id;
	private String name;
	private int age;
	private List<String> favouriteBooks;
	private String[] booksArray;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<String> getFavouriteBooks() {
		return favouriteBooks;
	}
	public void setFavouriteBooks(List<String> favouriteBooks) {
		this.favouriteBooks = favouriteBooks;
	}
	public String[] getBooksArray() {
		return booksArray;
	}
	public void setBooksArray(String[] booksArray) {
		this.booksArray = booksArray;
	}
	
}
