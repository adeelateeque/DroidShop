

package com.droidshop.ui.core;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.droidshop.R;
import com.droidshop.ui.CheckInsListFragment;
import com.droidshop.ui.FeedFragment;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
        	FeedFragment feedFragment = new FeedFragment();
            feedFragment.setArguments(bundle);
            return feedFragment;
        case 1:
        	FeedFragment feedFragment2 = new FeedFragment();
        	feedFragment2.setArguments(bundle);
        	return feedFragment2;
        case 2:
        	CheckInsListFragment checkInsFragment2 = new CheckInsListFragment();
        	checkInsFragment2.setArguments(bundle);
            return checkInsFragment2;
        case 3:
            CheckInsListFragment checkInsFragment = new CheckInsListFragment();
            checkInsFragment.setArguments(bundle);
            return checkInsFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
        	return resources.getString(R.string.page_feed);
        case 1:
        	return resources.getString(R.string.page_movies);
        case 2:
            return resources.getString(R.string.page_theaters);
        case 3:
            return resources.getString(R.string.page_checkins);
        default:
            return null;
        }
    }
}
