package com.example.jmm_homework;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MakeOrderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_order);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_order, menu);
		return true;
	}

}
