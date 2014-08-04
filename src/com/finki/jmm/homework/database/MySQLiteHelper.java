package com.finki.jmm.homework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{

	public static final String TABLE_STUDENTS = "students";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME= "name";
	public static final String COLUMN_INDEKS= "indeks";
	
	public static final String DATABASE_NAME = "students.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_STUDENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_INDEKS + " TEXT NOT NULL)";

	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
		onCreate(db);
	}
	
}
