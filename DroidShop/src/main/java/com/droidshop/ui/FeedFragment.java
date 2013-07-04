package com.droidshop.ui;

import static com.droidshop.core.Constants.Extra.NEWS_ITEM;

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

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.FeedItem;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class FeedFragment extends ItemListFragment<FeedItem> {

    @Inject protected ApiProvider apiProvider;
    @Inject protected LogoutService logoutService;

    protected BootstrapApi api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.getInstance().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_news);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.news_list_item_labels, null));
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
	public Loader<List<FeedItem>> onCreateLoader(int id, Bundle args)
	{
		final List<FeedItem> initialItems = items;
		return new ThrowableLoader<List<FeedItem>>(getActivity(), items)
		{
			@Override
			public List<FeedItem> loadData() throws Exception
			{
				if(api == null)
				{
					api = apiProvider.getApi(getActivity());
				}

				try
				{
					List<FeedItem> latest = null;

					if (getActivity() != null)
						latest = api.getNewsApi().getFeedItems();

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
    protected SingleTypeAdapter<FeedItem> createAdapter(List<FeedItem> items) {
        return new FeedListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        FeedItem news = ((FeedItem) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), FeedActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_feed;
    }
}
