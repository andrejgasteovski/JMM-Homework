package com.example.jmm_homework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PreferencesActivity extends Activity{
	public static final String MINIMUM_GOALS = "GOALS";
	public static final String FOOTBALL_CLUB = "FOOTBALL_CLUB";
	
	Spinner spMinimumGoals;
	Spinner spFootballClub;
	
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		initializeSpinners();
		updatePreferences();
		initializeButtons();
	}
	
	private void initializeSpinners(){
		spMinimumGoals = (Spinner)findViewById(R.id.spinnerMinimumGoals);
		spFootballClub = (Spinner)findViewById(R.id.spinnerFootballClub);
	
		ArrayAdapter<CharSequence> goalsAdapter;
		goalsAdapter = ArrayAdapter.createFromResource(this, R.array.minimum_goals_array, android.R.layout.simple_spinner_item);
		goalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spMinimumGoals.setAdapter(goalsAdapter);
	
		ArrayAdapter<CharSequence> clubAdapter;
		clubAdapter = ArrayAdapter.createFromResource(this, R.array.football_club_array, android.R.layout.simple_spinner_item);
		clubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spFootballClub.setAdapter(clubAdapter);
	}
	
	/*
	 * this method sets value of the spinners
	 * if there are existing preferences, values are set according to them
	 * otherwise, they are set to default values
	 * the method is called when preferences activity is craeted
	 */
	private void updatePreferences(){
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		String minimumGoals = prefs.getString(MINIMUM_GOALS, "1");
		String footballClub = prefs.getString(FOOTBALL_CLUB, "All");
	
		ArrayAdapter aa1 = (ArrayAdapter)spFootballClub.getAdapter();
		ArrayAdapter aa2 = (ArrayAdapter)spMinimumGoals.getAdapter();
		
		int footballClubPosition = aa1.getPosition(footballClub);
		int minimumGoalsPosition = aa2.getPosition(minimumGoals);
		
		spMinimumGoals.setSelection(minimumGoalsPosition);
		spFootballClub.setSelection(footballClubPosition);
	}
	
	private void initializeButtons(){
		Button btnOk = (Button)findViewById(R.id.okButton);
		Button btnCancel = (Button)findViewById(R.id.cancelButton);
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				savePreferences();
				PreferencesActivity.this.setResult(RESULT_OK);
				finish();
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferencesActivity.this.setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
	
	/*
	 * method is called when OK button is pressed
	 */
	private void savePreferences(){
		String minimumGoals =  spMinimumGoals.getSelectedItem().toString();
		String footballClub = spFootballClub.getSelectedItem().toString();
		
		Editor editor = prefs.edit();
		editor.putString(MINIMUM_GOALS, minimumGoals);
		editor.putString(FOOTBALL_CLUB, footballClub);
		editor.commit();
	}
	
}
