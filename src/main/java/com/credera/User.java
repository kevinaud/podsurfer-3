package com.credera;

public class User {

	private String email;
	private String name;
	private String password;
	private String authServ;

	public User() {
		email = "";
		name = "";
		password = "";
        authServ = "podsurfer";
	}
	
	public User(String email, String name, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.authServ = "podsurfer";
	}
  public User(String email, String name, String password, String server) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.authServ = server;
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

	public String getAuthServ() { return authServ; }

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) { this.password = password; }

	public void setAuthServ(String authServ) { this.authServ = authServ; }

    public String toJSON(){
        return "{ \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"";
    }

}
