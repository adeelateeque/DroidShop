package com.droidshop.ui.product;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class ProductListFragment extends ItemListFragment<Product> {

	public static String KEY_PRODUCT_ID;
	public static String KEY_CATEGORY_ID;

	@Inject
	protected ApiProvider apiProvider;
	@Inject
	protected LogoutService logoutService;

	protected BootstrapApi<AbstractEntity> api;

	private Button buyNow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BootstrapApplication.getInstance().inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.product_list_item, null);
		buyNow = (Button) view.findViewById(R.id.buyNowBtn);
		buyNow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final int position = getListView().getPositionForView(v);
		        if (position != ListView.INVALID_POSITION) {
		            Intent intent = new Intent(getActivity(), receipt.class);
		            startActivity(intent);
		        }
			}

		});
		return view;
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
		Toast.makeText(getSherlockActivity(), Integer.toString(items.size()),
				Toast.LENGTH_LONG).show();
		return new ThrowableLoader<List<Product>>(getSherlockActivity(), items) {
			@Override
			public List<Product> loadData() throws Exception {
				try {
					if (api == null) {
						api = apiProvider.getApi(getSherlockActivity());
					}
					List<Product> latest = null;

					if (getSherlockActivity() != null){
						ProductListActivity activity = (ProductListActivity) getSherlockActivity();
						Long categoryId = activity.categoryId;
						latest = api.getProductApi().getProductsForCategory(
								new Category(categoryId));
					}

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

	}

	@Override
	protected int getErrorMessage(Exception exception) {
		return R.string.error_loading_reservation;
	}
}