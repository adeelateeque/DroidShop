package com.droidshop.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.droidshop.R;
import com.droidshop.ui.category.CategoryListFragment;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ProductListActivity extends BootstrapFragmentActivity {

	protected Long categoryId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		categoryId = getIntent().getExtras().getLong(CategoryListFragment.KEY_CATEGORY_ID);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		final ProductListFragment mFragment = new ProductListFragment();
		ft.add(R.id.productListLayout, mFragment);
		ft.commit();

		/*LinearLayout listItem = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.product_list_item, null);
		Button buyNow = (Button) listItem.findViewById(R.id.buyNowBtn);
		buyNow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Go 1", Toast.LENGTH_SHORT).show();
				final int position = getListView().getPositionForView(arg0);
		        if (position != ListView.INVALID_POSITION) {
		            Intent intent = new Intent(mFragment.getActivity(), receipt.class);
		            startActivity(intent);
		        }
			}

		});*/
	}

}