package com.droidshop.api;

import java.io.IOException;

import javax.inject.Inject;

import android.accounts.AccountsException;
import android.app.Activity;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.AbstractEntity;

/**
 * Provider for a {@link com.droidshop.api.BootstrapApi} instance
 */
public class ApiProvider
{
	@Inject
	ApiKeyProvider keyProvider;
	@Inject
	UserAgentProvider userAgentProvider;

	/**
	 * Get user service for configured key provider
	 * <p>
	 * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
	 *
	 * @return bootstrap service
	 * @throws IOException
	 * @throws AccountsException
	 */
	public BootstrapApi<AbstractEntity> getApi(Activity activity) throws IOException, AccountsException
	{
		return new BootstrapApi<AbstractEntity>(keyProvider.getAuthKey(activity), userAgentProvider);
	}
}
