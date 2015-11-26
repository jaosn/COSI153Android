package com.example.photodiary;


import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final int database_VERSION = 1;
	// database name
	private static final String database_NAME = "PDDB";
	
	private static final String table_DIARY = "photodiary";
	private static final String diary_ID = "id";
	private static final String diary_NAME = "name";
	private static final String diary_LO = "longitude";
	private static final String diary_LA = "latitude";
	private static final String diary_TIME = "time";
	private static final String diary_USERID = "userid";
	private static final String diary_URI = "uri";
	
	private static final String table_USER = "user";
	private static final String user_ID = "id";
	private static final String user_USERNAME = "username";
	private static final String user_PASSWORD = "password";	
	private static final String user_EMAIL = "email";	
	
	private static final String[] USER_COLUMNS = {user_ID, user_USERNAME, user_PASSWORD, user_EMAIL};
	private static final String[] DIARY_COLUMNS = {diary_ID, diary_NAME, diary_LO, diary_LA, diary_TIME, diary_URI, diary_USERID};
	
	public DatabaseHelper(Context context) {
		super(context, database_NAME, null, database_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_EXPENSE_TABLE_USER = "CREATE TABLE " + table_USER + " ( " 
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ user_USERNAME + " TEXT UNIQUE IGNORE, " 
				+ user_PASSWORD + " TEXT, " 
				+ user_EMAIL + " TEXT )";
		String CREATE_EXPENSE_TABLE_DIARY = "CREATE TABLE "+ table_DIARY + " ( " 
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ diary_NAME + " TEXT, " 
				+ diary_LO + " TEXT, " 
				+ diary_LA + " TEXT, " 
				+ diary_TIME + " TEXT, " 
				+ diary_URI + " TEXT UNIQUE IGNORE, "
				+ diary_USERID + " TEXT ) ";
				
		db.execSQL(CREATE_EXPENSE_TABLE_USER);
		db.execSQL(CREATE_EXPENSE_TABLE_DIARY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS photodiary");
		this.onCreate(db);
	}
	
	public void createUser(User user){
		// get reference of the ItemsDB database
		SQLiteDatabase db = this.getWritableDatabase();
		// make values to be inserted
		ContentValues values = new ContentValues();
		values.put(user_USERNAME, user.getUsername());
		values.put(user_PASSWORD, user.getPassword());
		values.put(user_EMAIL, user.getEmail());
		
		// insert item
		db.insert(table_USER, null, values);
		// close database transaction
		db.close();
	}
	
	public void createDiaryPhoto(DiaryPhoto photo){
		// get reference of the ItemsDB database
		SQLiteDatabase db = this.getWritableDatabase();
		// make values to be inserted
		ContentValues values = new ContentValues();
		values.put(diary_NAME, photo.getName());
		values.put(diary_LO, photo.getLongitude());
		values.put(diary_LA, photo.getLatitude());
		values.put(diary_TIME, photo.getTime());
		values.put(diary_URI, photo.getUri());
		values.put(diary_USERID, photo.getUserid());
		
		// insert item
		db.insert(table_DIARY, null, values);
		// close database transaction
		db.close();
	}
	
	public User readUser(String username){
		// get reference of the ItemDB database
		SQLiteDatabase db = this.getReadableDatabase();
		/*
		// get book query
		Cursor cursor = db.query(table_USER, // a. table
				USER_COLUMNS, " username = ?", new String[] { String.valueOf(username) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		User user = new User();
		user.setId(Integer.parseInt(cursor.getString(0)));
		user.setUsername(cursor.getString(1));	
		user.setPassword(cursor.getString(2));
		user.setEmail(cursor.getString(3));	
		return user;
		*/
		User user = new User();
		return user ;
	}
	
	public DiaryPhoto readDiaryPhoto(String uri){
		// get reference of the ItemDB database
		SQLiteDatabase db = this.getReadableDatabase();
		
		// get book query
		Cursor cursor = db.query(table_DIARY, // a. table
				DIARY_COLUMNS, " uri = ?", new String[] { String.valueOf(uri) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		DiaryPhoto photo = new DiaryPhoto();
		photo.setId(Integer.parseInt(cursor.getString(0)));
		photo.setName(cursor.getString(1));	
		photo.setLongitude(cursor.getString(2));
		photo.setLatitude(cursor.getString(3));	
		photo.setTime(cursor.getString(4));	
		photo.setUri(cursor.getString(5));	
		photo.setUserid(Integer.parseInt(cursor.getString(6)));
		return photo;
	}
	
	public void deleteUser(String username) {
		// get reference of the ItemDB database
		SQLiteDatabase db = this.getWritableDatabase();
		// delete item	
		db.delete(table_USER, user_USERNAME + " = " + username, null);
		db.close();
	}
	public void deleteDiaryPhoto(String uri) {
		// get reference of the ItemDB database
		SQLiteDatabase db = this.getWritableDatabase();
		// delete item	
		db.delete(table_DIARY, diary_URI + " = " + uri, null);
		db.close();
	}
	
	public List<DiaryPhoto> getAllPhoto(User user){
		List<DiaryPhoto> photos = new LinkedList<DiaryPhoto>	();
		String query = "SELECT * FROM " + table_DIARY + " where " + diary_USERID + " = " + user.getId();
		
		// get reference of the ItemDB database
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		// parse all results
		DiaryPhoto photo = null;
		if (cursor.moveToFirst()) {
			do {
				photo = new DiaryPhoto();
				
				photo.setId(Integer.parseInt(cursor.getString(0)));
				photo.setName(cursor.getString(1));	
				photo.setLongitude(cursor.getString(2));
				photo.setLatitude(cursor.getString(3));	
				photo.setTime(cursor.getString(4));	
				photo.setUri(cursor.getString(5));	
				photo.setUserid(Integer.parseInt(cursor.getString(6)));
				
				// Add item to items
				photos.add(photo);
			} while (cursor.moveToNext());
		}
		return photos;
	}

}
