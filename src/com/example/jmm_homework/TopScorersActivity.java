package com.example.jmm_homework;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TopScorersActivity extends Activity{
	private static final int MENU_PREFERENCES = Menu.FIRST + 1;
	private static final int SHOW_PREFERENCES = 1;
	
	ArrayAdapter<TopScorer> aa;
	ArrayList<TopScorer> topScorers;
	ListView lvTopScorers;
	
	private String selectedClub;
	private int minimumGoals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_top_scorers);
		
		Log.d("custom", "Content view is set");
		
		lvTopScorers = (ListView)findViewById(R.id.listViewTopScorers);
		topScorers = new ArrayList<TopScorer>();
		
		Log.d("custom", "List view is inicialized");
		
		updateFromPreferences();
		
		Log.d("custom", "Updated from preferences");
		
		int layoutID = android.R.layout.simple_list_item_1;
		aa = new ArrayAdapter<TopScorer>(TopScorersActivity.this, layoutID, topScorers);
		lvTopScorers.setAdapter(aa);
		Log.d("custom", "Adapter is set 1");
		
		initializeTopScorersList();
		Log.d("custom", "List initialized");
		refreshTopScrorersList();
		Log.d("custom", "List refreshed");
		aa.notifyDataSetChanged();
		
		Log.d("custom", "Top Scorers activity is created");
	}
	
	private void refreshTopScrorersList(){
		ArrayList<TopScorer> newList = new ArrayList<TopScorer>();
		
//		for(TopScorer ts : topScorers){
//			if(selectedClub.equals("All") || selectedClub.equals(ts.getClub())){
//				if(minimumGoals <= ts.getGoals()){
//					newList.add(ts);								
//				}
//			}
//		}
		Log.d("custom", "do tuka stiga");
		for(TopScorer ts : topScorers){
			if(ts.getClub().equals("Barcelona")){
				newList.add(ts);
			}
			
		}
		
		Log.d("custom", "i do tuka stiga");
		
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
		URL url;
		try {
			url = new URL(feed);
			URLConnection connection;
			connection = url.openConnection();
			
			Log.d("custom", "stiga do tuka 1");
			
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
		
			Log.d("custom", "stiga do tuka 2");
			
			NodeList nl = null;
			if(responseCode == HttpURLConnection.HTTP_OK){
					
				Log.d("custom", "stiga do tuka 3");
				
				InputStream in = httpConnection.getInputStream();
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				nl = docEle.getElementsByTagName("topscorer");
					
				Log.d("custom", "stiga do tuka 4");
				
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
			} 
		}catch (IOException e) {
			Log.d("custom", "IO Exception");
		} catch (SAXException e) {
			Log.d("custom", "SAX Exception");
		} catch (ParserConfigurationException e) {
			Log.d("custom", "Parser Configuration Exception");
		}
			
		Log.d("custom", "stiga do tuka 5");
		
		Collections.sort(topScorers, new Comparator<TopScorer>() {
			@SuppressLint("NewApi")
			@Override
			public int compare(TopScorer o1, TopScorer o2) {
				if(o1.getGoals() > o2.getGoals()) return -1;
				else if (o1.getGoals() < o2.getGoals()) return 1;
				else return 0;
			}
		});
		
		Log.d("custom", "stiga do tuka 6");
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
}
