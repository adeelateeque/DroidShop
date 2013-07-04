package com.droidshop.ui;

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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.droidshop.R;
import com.droidshop.R.id;
import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.ui.core.BootstrapFragmentActivity;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Activity to view the tabs with fragments.
 */
public class CarouselActivity extends BootstrapFragmentActivity
{
	ActionBar mActionBar;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mSideMenu;

    private MergeAdapter mAdapter;
    private ArrayAdapter aAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (status != ConnectionResult.SUCCESS)
		{
			GooglePlayServicesUtil.getErrorDialog(status, this, 1234).show();
		}
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carousel_view);
		mActionBar = getSupportActionBar();
		setupNavigationTabs();

		mTitle = mDrawerTitle = getTitle();
        mSideMenu = getResources().getStringArray(R.array.navigationArray);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mAdapter = new MergeAdapter();
        aAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mSideMenu);
    	mAdapter.addAdapter(new ProfileImageAdapter(this));
    	mAdapter.addAdapter(aAdapter);
    	mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //if (savedInstanceState == null) {
        //    selectItem(0);
        //}
	}

	private void setupNavigationTabs()
	{
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab feedTab = mActionBar.newTab().setText(R.string.page_feed)
				.setTabListener(new TabListener<FeedFragment>(this, "feed", FeedFragment.class));
		mActionBar.addTab(feedTab, true);

		ActionBar.Tab moviesTab = mActionBar.newTab().setText(R.string.page_movies)
				.setTabListener(new TabListener<FeedFragment>(this, "movies", FeedFragment.class));
		mActionBar.addTab(moviesTab);

		ActionBar.Tab theatersTab = mActionBar.newTab().setText(R.string.page_theaters)
				.setTabListener(new TabListener<FeedFragment>(this, "theaters", FeedFragment.class));
		mActionBar.addTab(theatersTab);

		ActionBar.Tab trailersTab = mActionBar.newTab().setText(R.string.page_trailers)
				.setTabListener(new TabListener<FeedFragment>(this, "trailers", FeedFragment.class));
		mActionBar.addTab(trailersTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSupportMenuInflater().inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String text)
			{
				Toaster.showLong(CarouselActivity.this, text);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String text)
			{
				Toaster.showLong(CarouselActivity.this, text);
				return true;
			}
		});
		searchView.setQueryHint(getResources().getString(R.string.search_action));
		// Configure the search info and add any event listeners
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case id.timer:
			final Intent i = new Intent(this, BootstrapTimerActivity.class);
			startActivity(i);
			return true;
		case android.R.id.home:
	        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	            mDrawerLayout.closeDrawer(mDrawerList);
	        } else {
	            mDrawerLayout.openDrawer(mDrawerList);
	        }
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

	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
    	Intent intent;
    	switch (position){
    	case 0:
    		intent = new Intent(CarouselActivity.this, BootstrapAuthenticatorActivity.class);
        	startActivity(intent);
        	break;
    	}
    	mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
