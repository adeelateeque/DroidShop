package com.droidshop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class LoginActivity extends FragmentActivity {
	
	private FacebookFragment facebookFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    findViewById(R.id.sign_in_button).setOnClickListener(this);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
