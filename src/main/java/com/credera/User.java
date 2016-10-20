package com.credera;

public class User {

	private String email;
	private String name;
	private String password;
	private String token;
  private boolean auth;
	
	public User() {
		email = "";
		name = "";
		password = "";
		token = "";
		auth = false;
	}
	
	public User(String email, String name, String password, String token) {
		this.name = name;
		this.email = email;
		this.password = password;
    this.token = token;
    this.auth = false;
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

	public String getToken() { return token; }

  public boolean isAuthorized() { return auth; }
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(String token) { this.token = token; }

  public void authorize() { this.auth = true; }

  public void deauthorize() { this.auth = false; }

}
