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
import android.widget.Toast;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.authenticator.LogoutService;
<<<<<<< HEAD
import com.droidshop.model.Category;
=======
import com.droidshop.model.AbstractEntity;
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23
import com.droidshop.model.Product;
import com.droidshop.ui.core.ItemListFragment;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class ProductDescriptionFragment extends ItemListFragment<Product> {

	@Inject
	protected ApiProvider apiProvider;
	@Inject
	protected LogoutService logoutService;

	protected BootstrapApi<AbstractEntity> api;

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

	public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
		final List<Product> initialItems = items;
		return new ThrowableLoader<List<Product>>(getSherlockActivity(), items) {
			@Override
			public List<Product> loadData() throws Exception {
				try {
					if (api == null) {
						api = apiProvider.getApi(getSherlockActivity());
					}
					List<Product> latest = null;

<<<<<<< HEAD
					if (getSherlockActivity() != null) {
						ProductDescriptionActivity activity = (ProductDescriptionActivity) getSherlockActivity();
						Long productId = activity.productId;
						Long categoryId = activity.categoryId;
						latest = api.getProductApi().getProduct(categoryId,
								productId);
					}
=======
					if (getSherlockActivity() != null)
						latest = api.getProductApi().getAll(20);
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23

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
	protected SingleTypeAdapter<Product> createAdapter(List<Product> items) {
		return new ProductListAdapter(
				getSherlockActivity().getLayoutInflater(), items);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		// Intent intent = new Intent(getActivity(), ProductListFragment.class);
		// startActivity(intent);
	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_product;
	}
}