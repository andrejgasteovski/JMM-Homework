package com.example.jmm_homework;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.finki.jmm.homework.contentprovider.MyContentProvider;
import com.finki.jmm.homework.database.MySQLiteHelper;
import com.finki.jmm.homework.database.Student;
import com.finki.jmm.homework.database.StudentsDataSource;


public class DatabaseActivity extends ListActivity{
	StudentsDataSource dataSource;
	ContentResolver cr;
	String newStudentName;
	String newStudentNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
	
		dataSource = new StudentsDataSource(this);
		//initializeStudentsListWithDAO();

		cr = getContentResolver();
		initializeStudentsListWithCR();
		
		initializeAddNewStudentButton();
		initializeNextActivityButton();
	}
	
	private void initializeStudentsListWithCR(){
		String [] resultColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_INDEKS};
		Cursor cursor = cr.query(MyContentProvider.CONTENT_URI, resultColumns, null, null, null);
		
		List<Student> students = new ArrayList<Student>();
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Student student = new Student();
			student.setId(cursor.getLong(0));
			student.setName(cursor.getString(1));
			student.setIndex(cursor.getString(2));
			students.add(student);
			cursor.moveToNext();
		}
		
		ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, students);
		setListAdapter(adapter);
	}

	private void initializeStudentsListWithDAO() {
		dataSource.open();
		List<Student> values = dataSource.getAllStudents();
		ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	private void initializeAddNewStudentButton(){
		Button button = (Button)findViewById(R.id.buttonAddNewStudent);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etStudentName = (EditText)findViewById(R.id.editTextStudentName);
				EditText etStudentNumber = (EditText)findViewById(R.id.editTextStudentNumber);

				setNewStudentName(etStudentName.getText().toString());
				setNewStudentNumber(etStudentNumber.getText().toString());
			
				//insertStudentWithDAO();
				//initializeStudentsListWithDAO();

				insertStudentWithCR();
				initializeStudentsListWithCR();
				
				etStudentName.setText("");
				etStudentNumber.setText("");
			}
		});
	}
	
	private void insertStudentWithDAO(){
		dataSource.createStudent(newStudentName, newStudentNumber);
	}
	
	private void insertStudentWithCR(){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, newStudentName);
		values.put(MySQLiteHelper.COLUMN_INDEKS, newStudentNumber);
	
		cr.insert(MyContentProvider.CONTENT_URI, values);
	}
	
	private void setNewStudentName(String name){
		this.newStudentName = name;
	}
	
	private void setNewStudentNumber(String number){
		this.newStudentNumber = number;
	}
	
	@Override
	protected void onResume() {
		dataSource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		dataSource.close();
		super.onPause();
	}
	
	private void initializeNextActivityButton(){
		Button button = (Button)findViewById(R.id.buttonToServicesActivity);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("custom", "Next activity button is pressed");
				Intent intent = new Intent(DatabaseActivity.this, ServicesActivity.class);
				startActivity(intent);
				Log.d("custom", "Top Scorers activity is started");	
			}
		});
	}
}
