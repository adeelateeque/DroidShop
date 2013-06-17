package com.droidshop.ui.core;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.droidshop.BootstrapApplication;

import butterknife.Views;

/**
 * Base class for all Bootstrap Activities that need fragments.
 */
public class BootstrapFragmentActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BootstrapApplication.getInstance().inject(this);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);

        Views.inject(this);
    }

}
