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
	    findViewById(R.id.btnRegister).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "Registration", Toast.LENGTH_LONG).show();
			}
	    	
	    });
	    
	    Button login = (Button)findViewById(R.id.btnLogin);
	    
	    login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, ShoppingTab.class);
				startActivity(intent);
			}
	    	
	    });
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
