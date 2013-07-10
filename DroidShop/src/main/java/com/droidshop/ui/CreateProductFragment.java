package com.droidshop.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.droidshop.R;

public class CreateProductFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView view = (ScrollView) inflater.inflate(R.layout.create_product_view, null);

		return view;
	}

}
