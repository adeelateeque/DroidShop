package com.droidshop.ui.product;

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
import com.droidshop.model.Product;
import com.droidshop.ui.core.ItemGridFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class NewProductsFragment extends ItemGridFragment<Product>
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
	protected LogoutService getLogoutService()
	{
		return logoutService;
	}

	@Override
	public void onDestroyView()
	{
		setGridAdapter(null);

		super.onDestroyView();
	}

	@Override
	public Loader<List<Product>> onCreateLoader(int id, Bundle args)
	{
		final List<Product> initialItems = items;
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

					if (getSherlockActivity() != null)
						latest = api.getProductApi().getProducts();

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
		return new NewProductsAdapter(getSherlockActivity().getLayoutInflater(), items);
	}

	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// Product product = ((Product) l.getItemAtPosition(position));

		// startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)),
		// getString(R.string.choose)));
	}

	@Override
	protected int getErrorMessage(Exception exception)
	{
		return R.string.error_loading_product;
	}
}
