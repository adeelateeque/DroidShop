package com.droidshop.ui.reservation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ReservationActivity extends BootstrapFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ReservationListFragment mFragment = new ReservationListFragment();
		ft.add(R.id.reservationLayout, mFragment);
		ft.commit();
	}

}