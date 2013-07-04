package com.droidshop;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;

import java.util.Map;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.droidshop.core.Constants.Http;
import com.droidshop.util.VolleyUtils;
import com.github.kevinsawicki.http.HttpRequest;

import dagger.ObjectGraph;

/**
 * droidshop application
 */
public class BootstrapApplication extends Application
{
	private static final String SET_COOKIE_KEY = "Set-Cookie";
	private static final String COOKIE_KEY = "Cookie";

	private static BootstrapApplication instance;
	private SharedPreferences mPreferences;
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

		VolleyUtils.init(this);

		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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

	/**
	 * Checks the response headers for session cookie and saves it
	 * if it finds it.
	 * Used by Volley
	 *
	 * @param headers (Response Headers)
	 */
	public final void checkAndSaveSessionCookie(Map<String, String> headers)
	{
		if (headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).startsWith(Http.SESSION_TOKEN))
		{
			String cookie = headers.get(SET_COOKIE_KEY);
			if (cookie.length() > 0)
			{
				String[] splitCookie = cookie.split(";");
				String[] splitSessionId = splitCookie[0].split("=");
				cookie = splitSessionId[1];
				Editor prefEditor = mPreferences.edit();
				prefEditor.putString(Http.SESSION_TOKEN, cookie);
				prefEditor.commit();
			}
		}
	}

	/**
	 * Adds session cookie to headers if exists.
	 * Used by Volley
	 * @param headers
	 */
	public final void addSessionCookie(Map<String, String> headers)
	{
		String sessionToken = mPreferences.getString(Http.SESSION_TOKEN, "");
		if (sessionToken.length() > 0)
		{
			StringBuilder builder = new StringBuilder();
			builder.append(Http.SESSION_TOKEN);
			builder.append("=");
			builder.append(sessionToken);
			if (headers.containsKey(COOKIE_KEY))
			{
				builder.append("; ");
				builder.append(headers.get(COOKIE_KEY));
			}
			headers.put(COOKIE_KEY, builder.toString());
		}
	}

}
