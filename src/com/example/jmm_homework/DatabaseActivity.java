package com.example.jmm_homework;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.finki.jmm.homework.database.Student;
import com.finki.jmm.homework.database.StudentsDataSource;


public class DatabaseActivity extends ListActivity{
	StudentsDataSource dataSource;
	
	
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
		
		List<Student> values = dataSource.getAllStudents();
		ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
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
