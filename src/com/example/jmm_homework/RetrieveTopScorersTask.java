package com.example.jmm_homework;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.os.AsyncTask;
import android.util.Log;

public class RetrieveTopScorersTask extends AsyncTask<String, Void, Element>{

	@Override
	protected Element doInBackground(String... urls) {
		URL url;
		
		try{
			url= new URL(urls[0]);
			URLConnection connection;
			connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				InputStream in = httpConnection.getInputStream();
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				
				return docEle;
			}
		}
		catch(Exception e){
			Log.d("custom", "Excpetion while retrieving TopScorers list..");
		}	
		return null;
	}
}