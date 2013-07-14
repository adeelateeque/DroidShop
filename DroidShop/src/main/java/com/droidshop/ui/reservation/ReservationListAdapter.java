package com.droidshop.ui.reservation;

import java.util.List;

import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.model.Reservation;
import com.droidshop.ui.core.AlternatingColorListAdapter;

public class ReservationListAdapter extends AlternatingColorListAdapter<Reservation> {

	public ReservationListAdapter(LayoutInflater inflater, List<Reservation> items, boolean selectable) {
		super(R.layout.reservation_list_row, inflater, items, selectable);
	}

	public ReservationListAdapter(LayoutInflater inflater,
			List<Reservation> items) {
		super(R.layout.reservation_list_row, inflater, items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[] { R.id.toptext, R.id.bottomtext };
	}

	@Override
	protected void update(int position, Reservation item) {
		super.update(position, item);
		setText(0, item.getProduct().getName());
	}

}