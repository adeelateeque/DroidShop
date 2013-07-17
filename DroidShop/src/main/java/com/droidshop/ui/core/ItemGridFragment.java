package com.droidshop.ui.core;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.droidshop.R;
import com.droidshop.R.id;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.model.*;
import com.droidshop.ui.product.ProductDetailsActivity;
import com.droidshop.util.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;
import com.github.kevinsawicki.wishlist.ViewUtils;

/**
 * Base fragment for displaying a grid of items that loads with a progress bar
 * visible
 *
 * @param <E>
 */
public abstract class ItemGridFragment<E> extends SherlockFragment implements
		LoaderCallbacks<List<E>> {
	private static final String FORCE_REFRESH = "forceRefresh";

	public static String KEY_PRODUCT_ID;

	/**
	 * @param args
	 *            bundle passed to the loader by the LoaderManager
	 * @return true if the bundle indicates a requested forced refresh of the
	 *         items
	 */
	protected static boolean isForceRefresh(Bundle args) {
		return args != null && args.getBoolean(FORCE_REFRESH, false);
	}

	/**
	 * Grid items provided to {@link #onLoadFinished(Loader, Grid)}
	 */
	protected List<E> items = Collections.emptyList();

	/**
	 * Grid view
	 */
	protected GridView gridView;

	/**
	 * Empty view
	 */
	protected TextView emptyView;

	/**
	 * Progress bar
	 */
	protected ProgressBar progressBar;

	/**
	 * Is the grid currently shown?
	 */
	protected boolean gridShown;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (!items.isEmpty())
			setGridShown(true, false);

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.item_grid, null);
	}

	/**
	 * Detach from grid view.
	 */
	@Override
	public void onDestroyView() {
		gridShown = false;
		emptyView = null;
		progressBar = null;
		gridView = null;

		super.onDestroyView();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		gridView = (GridView) view.findViewById(R.id.grid);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onGridItemClick((GridView) parent, view, position, id, items);
			}
		});
		progressBar = (ProgressBar) view.findViewById(id.pb_loading);

		emptyView = (TextView) view.findViewById(android.R.id.empty);

		configureGrid(getActivity(), getGridView());
	}

	/**
	 * Configure grid after view has been created
	 *
	 * @param activity
	 * @param gridView
	 */
	protected void configureGrid(Activity activity, GridView gridView) {
		gridView.setAdapter(createAdapter());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu optionsMenu, MenuInflater inflater) {
		inflater.inflate(R.menu.bootstrap, optionsMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (!isUsable())
			return false;
		switch (item.getItemId()) {
		case id.refresh:
			forceRefresh();
			return true;
		case R.id.logout:
			logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected abstract LogoutService getLogoutService();

	private void logout() {
		getLogoutService().logout(new Runnable() {
			@Override
			public void run() {
				// Calling a refresh will force the service to look for a logged
				// in user
				// and when it finds none the user will be requested to log in
				// again.
				forceRefresh();
			}
		});
	}

	/**
	 * Force a refresh of the items displayed ignoring any cached items
	 */
	protected void forceRefresh() {
		Bundle bundle = new Bundle();
		bundle.putBoolean(FORCE_REFRESH, true);
		refresh(bundle);
	}

	/**
	 * Refresh the fragment's grid
	 */
	public void refresh() {
		refresh(null);
	}

	private void refresh(final Bundle args) {
		if (!isUsable())
			return;

		getSherlockActivity()
				.setSupportProgressBarIndeterminateVisibility(true);

		getLoaderManager().restartLoader(0, args, this);
	}

	/**
	 * Get error message to display for exception
	 *
	 * @param exception
	 * @return string resource id
	 */
	protected abstract int getErrorMessage(Exception exception);

	public void onLoadFinished(Loader<List<E>> loader, List<E> items) {

		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(
				false);

		Exception exception = getException(loader);
		if (exception != null) {
			showError(getErrorMessage(exception));
			showGrid();
			return;
		}

		this.items = items;
		getGridAdapter().setItems(items.toArray());
		showGrid();

	}

	/**
	 * Create adapter to display items
	 *
	 * @return adapter
	 */
	protected SingleTypeAdapter<E> createAdapter() {
		return createAdapter(items);
	}

	/**
	 * Create adapter to display items
	 *
	 * @param items
	 * @return adapter
	 */
	protected abstract SingleTypeAdapter<E> createAdapter(final List<E> items);

	/**
	 * Set the grid to be shown
	 */
	protected void showGrid() {
		setGridShown(true, isResumed());
	}

	@Override
	public void onLoaderReset(Loader<List<E>> loader) {
		// Intentionally left blank
	}

	/**
	 * Show exception in a Toast
	 *
	 * @param message
	 */
	protected void showError(final int message) {
		Toaster.showLong(getSherlockActivity(), message);
	}

	/**
	 * Get exception from loader if it provides one by being a
	 * {@link ThrowableLoader}
	 *
	 * @param loader
	 * @return exception or null if none provided
	 */
	protected Exception getException(final Loader<List<E>> loader) {
		if (loader instanceof ThrowableLoader)
			return ((ThrowableLoader<List<E>>) loader).clearException();
		else
			return null;
	}

	/**
	 * Refresh the grid with the progress bar showing
	 */
	protected void refreshWithProgress() {
		items.clear();
		setGridShown(false);
		refresh();
	}

	/**
	 * Get {@link GridView}
	 *
	 * @return gridView
	 */
	public GridView getGridView() {
		return gridView;
	}

	/**
	 * Get grid adapter
	 *
	 * @return grid adapter
	 */
	@SuppressWarnings("unchecked")
	protected SingleTypeAdapter<E> getGridAdapter() {
		if (gridView != null)
			return (SingleTypeAdapter<E>) gridView.getAdapter();
		else
			return null;
	}

	/**
	 * Set grid adapter to use on grid view
	 *
	 * @param adapter
	 * @return this fragment
	 */
	protected ItemGridFragment<E> setGridAdapter(final ListAdapter adapter) {
		if (gridView != null)
			gridView.setAdapter(adapter);
		return this;
	}

	private ItemGridFragment<E> fadeIn(final View view, final boolean animate) {
		if (view != null)
			if (animate)
				view.startAnimation(AnimationUtils.loadAnimation(getActivity(),
						android.R.anim.fade_in));
			else
				view.clearAnimation();
		return this;
	}

	private ItemGridFragment<E> show(final View view) {
		ViewUtils.setGone(view, false);
		return this;
	}

	private ItemGridFragment<E> hide(final View view) {
		ViewUtils.setGone(view, true);
		return this;
	}

	/**
	 * Set grid shown or progress bar show
	 *
	 * @param shown
	 * @return this fragment
	 */
	public ItemGridFragment<E> setGridShown(final boolean shown) {
		return setGridShown(shown, true);
	}

	/**
	 * Set grid shown or progress bar show
	 *
	 * @param shown
	 * @param animate
	 * @return this fragment
	 */
	public ItemGridFragment<E> setGridShown(final boolean shown,
			final boolean animate) {
		if (!isUsable())
			return this;

		if (shown == gridShown) {
			if (shown)
				// Grid has already been shown so hide/show the empty view with
				// no fade effect
				if (items.isEmpty())
					hide(gridView).show(emptyView);
				else
					hide(emptyView).show(gridView);
			return this;
		}

		gridShown = shown;

		if (shown)
			if (!items.isEmpty())
				hide(progressBar).hide(emptyView).fadeIn(gridView, animate)
						.show(gridView);
			else
				hide(progressBar).hide(gridView).fadeIn(emptyView, animate)
						.show(emptyView);
		else
			hide(gridView).hide(emptyView).fadeIn(progressBar, animate)
					.show(progressBar);

		return this;
	}

	/**
	 * Set empty text on grid fragment
	 *
	 * @param message
	 * @return this fragment
	 */
	protected ItemGridFragment<E> setEmptyText(final String message) {
		if (emptyView != null)
			emptyView.setText(message);
		return this;
	}

	/**
	 * Set empty text on grid fragment
	 *
	 * @param resId
	 * @return this fragment
	 */
	protected ItemGridFragment<E> setEmptyText(final int resId) {
		if (emptyView != null)
			emptyView.setText(resId);
		return this;
	}

	/**
	 * Callback when a grid view item is clicked
	 *
	 * @param l
	 * @param v
	 * @param position
	 * @param id
	 * @param items
	 */
	public void onGridItemClick(GridView l, View v, int position, long id,
			List<E> items) {
		Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
		KEY_PRODUCT_ID = Integer.toString(position);

		Product p = (Product) items.get(position);
		CharSequence name = p.getName();
		CharSequence price = p.getPrice().toString();
		CharSequence img = p.images.toString();

		Bundle b = new Bundle();
		b.putCharSequence("name", name);
		b.putCharSequence("price", price);
		b.putCharSequence("img", img);
		intent.putExtras(b);
		startActivity(intent);
	}

	/**
	 * Is this fragment still part of an activity and usable from the UI-thread?
	 *
	 * @return true if usable on the UI-thread, false otherwise
	 */
	protected boolean isUsable() {
		return getActivity() != null;
	}
}
