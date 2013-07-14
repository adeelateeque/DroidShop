package com.droidshop.ui.reservation;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.Reservation;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;

public class ReservationListFragment extends ItemListFragment<Reservation> {

	@Inject
	protected ApiProvider apiProvider;
	@Inject
	protected LogoutService logoutService;

	protected BootstrapApi api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BootstrapApplication.getInstance().inject(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected void configureList(Activity activity, ListView listView) {
		super.configureList(activity, listView);

		listView.setFastScrollEnabled(true);
		listView.setDividerHeight(0);

		//getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.checkins_list_item_labels, null));
	}

	@Override
	protected LogoutService getLogoutService() {
		return logoutService;
	}

	@Override
	public void onDestroyView() {
		setListAdapter(null);

		super.onDestroyView();
	}

	public Loader<List<Reservation>> onCreateLoader(int id, Bundle args) {
		final List<Reservation> initialItems = items;
		Toast.makeText(getSherlockActivity(), Integer.toString(items.size()), Toast.LENGTH_LONG).show();
		return new ThrowableLoader<List<Reservation>>(getSherlockActivity(), items) {
			@Override
			public List<Reservation> loadData() throws Exception {
				if (api == null) {
					api = apiProvider.getApi(getSherlockActivity());
				}

				try {
					List<Reservation> latest = null;

					if (getSherlockActivity() != null)
						latest = api.getReservationApi().getReservations();

					if (latest != null)
						return latest;
					else
						return Collections.emptyList();
				} catch (OperationCanceledException e) {
					Activity activity = getSherlockActivity();
					if (activity != null)
						activity.finish();
					return initialItems;
				}
			}
		};
	}

	@Override
	protected SingleTypeAdapter<Reservation> createAdapter(List<Reservation> items) {
		return new ReservationListAdapter(getSherlockActivity().getLayoutInflater(), items);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		Toaster.showLong(getSherlockActivity(), l.getItemAtPosition(position).toString());
	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_reservation;
	}
}