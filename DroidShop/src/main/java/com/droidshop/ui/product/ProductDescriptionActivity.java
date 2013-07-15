package com.droidshop.ui.product;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.droidshop.R;
import com.droidshop.ui.product.ProductListFragment;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ProductDescriptionActivity extends BootstrapFragmentActivity {

	protected Long productId, categoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_description);
		productId = getIntent().getExtras().getLong(
				ProductListFragment.KEY_PRODUCT_ID);
		categoryId = getIntent().getExtras().getLong(
				ProductListFragment.KEY_CATEGORY_ID);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ProductDescriptionFragment mFragment = new ProductDescriptionFragment();
		ft.add(R.id.productDescriptionLayout, mFragment);
		ft.commit();
	}

}