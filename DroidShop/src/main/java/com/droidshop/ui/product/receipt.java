package com.droidshop.ui.product;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.droidshop.R;

public class receipt extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt);

		Bundle bundle = this.getIntent().getExtras();
		CharSequence name = bundle.getCharSequence("name");
		CharSequence price = bundle.getCharSequence("price");
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

		Random randomNum = new Random();

		TextView invoice = (TextView)findViewById(R.id.invoiceID);
		invoice.setText(String.valueOf(randomNum.nextInt(1000000000)));
		TextView itemName = (TextView)findViewById(R.id.itemDescription);
		itemName.setText("Item: " + name);
		TextView itemPrice = (TextView)findViewById(R.id.totalPrice);
		itemPrice.setText("Total Price: " + price);
		TextView date = (TextView)findViewById(R.id.dateTime);
		date.setText(currentDateTimeString);
	}
}
