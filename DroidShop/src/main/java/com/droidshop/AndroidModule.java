package com.droidshop;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import com.android.volley.RequestQueue;
import com.droidshop.util.VolleyUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Module for all Android related provisions
 */
@Module(complete = false, library = true)
public class AndroidModule
{
	@Provides
	@Singleton
	Context provideAppContext()
	{
		return BootstrapApplication.getInstance().getApplicationContext();
	}

	@Provides
	SharedPreferences provideDefaultSharedPreferences(final Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Provides
	PackageInfo providePackageInfo(Context context)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		}
		catch (PackageManager.NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Provides
	TelephonyManager provideTelephonyManager(Context context)
	{
		return getSystemService(context, Context.TELEPHONY_SERVICE);
	}

	@Provides
	InputMethodManager provideInputMethodManager(final Context context)
	{
		return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Provides
	ApplicationInfo provideApplicationInfo(final Context context)
	{
		return context.getApplicationInfo();
	}

	@Provides
	AccountManager provideAccountManager(final Context context)
	{
		return AccountManager.get(context);
	}

	@Provides
	ClassLoader provideClassLoader(final Context context)
	{
		return context.getClassLoader();
	}

	@Provides
	NotificationManager provideNotificationManager(final Context context)
	{
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Provides
	RequestQueue provideRequestQueue(final Context context)
	{
		return VolleyUtils.getRequestQueue();
	}

	@SuppressWarnings("unchecked")
	private <T> T getSystemService(Context context, String serviceConstant)
	{
		return (T) context.getSystemService(serviceConstant);
	}
}