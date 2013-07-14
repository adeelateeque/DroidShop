package com.droidshop.ui.product;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidshop.R;

public class ProductDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_description);


		int selectedProduct = getIntent().getExtras().getInt("position");

		// Set the proper image and text
		ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
		productImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon));
		TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
		productTitleTextView.setText("Product " + selectedProduct);
		//productTitleTextView.setText("Product 1");
		TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
		productDetailsTextView.setText("Description of products here!");
		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
		productPriceTextView.setText("$100.00");


		// Save a reference to the quantity edit text
		final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

		Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
		addToCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check to see that a valid quantity was entered
				int quantity = 0;
				try {
					quantity = Integer.parseInt(editTextQuantity.getText()
							.toString());

					if (quantity < 0) {
						Toast.makeText(getBaseContext(),
								"Please enter a quantity of 0 or higher",
								Toast.LENGTH_SHORT).show();
						return;
					}

				} catch (Exception e) {
					Toast.makeText(getBaseContext(),
							"Please enter a numeric quantity",
							Toast.LENGTH_SHORT).show();

					return;
				}

				// If we make it here, a valid quantity was entered
				//Product product = new Product();
				//product.setQuantity(selectedProduct, quantity);

				// Close the activity
				finish();
			}
		});

	}

}
