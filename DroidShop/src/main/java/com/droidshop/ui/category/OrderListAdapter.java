package com.droidshop.ui.category;

import java.util.List;

import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.model.Order;
import com.droidshop.ui.core.AlternatingColorListAdapter;

public class OrderListAdapter extends AlternatingColorListAdapter<Order> {

	public OrderListAdapter(LayoutInflater inflater, List<Order> items, boolean selectable) {
		super(R.layout.order_list_item, inflater, items, selectable);
	}

	public OrderListAdapter(LayoutInflater inflater,
			List<Order> items) {
		super(R.layout.order_list_item, inflater, items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[] { R.id.toptext, R.id.bottomtext };
	}

	@Override
	protected void update(int position, Order item) {
		super.update(position, item);
		setText(0, item.getPrice().toString());
	}

}