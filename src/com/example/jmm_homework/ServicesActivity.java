package com.example.jmm_homework;

import com.finki.jmm.homework.services.HelloService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServicesActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
	}
	
	public void startService(View view){
		startService(new Intent(getBaseContext(), HelloService.class));
	}
	
	public void stopService(View view){
		stopService(new Intent(getBaseContext(), HelloService.class));
	}
}
