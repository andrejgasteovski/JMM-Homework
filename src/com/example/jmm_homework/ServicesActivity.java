package com.example.jmm_homework;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.finki.jmm.homework.rss_feed.FeedMessage;
import com.finki.jmm.homework.services.HelloService;

public class ServicesActivity extends Activity{
	HelloService mService;
	boolean mBound = false;
	ArrayAdapter<FeedMessage> aa;
	ListView lvMessages;
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {	
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if(bundle != null){
				List<FeedMessage> messages = (ArrayList<FeedMessage>)bundle.getSerializable(HelloService.MESSAGES);
				int resultCode = bundle.getInt(HelloService.RESULT);
				
				if(resultCode == RESULT_OK){
					Toast.makeText(ServicesActivity.this, "Parsing completed", Toast.LENGTH_SHORT).show();
					
					int layoutID = android.R.layout.simple_list_item_1;
					aa = new ArrayAdapter<FeedMessage>(ServicesActivity.this, layoutID, messages);
					aa.notifyDataSetChanged();
					lvMessages.setAdapter(aa);
				}else{
					Toast.makeText(ServicesActivity.this, "Parsing failed", Toast.LENGTH_SHORT).show();
				}
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		initializeListMessages();
	}
	
	private void initializeListMessages(){
		lvMessages = (ListView)findViewById(R.id.listViewRssFeed);
		lvMessages.setClickable(true);
		//so ova se pravi koga ke se klikne na nekoj naslov na vest, da ja otvori vesta vo browser
		lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				FeedMessage message = (FeedMessage)lvMessages.getItemAtPosition(position);
				String url = message.getLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiver, new IntentFilter(HelloService.NOTIFICATION));
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}

	public void populateRssFeed(View view){
		Intent intent = new Intent(this, HelloService.class);
		startService(intent);
		Toast.makeText(ServicesActivity.this, "Service started", Toast.LENGTH_SHORT).show();
	}

	
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		Intent intent = new Intent(this, HelloService.class);
//		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//		//initializeRssFeedList();
//	}
//	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		if(mBound){
//			unbindService(mConnection);
//			mBound = false;
//		}
//	}
	

	
//	private void initializeRssFeedList(){
//		ListView list = (ListView)findViewById(R.id.listViewRssFeed);
//		Log.d("homework", "stiga do tuka 1");
//		List<FeedMessage> messages = mService.getMessages();
//		Log.d("homework", "stiga do tuka 2");
//		int layoutID = android.R.layout.simple_list_item_1;
//		aa = new ArrayAdapter<FeedMessage>(ServicesActivity.this, layoutID, messages);
//		aa.notifyDataSetChanged();
//		Log.d("homework", "stiga do tuka 3");
//		list.setAdapter(aa);
//		Log.d("homework", "stiga do tuka 4");
//	}
		
//	private ServiceConnection mConnection = new ServiceConnection() {
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			mBound = false;
//		}
//		
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			HelloBinder binder = (HelloBinder)service;
//			mService = binder.getService();
//			mBound = true;
//		}
//	};
}
