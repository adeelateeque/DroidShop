package com.droidshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
<<<<<<< HEAD
	    findViewById(R.id.btnRegister).setOnClickListener(new OnClickListener(){
=======
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
>>>>>>> db5e10d6318ef02b331af60870e518e76a889d4d

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "Registration", Toast.LENGTH_LONG).show();
			}
	    	
<<<<<<< HEAD
	    });
=======
	    });*/
>>>>>>> 1239e85cfdc01d80f037c1ee45d18f7f9dd6019b
>>>>>>> db5e10d6318ef02b331af60870e518e76a889d4d
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.admin_login:
        	adminLogin();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private void adminLogin(){
		Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
		startActivity(intent);
	}
}
