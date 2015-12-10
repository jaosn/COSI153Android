package com.example.clustering;

public class DiaryPhoto {
	private int id;
	private String name;
	private float longitude;
	private float latitude;
	private String time;
	private String uri;
	private int userid;

	public DiaryPhoto(String name, float longitude, float latitude, String time, String uri, int userid) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.time = time;
		this.userid = userid;
		this.uri = uri;
	}

	public DiaryPhoto() {
	}

	public void setId(int idInput) {
		this.id = idInput;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public String getTime() {
		return this.time;
	}

	public int getUserid() {
		return this.userid;
	}

	public String getUri() {
		return this.uri;
	}
}