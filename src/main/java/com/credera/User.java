package com.credera;

public class User {

	private String email;
	private String name;
	private String password;
	private String id;

	public User() {
		email = "";
		name = "";
		password = "";
		id = "";
	}

	public User(String email, String name){
		this.name = name;
		this.email = email;
		this.password = "";
		id = "";
	}

	public User(String email, String name, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		id = "";
	}

  public User(String email, String name, String password, String server) {
    this.name = name;
    this.email = email;
    this.password = password;
		id = "";
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

	public String getId() { return id; }

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) { this.password = password; }

	public void setId(String id){ this.id = id; }

}
