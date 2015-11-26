package com.example.photodiary;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	
	public User(){
		super();
		this.username = null;
		this.password = null;
		this.email = null;
	}
	
	public User(String username, String password, String email){
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void setId(int idInput){
		this.id = idInput;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public int getId(){
		return this.id;
	}
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public String getEmail(){
		return this.email;
	}

}
