package com.droidshop.ui.core;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.droidshop.R;
import com.droidshop.ui.MainFragment;
import com.droidshop.ui.category.CategoryListFragment;

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
        	MainFragment mainFragment = new MainFragment();
        	mainFragment.setArguments(bundle);
            return mainFragment;
        case 1:
        	CategoryListFragment categoryListFragment = new CategoryListFragment();
        	categoryListFragment.setArguments(bundle);
        	return categoryListFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
        	return resources.getString(R.string.page_main);
        case 1:
        	return resources.getString(R.string.page_category);
        default:
            return null;
        }
    }
}
