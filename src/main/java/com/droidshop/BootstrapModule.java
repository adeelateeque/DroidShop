package com.droidshop;

import android.accounts.AccountManager;
import android.content.Context;

import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.authenticator.LogoutService;
import com.droidshop.core.CheckIn;
import com.droidshop.core.TimerService;
import com.droidshop.ui.BootstrapTimerActivity;
import com.droidshop.ui.CarouselActivity;
import com.droidshop.ui.CheckInsListFragment;
import com.droidshop.ui.ItemListFragment;
import com.droidshop.ui.NewsActivity;
import com.droidshop.ui.NewsListFragment;
import com.droidshop.ui.UserActivity;
import com.droidshop.ui.UserListFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
