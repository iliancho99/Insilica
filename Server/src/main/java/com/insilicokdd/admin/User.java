package com.insilicokdd.admin;

public class User {
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "{\r\n" + 
				"  \"username\":" + "\"" + username + "\",\r\n" + 
				"  \"password\":" + "\"" + password + "\"" + "\r\n" + 
				"}";
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
