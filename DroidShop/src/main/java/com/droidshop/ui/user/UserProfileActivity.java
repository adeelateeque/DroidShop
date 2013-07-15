package com.droidshop.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class UserProfileActivity extends BootstrapFragmentActivity
{

	private Spinner spCountry;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		addItemsOnCountrySpinner();

		findViewById(R.id.btnDOB).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				DialogFragment newFragment = new DialogFragment();
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}

		});

		findViewById(R.id.btnUpdate).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
			}

		});
	}

	public void addItemsOnCountrySpinner()
	{

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
