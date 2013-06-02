package com.droidshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    findViewById(R.id.btnRegister).setOnClickListener(this);
	    /*findViewById(R.id.admin_login).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
				startActivity(intent);
			}
	    	
	    });*/
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
