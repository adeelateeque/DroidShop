package com.droidshop.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.AccountsException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.BootstrapApi;
import com.droidshop.api.ApiProvider;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.CheckIn;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class CheckInsListFragment extends ItemListFragment<CheckIn> {

    @Inject protected ApiProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    protected BootstrapApi api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.getInstance().inject(this);

        try
		{
			api = serviceProvider.getApi(getActivity());
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.checkins_list_item_labels, null));
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

    @Override
	public Loader<List<CheckIn>> onCreateLoader(int id, Bundle args)
	{
		final List<CheckIn> initialItems = items;
		return new ThrowableLoader<List<CheckIn>>(getActivity(), items)
		{
			@Override
			public List<CheckIn> loadData() throws Exception
			{

				try
				{
					List<CheckIn> latest = null;

					if (getActivity() != null)
						latest = api.getCheckInApi().getCheckIns();

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

    @Override
    protected SingleTypeAdapter<CheckIn> createAdapter(List<CheckIn> items) {
        return new CheckInsListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        CheckIn checkIn = ((CheckIn) l.getItemAtPosition(position));

        String uri = String.format("geo:%s,%s?q=%s",
                checkIn.getLocation().getLatitude(),
                checkIn.getLocation().getLongitude(),
                checkIn.getName());

        // Show a chooser that allows the user to decide how to display this data, in this case, map data.
        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)), getString(R.string.choose)));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_checkins;
    }

	public BootstrapApi getApi()
	{
		return api;
	}

	public void setApi(BootstrapApi api)
	{
		this.api = api;
	}
}
