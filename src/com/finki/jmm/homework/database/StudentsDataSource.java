package com.finki.jmm.homework.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentsDataSource {
	private SQLiteDatabase database;
	private SQLiteOpenHelper dbHelper;
	private String [] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_INDEKS};
	
	public StudentsDataSource(Context context){
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Student createStudent(String name, String index){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, name);
		values.put(MySQLiteHelper.COLUMN_INDEKS, index);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS, allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		Student newStudent = cursorToStudent(cursor);
		cursor.close();
		return newStudent;	
	}
	
	public Student cursorToStudent(Cursor cursor){
		Student student = new Student();
		student.setId(cursor.getLong(0));
		student.setName(cursor.getString(1));
		student.setIndex(cursor.getString(2));
		return student;
	}
	
	public void deleteStudent(Student student){
		long id = student.getId();
		database.delete(MySQLiteHelper.TABLE_STUDENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<Student> getAllStudents(){
		List<Student> students = new ArrayList<Student>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Student student = cursorToStudent(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		
		cursor.close();
		return students;
	}
}
