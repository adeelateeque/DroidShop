package com.droidshop.ui;

import java.util.List;

import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.model.Product;
import com.droidshop.ui.core.AlternatingColorListAdapter;

public class ListItemAdapter extends AlternatingColorListAdapter<Product> {

	public ListItemAdapter(LayoutInflater inflater, List<Product> items, boolean selectable) {
		super(R.layout.itemlist, inflater, items, selectable);
	}

	public ListItemAdapter(LayoutInflater inflater,
			List<Product> items) {
		super(R.layout.itemlist, inflater, items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[] { R.id.toptext, R.id.bottomtext };
	}

	@Override
	protected void update(int position, Product item) {
		super.update(position, item);
		setText(0, item.getName());
	}

}