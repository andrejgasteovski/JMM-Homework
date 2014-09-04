package com.finki.jmm.homework.services;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.finki.jmm.homework.rss_feed.FeedMessage;

public class HelloService extends IntentService{
//	private final IBinder mBinder = new HelloBinder();
//	private final Random mGenerator = new Random();
	
	public static final String MESSAGES = "messages";
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "com.example.jmm_homework";
	
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LINK = "link";
	static final String ITEM = "item";
	static final String CHANNEL = "channel";
	
	private final String URL_FEED = "http://news.nationalgeographic.com/index.rss";
	
	public HelloService(){
		super("HelloService");
	}
	
//	@Override
//	public IBinder onBind(Intent intent) {
//		Toast.makeText(this, "Service bound", Toast.LENGTH_LONG).show();
//		return mBinder;
//	}
//	
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		//Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
//		return START_STICKY;
//	}
//	
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show();
//	}
	
//	public class HelloBinder extends Binder {
//        public HelloService getService() {
//            // Return this instance of LocalService so clients can call public methods
//            return HelloService.this;
//        }
//    }
	
//	public int getRandomNumber() {
//	      return mGenerator.nextInt(100);
//	    }
	
//	public List<FeedMessage> getMessages(){
//		RssFeedParser parser = new RssFeedParser(URL_FEED);
//		Log.d("homework", "stiga do tuka 4");
//		return parser.readFeed();
//		return null;
//	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		List<FeedMessage> messages = null;
		int result = -1;
		
		try{
			URL url = new URL(URL_FEED);
			URLConnection connection = url.openConnection();
			
			Log.d("homework", "stiga do tuka 10");
			
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
			InputStream in = null;
			
			Log.d("homework", "stiga do tuka 9");
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				Log.d("homework", "stiga do tuka 12");
				in = httpConnection.getInputStream();
				Log.d("homework", "stiga do tuka 13");
			}
			
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(in, null);
			
			Log.d("homework", "stiga do tuka 5");
			
			int eventType = parser.getEventType();
			FeedMessage currentMessage = null;
			boolean done = false;
			
			Log.d("homework", "stiga do tuka 6");
			
			while(eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch(eventType){
				
				case XmlPullParser.START_DOCUMENT:
					messages = new ArrayList<FeedMessage>();
					break;
				
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if(name.equalsIgnoreCase(ITEM)){
						currentMessage = new FeedMessage();
					} else if(currentMessage != null){
						if(name.equalsIgnoreCase(TITLE)){
							currentMessage.setTitle(parser.nextText());
						}else if(name.equalsIgnoreCase(DESCRIPTION)){
							currentMessage.setDescription(parser.nextText());
						}else if(name.equalsIgnoreCase(LINK)){
							currentMessage.setLink(parser.nextText());
						}
					}
					break;
				
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if(name.equalsIgnoreCase(ITEM) && currentMessage != null){
						messages.add(currentMessage);
					}else if(name.equalsIgnoreCase(CHANNEL)){
						done = true;
					}
					break;
				}
				eventType = parser.next();
			}
			
			result = Activity.RESULT_OK;
		}catch(Exception e){
			Log.d("homework", e.getMessage());
			throw new RuntimeException(e);
		}
		
		Log.d("homework", "stiga do tuka 4");
		
		Intent intent = new Intent(NOTIFICATION);
		//intent.putStringArrayListExtra(MESSAGES, (ArrayList<String>) messages);
		//intent.putExtra(MESSAGES, messages);
		intent.putExtra(MESSAGES, (Serializable) messages);
		intent.putExtra(RESULT, result);
		sendBroadcast(intent);
	}
}
