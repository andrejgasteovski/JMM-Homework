package com.example.jmm_homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MakeOrderActivity extends Activity implements OnBakcgroundColorSelected{

	RadioGroup rgPizzaSizes;
	RadioGroup rgPizzaTypes;
	CheckBox cbExtraCheese;
	CheckBox cbKetchup;
	CheckBox cbMayo;
	Button btnMakeOrder;
	TextView tvOrderStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_order);

		initializeComponents();
		
		if(savedInstanceState == null){
			tvOrderStatus.setText(getResources().getString(R.string.start_creating_order));
		}
	}
	
	public void initializeComponents(){
		rgPizzaSizes = (RadioGroup)findViewById(R.id.radioGroupSize);
		rgPizzaTypes = (RadioGroup)findViewById(R.id.radioGroupPizzaType);
		cbExtraCheese = (CheckBox)findViewById(R.id.checkBoxExtraCheese);
		cbKetchup = (CheckBox)findViewById(R.id.checkBoxKetchup);
		cbMayo = (CheckBox)findViewById(R.id.checkBoxMayo);
		tvOrderStatus = (TextView)findViewById(R.id.textViewOrderStatus);
		btnMakeOrder = (Button)findViewById(R.id.buttonContinue);
		
		initializeMakeOrderButton();
		initializeFragmentSpinner();
	}
	
	public void initializeMakeOrderButton(){
		btnMakeOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("custom", "Make Order button is clicked");
				Intent intent = new Intent(MakeOrderActivity.this, SendOrderActivity.class);
				
				Log.d("custom", "New intent is created");
				
				intent.putExtra("pizzaSize", getPizzaSize());
				intent.putExtra("pizzaType", getPizzaType());
				intent.putExtra("additionals", getAdditionals());
				
				Log.d("custom", "Extras completed");
				
				startActivity(intent);
				Log.d("custom", "Send Order Activity is started");
			}
		});
	}
	
	private CharSequence getPizzaSize(){
		int id = rgPizzaSizes.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton)rgPizzaSizes.findViewById(id);
		return radioButton.getText();
	}
	
	private CharSequence getPizzaType(){
		int id = rgPizzaTypes.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton)rgPizzaTypes.findViewById(id);
		return radioButton.getText();
	}
	
	private String getAdditionals(){
		String result = "";
		
		if(cbExtraCheese.isChecked()){
			result += cbExtraCheese.getText() + " ";
		}
		if(cbKetchup.isChecked()){
			result += cbKetchup.getText() + " ";
		}
		if(cbMayo.isChecked()){
			result += cbMayo.getText() + " ";
		}
		return result;
	}
	
	public void initializeFragmentSpinner(){
		Spinner spinner = (Spinner)findViewById(R.id.spinnerBackgroundColor);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.background_colors_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	@Override
	public void onBackgroundColorSelected(String newColor) {
		if(newColor.equals("Црвена")){
			changeBackground(R.color.maroon);
		} else if(newColor.equals("Сина")){
			changeBackground(R.color.light_blue);
		} else{
			changeBackground(R.color.teal);
		}		
	}
	
	private void changeBackground(int color) {
		rgPizzaSizes.setBackgroundColor(getResources().getColor(color));
		rgPizzaTypes.setBackgroundColor(getResources().getColor(color));
		cbExtraCheese.setBackgroundColor(getResources().getColor(color));
		cbKetchup.setBackgroundColor(getResources().getColor(color));
		cbMayo.setBackgroundColor(getResources().getColor(color));
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//Log.d("custom", "Activity onRestoreInstanceState is called");
		
		String orderStatus = savedInstanceState.getString("orderStatus");
		tvOrderStatus.setText(orderStatus);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//Log.d("custom", "Activity onStart is called");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//Log.d("custom", "Activity onResume is called");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//Log.d("custom", "Activity onSaveInstanceState is called");
		
		outState.putString("orderStatus", getResources().getString(R.string.continue_creating_order));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//Log.d("custom", "Activity onPause is called");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//Log.d("custom", "Activity onStop is called");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Log.d("custom", "Activity onDestroy is called");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		//Log.d("custom", "Activity onRestart is called");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_order, menu);
		return true;
	}
}
