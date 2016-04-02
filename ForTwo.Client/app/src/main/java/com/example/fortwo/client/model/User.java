package com.example.fortwo.client.model;

public class User {
	private String name;
	private String email;
	private int userId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User(String name, String email, int userId) {
		super();
		this.name = name;
		this.email = email;
		this.userId = userId;
	}
	public User()
	{
	
	}
	
}
