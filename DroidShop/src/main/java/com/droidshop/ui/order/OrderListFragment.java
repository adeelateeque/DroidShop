package com.droidshop.ui.order;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.widget.ListView;
import android.widget.Toast;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.Order;
import com.droidshop.ui.category.OrderListAdapter;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class OrderListFragment extends ItemListFragment<Order>
{

	@Inject
	protected ApiProvider apiProvider;
	@Inject
	protected LogoutService logoutService;

	protected BootstrapApi api;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BootstrapApplication.getInstance().inject(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected void configureList(Activity activity, ListView listView)
	{
		super.configureList(activity, listView);

		listView.setFastScrollEnabled(true);
		listView.setDividerHeight(0);

		// getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.checkins_list_item_labels,
		// null));
	}

	@Override
	protected LogoutService getLogoutService()
	{
		return logoutService;
	}

	@Override
	public void onDestroyView()
	{
		setListAdapter(null);

		super.onDestroyView();
	}

	public Loader<List<Order>> onCreateLoader(int id, Bundle args)
	{
		final List<Order> initialItems = items;
		Toast.makeText(getSherlockActivity(), Integer.toString(items.size()), Toast.LENGTH_LONG).show();
		return new ThrowableLoader<List<Order>>(getSherlockActivity(), items)
		{
			@Override
			public List<Order> loadData() throws Exception
			{
				try
				{
					if (api == null)
					{
						api = apiProvider.getApi(getSherlockActivity());
					}
					List<Order> loaded = null;

					if (getSherlockActivity() != null)
						loaded = api.getOrderApi().getOrders();

					if (loaded != null)
						return loaded;
					else
						return Collections.emptyList();
				}
				catch (OperationCanceledException e)
				{
					Activity activity = getSherlockActivity();
					if (activity != null)
						activity.finish();
					return initialItems;
				}
			}
		};
	}

	@Override
	protected SingleTypeAdapter<Order> createAdapter(List<Order> items)
	{
		return new OrderListAdapter(getSherlockActivity().getLayoutInflater(), items);
	}

	@Override
	protected int getErrorMessage(Exception exception)
	{
		return R.string.error_loading_reservation;
	}
}