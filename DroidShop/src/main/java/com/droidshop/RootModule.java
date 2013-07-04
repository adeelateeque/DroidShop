package com.droidshop;

import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.core.TimerService;
import com.droidshop.ui.BootstrapTimerActivity;
import com.droidshop.ui.CarouselActivity;
import com.droidshop.ui.CheckInsListFragment;
import com.droidshop.ui.FeedActivity;
import com.droidshop.ui.FeedFragment;
import com.droidshop.ui.RegisterActivity;
import com.droidshop.ui.UserActivity;
import com.droidshop.ui.UserListFragment;

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
            CarouselActivity.class,
            BootstrapTimerActivity.class,
            CheckInsListFragment.class,
            FeedActivity.class,
            FeedFragment.class,
            UserActivity.class,
            UserListFragment.class,
            TimerService.class,
    }
)
public class RootModule {
}
