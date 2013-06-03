package com.droidshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class mySpinCatalog extends Activity {
	ArrayAdapter<CharSequence> adapter;
	TextView selection;
	String[] items = {"Clothes", "Electronics", "Books"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_listview);
		
		Spinner spin = (Spinner)findViewById(R.id.CatalogSpinner);
		
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		selection = (TextView)findViewById(R.id.selected);
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				String text = "Category is.. [" + position + "] " + items[position];
				selection.setText(text);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				selection.setText("Category is...");
			}
			
		});
	}
}
