package com.example.photodiary;

public class DiaryPhoto {
	private int id;
	private String name;
	private String longitude;
	private String latitude;
	private String time;
	private String uri;
	private int userid;
	
	
	public DiaryPhoto(String name, String longitude, String latitude, String time, String uri,int userid){
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.time = time;
		this.userid = userid;
		this.uri =uri;
	}
	public DiaryPhoto(){
	}
	
	public void setId(int idInput){
		this.id = idInput;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	public void setTime(String time){
		this.time = time;
	}
	public void setUserid(int userid){
		this.userid = userid;
	}
	public void setUri(String uri){
		this.uri = uri;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	public String getLongitude(){
		return this.longitude;
	}
	public String getLatitude(){
		return this.latitude;
	}
	public String getTime(){
		return this.time;
	}
	public int getUserid(){
		return this.userid;
	}
	public String getUri(){
		return this.uri;
	}
}
