package com.droidshop.ui.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.droidshop.R;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class OrderActivity extends BootstrapFragmentActivity {

	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		setupNavigationTabs();
	}

	private void setupNavigationTabs()
	{
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab currentOrderTab = mActionBar.newTab().setText(R.string.page_current_order)
				.setTabListener(new TabListener<CurrentOrderFragment>(this, "current order", CurrentOrderFragment.class));
		mActionBar.addTab(currentOrderTab, true);

		ActionBar.Tab pastOrderTab = mActionBar.newTab().setText(R.string.page_past_order)
				.setTabListener(new TabListener<PastOrderFragment>(this, "past order", PastOrderFragment.class));
		mActionBar.addTab(pastOrderTab);
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
}
