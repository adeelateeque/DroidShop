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
import com.droidshop.model.News;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class NewsListFragment extends ItemListFragment<News> {

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
	public Loader<List<News>> onCreateLoader(int id, Bundle args)
	{
		final List<News> initialItems = items;
		return new ThrowableLoader<List<News>>(getActivity(), items)
		{
			@Override
			public List<News> loadData() throws Exception
			{
				if(api == null)
				{
					api = apiProvider.getApi(getActivity());
				}

				try
				{
					List<News> latest = null;

					if (getActivity() != null)
						latest = api.getNewsApi().getNews();

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
    protected SingleTypeAdapter<News> createAdapter(List<News> items) {
        return new NewsListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        News news = ((News) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), NewsActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
