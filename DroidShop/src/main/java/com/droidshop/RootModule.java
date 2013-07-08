package com.droidshop;

import com.droidshop.authenticator.BootstrapAuthenticatorActivity;
import com.droidshop.core.TimerService;
import com.droidshop.ui.BootstrapTimerActivity;
import com.droidshop.ui.CarouselActivity;
import com.droidshop.ui.CategoryFragment;
import com.droidshop.ui.CreateProductFragment;
import com.droidshop.ui.CurrentOrderFragment;
import com.droidshop.ui.MainFragment;
import com.droidshop.ui.MyOrderActivity;
import com.droidshop.ui.PastOrderFragment;
import com.droidshop.ui.RegisterActivity;
import com.droidshop.ui.ReservationActivity;
import com.droidshop.ui.UpdateProductFragment;
import com.droidshop.ui.UserProfileActivity;

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
            TimerService.class,
            MainFragment.class,
            CategoryFragment.class,
            CreateProductFragment.class,
            UpdateProductFragment.class,
            UserProfileActivity.class,
            MyOrderActivity.class,
            CurrentOrderFragment.class,
            PastOrderFragment.class,
            ReservationActivity.class
    }
)
public class RootModule {
}
