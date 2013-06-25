package com.droidshop;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class UserProfile extends FragmentActivity {
	
	private ImageButton ibUser;
	private EditText etUsername, etOPwd, etNPwd, etCPwd, etLName, etFName, etMPhone, etEmail, etAddress, etTele;
	private RadioGroup rgGender;
	private Button btnDOB;
	private Spinner spCountry, spCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		ibUser = (ImageButton) findViewById(R.id.ibUser);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etOPwd = (EditText) findViewById(R.id.etOPwd);
		etNPwd = (EditText) findViewById(R.id.etNPwd);
		etCPwd = (EditText) findViewById(R.id.etCPwd);
		etLName = (EditText) findViewById(R.id.etLName);
		etFName = (EditText) findViewById(R.id.etFName);
		etTele = (EditText) findViewById(R.id.etTele);
		etMPhone = (EditText) findViewById(R.id.etMPhone);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etAddress = (EditText) findViewById(R.id.etAddress);
		rgGender = (RadioGroup) findViewById(R.id.rgGender);
		btnDOB = (Button) findViewById(R.id.btnDOB);
		spCountry = (Spinner) findViewById(R.id.spCountry);
		spCity = (Spinner) findViewById(R.id.spCity);
		
		addItemsOnCountrySpinner();
		
		findViewById(R.id.btnDOB).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getSupportFragmentManager(), "datePicker");
			}
			
		});
		
		findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserProfile.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
		
		findViewById(R.id.btnUpdate).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}
	
	public void addItemsOnCountrySpinner() {
		 
		spCountry = (Spinner) findViewById(R.id.spCountry);
		List<String> list = new ArrayList<String>();
		list.add("Singapore");
		list.add("United States");
		list.add("Indonesia");
		list.add("France");
		list.add("Italy");
		list.add("Malaysia");
		list.add("New Zealand");
		list.add("India");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCountry.setAdapter(dataAdapter);
	}

}
