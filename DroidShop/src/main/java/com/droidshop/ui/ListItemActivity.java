package com.droidshop.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ListItemActivity extends BootstrapFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemlist);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ListItemFragment mFragment = new ListItemFragment();
		ft.add(R.id.itemListLayout, mFragment);
		ft.commit();
	}

}