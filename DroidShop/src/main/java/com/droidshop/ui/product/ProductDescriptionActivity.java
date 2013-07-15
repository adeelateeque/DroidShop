package com.droidshop.ui.product;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;
import com.droidshop.ui.reservation.ReservationListFragment;

public class ProductDescriptionActivity extends BootstrapFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_description);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ReservationListFragment mFragment = new ReservationListFragment();
		ft.add(R.id.productDescriptionLayout, mFragment);
		ft.commit();
	}

	/*protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_description);


		//final int selectedProduct = getIntent().getExtras().getInt("position");

		// Set the proper image and text
		ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
		productImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon));
		TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
		productTitleTextView.setText("Product 1 ");
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
				//Cart.addProduct(selectedProduct, quantity);

				// Close the activity
				finish();
			}
		});

	}*/


}
