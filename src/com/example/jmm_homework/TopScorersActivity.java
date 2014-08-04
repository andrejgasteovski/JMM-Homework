package com.example.jmm_homework;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class TopScorersActivity extends Activity{
	private static final int MENU_PREFERENCES = Menu.FIRST + 1;
	private static final int SHOW_PREFERENCES = 1;
	
	ArrayAdapter<TopScorer> aa;
	ArrayList<TopScorer> topScorers;
	ListView lvTopScorers;
	Button nextActivity;
	
	private String selectedClub;
	private int minimumGoals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_top_scorers);
		initializeNextActivityButton();
	
		lvTopScorers = (ListView)findViewById(R.id.listViewTopScorers);
		topScorers = new ArrayList<TopScorer>();

		updateFromPreferences();

		initializeTopScorersList();
		refreshTopScrorersList();
		
		int layoutID = android.R.layout.simple_list_item_1;
		aa = new ArrayAdapter<TopScorer>(TopScorersActivity.this, layoutID, topScorers);
		aa.notifyDataSetChanged();
		lvTopScorers.setAdapter(aa);
	}
	
	private void refreshTopScrorersList(){
		ArrayList<TopScorer> newList = new ArrayList<TopScorer>();
		
		for(TopScorer ts : topScorers){
			if(selectedClub.equals("All") || selectedClub.equals(ts.getClub())){
				if(minimumGoals <= ts.getGoals()){
					newList.add(ts);								
				}
			}
		}
	
		topScorers = newList;
	}
	
	private void updateFromPreferences(){
		Context context = getApplicationContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		String minimumGoalsString = prefs.getString(PreferencesActivity.MINIMUM_GOALS, "1");
		
		minimumGoals = Integer.parseInt(minimumGoalsString);
		selectedClub = prefs.getString(PreferencesActivity.FOOTBALL_CLUB, "All");
	}
	
	
	private void initializeTopScorersList(){
		String feed = "https://dl.dropboxusercontent.com/s/ka77pgyfqbi07g4/topscorer.xml";
		
		NodeList nl = null;
		try {
			Element docEle = new RetrieveTopScorersTask().execute(feed).get();
			nl = docEle.getElementsByTagName("topscorer");
			
			if(nl != null && nl.getLength() > 0){
				for(int i = 0; i < nl.getLength(); i++){
					Element entry = (Element)nl.item(i);
						
					String name = entry.getAttribute("player");
					String club = entry.getAttribute("participantname");
					int goals = Integer.parseInt(entry.getAttribute("goals"));
					
					TopScorer ts = new TopScorer(name, club, goals);
					topScorers.add(ts);						
				}
			}
		} catch (InterruptedException e) {
			Log.d("custom", "Interrupted exception while executing AsyncTask..");
		} catch (ExecutionException e) {
			Log.d("custom", "Execution exception while executing AsyncTask..");
		}
		
		Collections.sort(topScorers, new Comparator<TopScorer>() {
			@SuppressLint("NewApi")
			@Override
			public int compare(TopScorer o1, TopScorer o2) {
				if(o1.getGoals() > o2.getGoals()) return -1;
				else if (o1.getGoals() < o2.getGoals()) return 1;
				else return 0;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
	
		if(item.getItemId() == MENU_PREFERENCES){
			Intent intent = new Intent(this, PreferencesActivity.class);
			startActivityForResult(intent, SHOW_PREFERENCES);
			return true;
		}		
		return false;
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		
		updateFromPreferences();
		refreshTopScrorersList();

		aa.notifyDataSetChanged();
		
	}
	
	public void initializeNextActivityButton(){
		Button btnNextActivity = (Button)findViewById(R.id.buttonToDatabaseActivity);
		btnNextActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("custom", "Next activity button is pressed");
				Intent intent = new Intent(TopScorersActivity.this, DatabaseActivity.class);
				startActivity(intent);
				Log.d("custom", "Top Scorers activity is started");
			}
		});
	}
}
