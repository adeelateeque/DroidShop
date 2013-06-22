package com.droidshop.ui;

import static com.droidshop.core.Constants.Extra.USER;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.BootstrapApi;
import com.droidshop.api.ApiProvider;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.core.AvatarLoader;
import com.droidshop.model.User;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class UserListFragment extends ItemListFragment<User>
{

	@Inject
	ApiProvider apiProvider;
	@Inject
	AvatarLoader avatars;
	@Inject
	LogoutService logoutService;
	protected BootstrapApi api;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BootstrapApplication.getInstance().inject(this);

		try
		{
			api = apiProvider.getApi(getActivity());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (AccountsException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		setEmptyText(R.string.no_users);
	}

	@Override
	protected void configureList(Activity activity, ListView listView)
	{
		super.configureList(activity, listView);

		listView.setFastScrollEnabled(true);
		listView.setDividerHeight(0);

		getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.user_list_item_labels, null));
	}

	@Override
	protected LogoutService getLogoutService()
	{
		return logoutService;
	}

	@Override
	public Loader<List<User>> onCreateLoader(int id, Bundle args)
	{
		final List<User> initialItems = items;
		return new ThrowableLoader<List<User>>(getActivity(), items)
		{
			@Override
			public List<User> loadData() throws Exception
			{

				try
				{
					List<User> latest = null;

					if (getActivity() != null)
						latest = api.getUserApi().getUsers();

					if (latest != null)
						return latest;
					else
						return Collections.emptyList();
				}
				catch (OperationCanceledException e)
				{
					Activity activity = getActivity();
					if (activity != null)
						activity.finish();
					return initialItems;
				}
			}
		};
	}

	public void onListItemClick(ListView l, View v, int position, long id)
	{
		User user = ((User) l.getItemAtPosition(position));

		startActivity(new Intent(getActivity(), UserActivity.class).putExtra(USER, user));
	}

	@Override
	public void onLoadFinished(Loader<List<User>> loader, List<User> items)
	{
		super.onLoadFinished(loader, items);

	}

	@Override
	protected int getErrorMessage(Exception exception)
	{
		return R.string.error_loading_users;
	}

	@Override
	protected SingleTypeAdapter<User> createAdapter(List<User> items)
	{
		return new UserListAdapter(getActivity().getLayoutInflater(), items, avatars);
	}
}
