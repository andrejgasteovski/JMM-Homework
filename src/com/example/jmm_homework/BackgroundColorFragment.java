package com.example.jmm_homework;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class BackgroundColorFragment extends Fragment{
	private OnBakcgroundColorSelected onBackgroundColorSelected;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_background_selection, container, false);
		Spinner spinner = (Spinner)view.findViewById(R.id.spinnerBackgroundColor);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.background_colors_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(onBackgroundColorSelected));
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try{
			onBackgroundColorSelected = (OnBakcgroundColorSelected)activity;
			Log.d("custom", "A reference from BackgroundColorFragment to the main activity is created");
			
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString() + " must implement OnBackgroundColorSelected interface!");
		}
	}
}

class CustomOnItemSelectedListener implements OnItemSelectedListener {
	 
	private OnBakcgroundColorSelected onBackgroundColorSelected;
	
	public CustomOnItemSelectedListener(OnBakcgroundColorSelected a){
		this.onBackgroundColorSelected = a;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		Log.d("custom", "New background color is selected");
		String color = (String)parent.getItemAtPosition(pos);
		onBackgroundColorSelected.onBackgroundColorSelected(color);
	}
	 
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// do nothing
	}
}