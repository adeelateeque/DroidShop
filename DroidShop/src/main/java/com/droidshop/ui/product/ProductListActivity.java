package com.droidshop.ui.product;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ProductListActivity extends BootstrapFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ProductListFragment mFragment = new ProductListFragment();
		ft.add(R.id.itemListLayout, mFragment);
		ft.commit();
	}

}