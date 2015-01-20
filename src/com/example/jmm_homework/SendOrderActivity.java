package com.example.jmm_homework;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SendOrderActivity extends Activity{
private static final int PICK_CONTACT_SUBACTIVITY = 1;
	
	private String pizzaType;
	private String pizzaSize;
	private String additionals;
	private TextView tvOrderContent;
	private String telNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_send_order);
			
		Intent intent = getIntent();
		pizzaType = intent.getStringExtra("pizzaType");
		pizzaSize = intent.getStringExtra("pizzaSize");
		additionals = intent.getStringExtra("additionals");
			
		tvOrderContent = (TextView)findViewById(R.id.textViewOrderContent);
		tvOrderContent.setText(String.format("Големина на пица: %s\nТип на пица: %s\nДодатоци: %s\n", pizzaSize, pizzaType, additionals));
		
		initializeChooseContactButton();
		initializeSendMessageButton();
		initializeNextActivityButton();		
	}
	
	public void initializeChooseContactButton(){
		Button btnPickContact = (Button)findViewById(R.id.buttonPickContact);
		btnPickContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				pickContactIntent.setType(Phone.CONTENT_TYPE);
				PackageManager pm = getPackageManager();
				ComponentName cn = pickContactIntent.resolveActivity(pm);
				if(cn == null){
					Log.d("custom", "Contacts application is not available");
				}
				else{
					startActivityForResult(pickContactIntent, PICK_CONTACT_SUBACTIVITY);
					Log.d("custom", "Activity for picking contact started succesfully");
				}
				
			}
		});
	}
	
	public void initializeSendMessageButton(){
		Button btnSendMessage = (Button)findViewById(R.id.buttonSendMessage);
		btnSendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("custom", "Sending SMS to " + telNumber); 
				Uri smsUri = Uri.parse("sms:" + telNumber);
				Intent sendSMSIntent = new Intent(Intent.ACTION_VIEW, smsUri);
				sendSMSIntent.putExtra("sms_body", tvOrderContent.getText());
		        startActivity(sendSMSIntent);
		        Log.d("custom", "SMS sent ssucsessfully"); 
			}
		});
	}
	
	public void initializeNextActivityButton(){
		Button btnNextActivity = (Button)findViewById(R.id.buttonNextActivity);
		btnNextActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("custom", "Next activity button is pressed");
				Intent intent = new Intent(SendOrderActivity.this, TopScorersActivity.class);
				startActivity(intent);
				Log.d("custom", "Top Scorers activity is started");
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("custom", "Result from an activity returned");
		super.onActivityResult(requestCode, resultCode, data);
		
		
		switch(requestCode){
			case(PICK_CONTACT_SUBACTIVITY):{
				Log.d("custom", "Result from Picking contact activity returned");
				if(resultCode == Activity.RESULT_OK){
					Uri contactData = data.getData();
					String [] projection = {Phone.NUMBER};
					Cursor cursor = getContentResolver().query(contactData, projection, null, null, null);					
					Log.d("custom", "Cursor initialized");					
					
					if(cursor.moveToFirst()){
						int column = cursor.getColumnIndex(Phone.NUMBER);
						telNumber = cursor.getString(column);
						
						TextView tvContact = (TextView)findViewById(R.id.textViewContact);
						tvContact.setText("Број за нарачка: " + telNumber);
						Log.d("custom", "Contact number set in textview");
					}
				}
				break;
			}
		}
	}
	
}
