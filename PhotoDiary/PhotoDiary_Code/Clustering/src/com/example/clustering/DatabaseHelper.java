package com.example.clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int database_VERSION = 1;
	// database name
	private static final String database_NAME = "PhotoDiaryDB";

	private static final String table_USER = "users";
	private static final String user_ID = "_id";
	private static final String user_USERNAME = "username";
	private static final String user_PASSWORD = "password";
	private static final String user_EMAIL = "email";

	private static final String table_DIARY = "diaryphoto";
	private static final String diary_ID = "_id";
	private static final String diary_NAME = "name";
	private static final String diary_LO = "longitude";
	private static final String diary_LA = "latitude";
	private static final String diary_TIME = "time";
	private static final String diary_URI = "uri";
	private static final String diary_USERID = "userid";

	private static final String[] USER_COLUMNS = { user_ID, user_USERNAME, user_PASSWORD, user_EMAIL };
	private static final String[] DIARY_COLUMNS = { diary_ID, diary_NAME, diary_LO, diary_LA, diary_TIME, diary_URI,
			diary_USERID };

	public DatabaseHelper(Context context) {
		super(context, database_NAME, null, database_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + table_USER + " ( " + user_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + user_USERNAME + " TEXT UNIQUE, " + user_PASSWORD + " TEXT, "
				+ user_EMAIL + " TEXT )";
		db.execSQL(CREATE_TABLE_USER);

		String CREATE_TABLE_DIARY = "CREATE TABLE IF NOT EXISTS " + table_DIARY + " ( " + diary_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + diary_NAME + " TEXT UNIQUE, " + diary_LO + " REAL, "
				+ diary_LA + " REAL, " + diary_TIME + " TEXT, " + diary_URI + " TEXT, " + diary_USERID + " TEXT )";
		db.execSQL(CREATE_TABLE_DIARY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String diary_drop = "DROP TABLE IF EXISTS " + table_DIARY;
		db.execSQL(diary_drop);
		this.onCreate(db);
	}

	public void createUser(User user) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// make values to be inserted
		ContentValues values = new ContentValues();
		values.put(user_USERNAME, user.getUsername());
		values.put(user_PASSWORD, user.getPassword());
		values.put(user_EMAIL, user.getEmail());

		// insert book
		db.insert(table_USER, null, values);

		// close database transaction
		db.close();
	}

	public void createPhoto(DiaryPhoto photo) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// make values to be inserted
		ContentValues values = new ContentValues();
		values.put(diary_NAME, photo.getName());
		values.put(diary_LO, photo.getLongitude());
		values.put(diary_LA, photo.getLatitude());
		values.put(diary_TIME, photo.getTime());
		values.put(diary_URI, photo.getUri());
		values.put(diary_USERID, photo.getUserid());

		// insert book
		db.insert(table_DIARY, null, values);

		// close database transaction
		db.close();
	}

	public User readUser(String username) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getReadableDatabase();

		// get book query
		Cursor cursor = db.query(table_USER, // a. table
				USER_COLUMNS, " username = ?", new String[] { username }, null, null, null, null);

		// if results !=null, parse the first one
		if (cursor != null)
			cursor.moveToFirst();
		User user = new User();
		user.setId(Integer.parseInt(cursor.getString(0)));
		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		user.setEmail(cursor.getString(3));

		return user;
	}

	public DiaryPhoto readDiary(String uri) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getReadableDatabase();

		// get book query
		Cursor cursor = db.query(table_DIARY, // a. table
				DIARY_COLUMNS, " uri = ?", new String[] { uri }, null, null, null, null);

		// if results !=null, parse the first one
		if (cursor != null)
			cursor.moveToFirst();

		DiaryPhoto photo = new DiaryPhoto();
		photo.setId(Integer.parseInt(cursor.getString(0)));
		photo.setName(cursor.getString(1));
		photo.setLongitude(cursor.getFloat(2));
		photo.setLatitude(cursor.getFloat(3));
		photo.setTime(cursor.getString(4));
		photo.setUri(cursor.getString(5));
		photo.setUserid(Integer.parseInt(cursor.getString(6)));

		return photo;
	}

	public ArrayList<DiaryPhoto> getAllPhotos() {
		ArrayList<DiaryPhoto> allpictures = new ArrayList<DiaryPhoto>();
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT " + "*" + " FROM " + table_DIARY;
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				DiaryPhoto temp = new DiaryPhoto(cursor.getString(1), cursor.getFloat(2), cursor.getFloat(3),
						cursor.getString(4), cursor.getString(5), 0);

				allpictures.add(temp);
			} while (cursor.moveToNext());
		}

		return allpictures;
	}

	public List<String> getAllItems() {
		List<String> usernames = new LinkedList<String>();
		String query = "SELECT " + user_USERNAME + " FROM " + table_USER;

		// get reference of the ItemDB database
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// parse all results
		if (cursor.moveToFirst()) {
			do {
				usernames.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		return usernames;
	}

	public void deleteUser(String username) {

		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// delete book
		db.delete(table_USER, user_USERNAME + " = ?", new String[] { username });
		db.close();
	}

}