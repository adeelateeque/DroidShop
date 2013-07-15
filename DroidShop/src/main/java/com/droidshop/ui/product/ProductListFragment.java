package com.droidshop.ui.product;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
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
import com.droidshop.model.AbstractEntity;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class ProductListFragment extends ItemListFragment<Product>
{

	@Inject
	protected ApiProvider apiProvider;
	@Inject
	protected LogoutService logoutService;

	protected BootstrapApi<AbstractEntity> api;

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

	public Loader<List<Product>> onCreateLoader(int id, Bundle args)
	{
		final List<Product> initialItems = items;
		Toast.makeText(getSherlockActivity(), Integer.toString(items.size()), Toast.LENGTH_LONG).show();
		return new ThrowableLoader<List<Product>>(getSherlockActivity(), items)
		{
			@Override
			public List<Product> loadData() throws Exception
			{
				try
				{
					if (api == null)
					{
						api = apiProvider.getApi(getSherlockActivity());
					}
					List<Product> latest = null;


					if (getSherlockActivity() != null){
						ProductListActivity activity = (ProductListActivity) getSherlockActivity();
						Long categoryId = activity.categoryId;
						latest = api.getProductApi().getProductsForCategory(new Category(categoryId));
					}

					if (latest != null)
						return latest;
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
	protected SingleTypeAdapter<Product> createAdapter(List<Product> items)
	{
		return new ProductListAdapter(getSherlockActivity().getLayoutInflater(), items);
	}

	public void onListItemClick(ListView l, View v, int position, long id)
	{

		Intent intent = new Intent(getActivity(), ProductDescriptionActivity.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}

	@Override
	protected int getErrorMessage(Exception exception)
	{
		return R.string.error_loading_reservation;
	}
}