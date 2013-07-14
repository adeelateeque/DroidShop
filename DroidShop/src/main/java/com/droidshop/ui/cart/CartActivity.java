package com.droidshop.ui.cart;


import java.util.List;

import com.droidshop.R;
import com.droidshop.model.Product;
import com.droidshop.ui.product.ProductAdapter;
import com.droidshop.ui.product.ProductDescriptionActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CartActivity extends Activity {

	private List<Product> mCartList;
	private ProductAdapter mProductAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);


		mCartList = Cart.getCartList();

		// Make sure to clear the selections
		for(int i=0; i<mCartList.size(); i++) {
			mCartList.get(i).selected = false;
		}

		// Create the list
		final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
		mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
		listViewCatalog.setAdapter(mProductAdapter);

		listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent productDetailsIntent = new Intent(getBaseContext(),ProductDescriptionActivity.class);
				productDetailsIntent.putExtra(Cart.PRODUCT_INDEX, position);
				startActivity(productDetailsIntent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Refresh the data
		if(mProductAdapter != null) {
			mProductAdapter.notifyDataSetChanged();
		}
	}

}
