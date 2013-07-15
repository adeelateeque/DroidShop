package com.droidshop;

import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.ui.HomeActivity;
import com.droidshop.ui.MainFragment;
import com.droidshop.ui.category.CategoryListFragment;
import com.droidshop.ui.order.OrderActivity;
import com.droidshop.ui.order.OrderListFragment;
import com.droidshop.ui.product.HomeProductsFragment;
import com.droidshop.ui.product.ProductListActivity;
import com.droidshop.ui.product.ProductListAdapter;
import com.droidshop.ui.product.ProductListFragment;
import com.droidshop.ui.product.ProductManagerFragment;
import com.droidshop.ui.reservation.ReservationActivity;
import com.droidshop.ui.reservation.ReservationListAdapter;
import com.droidshop.ui.reservation.ReservationListFragment;
import com.droidshop.ui.user.RegisterActivity;
import com.droidshop.ui.user.UserProfileActivity;

import dagger.Module;

/**
 * Add all the other modules to this one.
 */
@Module
(
    includes = {
            AndroidModule.class,
            BootstrapModule.class
    },
    injects = {
    		RegisterActivity.class,
            BootstrapApplication.class,
            BootstrapAuthenticatorActivity.class,
            HomeActivity.class,
            MainFragment.class,
            CategoryListFragment.class,
            OrderListFragment.class,
            ProductManagerFragment.class,
            UserProfileActivity.class,
            OrderActivity.class,
            ReservationActivity.class,
            ReservationListFragment.class,
            ReservationListAdapter.class,
            ProductListActivity.class,
            ProductListAdapter.class,
            ProductListFragment.class,
            HomeProductsFragment.class,
    }
)
public class RootModule {
}
