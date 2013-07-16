package com.droidshop.ui;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.R.id;
import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.ui.category.CategoryListFragment;
import com.droidshop.ui.core.BootstrapFragmentActivity;
import com.droidshop.ui.order.OrderActivity;
import com.droidshop.ui.product.HomeProductsFragment;
import com.droidshop.ui.reservation.ReservationActivity;
import com.droidshop.ui.user.UserProfileActivity;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class HomeActivity extends BootstrapFragmentActivity
{
	@Inject
	LogoutService logoutService;
	private ActionBar mActionBar;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private MergeAdapter mAdapter;
	private ArrayAdapter<String> aAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		ensureGoogplePlayService();
		setContentView(R.layout.activity_home);
		mActionBar = getSupportActionBar();
		setupNavigationTabs();
		setupDrawer();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setupDrawer()
	{
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		)
		{
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// set up the drawer's list view with items and click listener
		mAdapter = new MergeAdapter();

		if (BootstrapApplication.isUser == true)
		{
			// enable ActionBar app icon to behave as action to toggle nav drawer
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			aAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, getResources().getStringArray(
					R.array.user_drawer_items));
			mAdapter.addAdapter(aAdapter);
			mDrawerList.setAdapter(mAdapter);
		}
		else if (BootstrapApplication.isAdmin == true)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setHomeButtonEnabled(false);
			mDrawerLayout.setEnabled(false);
		}
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private void ensureGoogplePlayService()
	{
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS)
		{
			GooglePlayServicesUtil.getErrorDialog(status, this, 1234).show();
		}
	}

	private void setupNavigationTabs()
	{
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab mainTab = mActionBar.newTab().setText(R.string.page_main)
				.setTabListener(new TabListener<HomeProductsFragment>(this, "product", HomeProductsFragment.class));
		mActionBar.addTab(mainTab, true);

		ActionBar.Tab categoryTab = mActionBar.newTab().setText(R.string.page_category)
				.setTabListener(new TabListener<CategoryListFragment>(this, "category", CategoryListFragment.class));
		mActionBar.addTab(categoryTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (BootstrapApplication.isAdmin)
		{
			getSupportMenuInflater().inflate(R.menu.admin, menu);
		}
		else
		{
			getSupportMenuInflater().inflate(R.menu.main, menu);
			SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
			searchView.setOnQueryTextListener(new OnQueryTextListener()
			{
				@Override
				public boolean onQueryTextSubmit(String text)
				{
					Toaster.showLong(HomeActivity.this, text);
					return true;
				}

				@Override
				public boolean onQueryTextChange(String text)
				{
					Toaster.showLong(HomeActivity.this, text);
					return true;
				}
			});
			searchView.setQueryHint(getResources().getString(R.string.search_action));
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case id.cart_action:
				Toast.makeText(getApplicationContext(), "Cart", Toast.LENGTH_LONG).show();
				// final Intent i = new Intent(this, BootstrapTimerActivity.class);
				// startActivity(i);
				return true;
			case android.R.id.home:
				if (mDrawerLayout.isDrawerOpen(mDrawerList))
				{
					mDrawerLayout.closeDrawer(mDrawerList);
				}
				else
				{
					mDrawerLayout.openDrawer(mDrawerList);
				}
				return true;
			case id.logout:
				logoutService.logout(new Runnable()
				{
					@Override
					public void run()
					{
						Intent intent = new Intent(getApplicationContext(), BootstrapAuthenticatorActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				});
				return true;
			case id.add_product:
				Toast.makeText(getApplicationContext(), "Add product", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public static class TabListener<T extends Fragment> implements ActionBar.TabListener
	{
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 *
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz)
		{
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		/* The following are each of the ActionBar.TabListener callbacks */

		public void onTabSelected(Tab tab, FragmentTransaction ft)
		{
			// Check if the fragment is already initialized
			if (mFragment == null)
			{
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(R.id.content_frame, mFragment, mTag);
			}
			else
			{
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft)
		{
			if (mFragment != null)
			{
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft)
		{
			// User selected the already selected tab. Usually do nothing.
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Intent intent;
			switch (position)
			{
				case 0:
					intent = new Intent(HomeActivity.this, UserProfileActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(HomeActivity.this, OrderActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(HomeActivity.this, ReservationActivity.class);
					startActivity(intent);
					break;
				case 3:
					logoutService.logout(new Runnable()
					{
						@Override
						public void run()
						{
							Intent intent = new Intent(getApplicationContext(), BootstrapAuthenticatorActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish();
						}
					});
					break;
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
