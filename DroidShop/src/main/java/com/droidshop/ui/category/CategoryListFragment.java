package com.droidshop.ui.category;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.Category;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class CategoryListFragment extends ItemListFragment<Category>
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

	@Override
	public Loader<List<Category>> onCreateLoader(int id, Bundle args)
	{
		final List<Category> initialItems = items;
		return new ThrowableLoader<List<Category>>(getSherlockActivity(), items)
		{
			@Override
			public List<Category> loadData() throws Exception
			{
				try
				{
					if (api == null)
					{
						api = apiProvider.getApi(getSherlockActivity());
					}

					List<Category> loaded = null;

					if (getSherlockActivity() != null)
						loaded = api.getCategoryApi().getCategories();

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
	protected SingleTypeAdapter<Category> createAdapter(List<Category> items)
	{
		return new CategoryListAdapter(getSherlockActivity().getLayoutInflater(), items);
	}

	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// Category category = ((Category) l.getItemAtPosition(position));
	}

	@Override
	protected int getErrorMessage(Exception exception)
	{
		return R.string.error_loading_category;
	}
}
