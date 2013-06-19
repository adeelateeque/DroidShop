package com.droidshop;

import javax.inject.Singleton;

import android.accounts.AccountManager;
import android.content.Context;

import com.droidshop.authenticator.LogoutService;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
    complete = false, library=true
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
