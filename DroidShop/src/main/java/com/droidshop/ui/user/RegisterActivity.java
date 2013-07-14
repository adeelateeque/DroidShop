package com.droidshop.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import butterknife.InjectView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;
import com.droidshop.ui.core.DatePickerFragment;

public class RegisterActivity extends BootstrapFragmentActivity
{
	@InjectView(R.id.et_username)
	EditText etUsername;
	@InjectView(R.id.et_password)
	EditText etPassword;
	@InjectView(R.id.et_confirmpassword)
	EditText etConfirmPassword;
	@InjectView(R.id.et_firstname)
	EditText etFirstName;
	@InjectView(R.id.et_lastname)
	EditText etLastName;
	@InjectView(R.id.et_mobilenumber)
	EditText etMobileNumber;
	@InjectView(R.id.et_phonenumber)
	EditText etPhoneNumber;
	@InjectView(R.id.et_email)
	EditText etEmail;
	@InjectView(R.id.et_address)
	EditText etAddress;
	@InjectView(R.id.et_secretquestion)
	EditText etSecretQuestion;
	@InjectView(R.id.et_secretanswer)
	EditText etSecretAnswer;
	@InjectView(R.id.rg_gender)
	RadioGroup rgGender;
	@InjectView(R.id.et_dateofbirth)
	EditText et_dateofbirth;
	@InjectView(R.id.sp_country)
	Spinner spCountry;
	@InjectView(R.id.sp_city)
	Spinner spCity;

	private List<String> countries;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();
		setupFacebook();
		setupCountrySpinner();

		et_dateofbirth.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				DialogFragment newFragment = new DatePickerFragment()
				{
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
					{
						String yy = Integer.toString(year);
						String mm = Integer.toString(monthOfYear + 1);
						if (mm.length() == 1)
						{
							mm = "0" + mm;
						}
						String dd = Integer.toString(dayOfMonth);
						if (dd.length() == 1)
						{
							dd = "0" + dd;
						}
						et_dateofbirth.setText(yy + "/" + mm + "/" + dd);
					}

				};
				newFragment.show(getSupportFragmentManager(), "datePicker");

			}

		});

		spCountry.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				String country = countries.get((int) id);
				if (country != null && !country.equals(""))
				{
					updateCitySpinner(country);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Calls a web service to retrieve a list of countries
	 */
	public void setupCountrySpinner()
	{
		countries = new ArrayList<String>();
		countries.add("Singapore");
		countries.add("United States");
		countries.add("Indonesia");
		countries.add("France");
		countries.add("Italy");
		countries.add("Malaysia");
		countries.add("New Zealand");
		countries.add("India");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCountry.setAdapter(dataAdapter);
	}

	/**
	 * Calls a web service to retrieve list of cities for the selected country
	 */
	public void updateCitySpinner(String country)
	{
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void resetForm(View view)
	{
		etUsername.setText("");
		etPassword.setText("");
		etConfirmPassword.setText("");
		etLastName.setText("");
		etFirstName.setText("");
		etPhoneNumber.setText("");
		etEmail.setText("");
		etMobileNumber.setText("");
		etAddress.setText("");
		etSecretQuestion.setText("");
		etSecretAnswer.setText("");
		rgGender.clearCheck();
		et_dateofbirth.setText(R.string.dateofbirth);
		spCountry.setSelection(0);
		spCity.setSelection(0);
	}

	public void registerUser(View view)
	{

	}

	public void setupFacebook()
	{

	}

}
