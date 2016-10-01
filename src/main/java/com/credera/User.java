package com.credera;

public class User {

	private String email;
	private String name;
	private String password;
	
	public User() {
		email = "";
		name = "";
		password = "";
	}
	
	public User(String email, String name, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
