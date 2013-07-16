package com.droidshop;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.droidshop.core.Constants;
import com.github.kevinsawicki.http.HttpRequest;

import dagger.ObjectGraph;

/**
 * DroidShop application
 */
public class BootstrapApplication extends Application
{
	private static BootstrapApplication instance;
	public static final String ADMIN_EMAIL = "admin@droidshop.com";
	ObjectGraph objectGraph;

	/**
	 * Create main application
	 */
	public BootstrapApplication()
	{
		// Disable http.keepAlive on Froyo and below
		if (SDK_INT <= FROYO)
			HttpRequest.keepAlive(false);
	}

	/**
	 * Create main application
	 *
	 * @param context
	 */
	public BootstrapApplication(final Context context)
	{
		this();
		attachBaseContext(context);
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		instance = this;
		// Perform Injection
		objectGraph = ObjectGraph.create(getRootModule());
		objectGraph.inject(this);
		objectGraph.injectStatics();
	}

	public boolean isAdmin()
	{
		AccountManager accountManager = AccountManager.get(this);
		Account[] accounts = accountManager.getAccountsByType(Constants.Auth.DROIDSHOP_ACCOUNT_TYPE);
		if (accounts.length != 0)
		{
			return accounts[0].name.equals(ADMIN_EMAIL);
		}
		return false;
	}

	public boolean isUser()
	{
		return !isAdmin();
	}

	private Object getRootModule()
	{
		return new RootModule();
	}

	/**
	 * Create main application
	 *
	 * @param instrumentation
	 */
	public BootstrapApplication(final Instrumentation instrumentation)
	{
		this();
		attachBaseContext(instrumentation.getTargetContext());
	}

	public void inject(Object object)
	{
		objectGraph.inject(object);
	}

	public static BootstrapApplication getInstance()
	{
		return instance;
	}
}
