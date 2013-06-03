package com.droidshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    findViewById(R.id.btnRegister).setOnClickListener(this);
<<<<<<< HEAD
	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        facebookFragment = new FacebookFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, facebookFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        facebookFragment = (FacebookFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	   
=======
	    /*findViewById(R.id.admin_login).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
				startActivity(intent);
			}
	    	
	    });*/
>>>>>>> 1239e85cfdc01d80f037c1ee45d18f7f9dd6019b
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "Registration", Toast.LENGTH_LONG).show();
	}
}
