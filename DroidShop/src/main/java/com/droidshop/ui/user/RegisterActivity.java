package com.droidshop.ui.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.TextView;
import butterknife.InjectView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.model.User;
import com.droidshop.ui.core.BootstrapFragmentActivity;
import com.droidshop.ui.core.DatePickerFragment;
import com.droidshop.util.SafeAsyncTask;
import com.github.kevinsawicki.wishlist.Toaster;

public class RegisterActivity extends BootstrapFragmentActivity
{
	User newUser = new User();
	BootstrapApi api;
	@Inject
	ApiProvider apiProvider;
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
	TextView tv_dateofbirth;
	@InjectView(R.id.sp_country)
	Spinner spCountry;
	@InjectView(R.id.sp_city)
	Spinner spCity;

	private SafeAsyncTask<Boolean> registrationTask;

	private List<String> countries;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();
		setupCountrySpinner();

		tv_dateofbirth.setOnClickListener(new OnClickListener()
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
						tv_dateofbirth.setText(yy + "/" + mm + "/" + dd);
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
		etEmail.setText("");
		etMobileNumber.setText("");
		etAddress.setText("");
		etSecretQuestion.setText("");
		etSecretAnswer.setText("");
		rgGender.clearCheck();
		tv_dateofbirth.setText(R.string.dateofbirth);
		spCountry.setSelection(0);
		spCity.setSelection(0);
	}

	public void registerUser(View view)
	{
		// means that the registration process is still in progress
		if (registrationTask != null)
			return;

		newUser.setUserName(etUsername.getText().toString());
		newUser.setPassword(etPassword.getText().toString());
		newUser.setLastName(etLastName.getText().toString());
		newUser.setFirstName(etFirstName.getText().toString());
		newUser.setEmail(etEmail.getText().toString());
		newUser.setHandphoneNo(etMobileNumber.getText().toString());
		newUser.setAddress(etAddress.getText().toString());
		newUser.setSecretQuestion(etSecretQuestion.getText().toString());
		newUser.setSecretAnswer(etSecretAnswer.getText().toString());
		newUser.setCountry(spCountry.getSelectedItem().toString());
		newUser.setCity(spCity.getSelectedItem().toString());

		registrationTask = new SafeAsyncTask<Boolean>()
		{
			public Boolean call() throws Exception
			{
				if (api == null)
				{
					api = apiProvider.getApi(RegisterActivity.this);
				}

				return api.getUserApi().registerUser(newUser);
			}

			@Override
			protected void onException(Exception e) throws RuntimeException
			{
				Throwable cause = e.getCause() != null ? e.getCause() : e;

				String message = cause.getMessage();

				Toaster.showLong(RegisterActivity.this, message);
			}

			@Override
			public void onSuccess(Boolean authSuccess)
			{
				registrationSuccessful();
			}

			@Override
			protected void onFinally() throws RuntimeException
			{
				registrationTask = null;
			}
		};
		registrationTask.execute();
	}

	private void registrationSuccessful()
	{
		Toaster.showLong(this, "Registration successful");
		Intent intent = new Intent(this, BootstrapAuthenticatorActivity.class);
		startActivity(intent);
	}
}
