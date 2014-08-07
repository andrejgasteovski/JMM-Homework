package com.example.jmm_homework;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.finki.jmm.homework.database.Student;
import com.finki.jmm.homework.database.StudentsDataSource;


public class DatabaseActivity extends ListActivity{
	StudentsDataSource dataSource;
	String newStudentName;
	String newStudentNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
	
		dataSource = new StudentsDataSource(this);
		dataSource.open();
		
//		dataSource.createStudent("Andrej Gasteovski", "111035");
//		dataSource.createStudent("Sandra Delovska", "111043");
//		dataSource.createStudent("Bojana Trajkovska", "111066");
//		dataSource.createStudent("Viktor Petrovski", "111025");
		
		initializeStudentsList();
		initializeAddNewStudentButton();
	}

	private void initializeStudentsList() {
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
			
				dataSource.createStudent(newStudentName, newStudentNumber);
				initializeStudentsList();
				
				etStudentName.setText("");
				etStudentNumber.setText("");
			}
		});
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
	
	
}
