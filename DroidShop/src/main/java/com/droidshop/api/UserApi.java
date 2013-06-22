package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_USERS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.User;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class UserApi extends BootstrapApi
{
	protected UserApi(String username, String password)
	{
		super(username, password);
	}

	protected UserApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	private static class UsersWrapper
	{
		private List<User> results;
	}

	/**
	 * Get all bootstrap Users that exist on Parse.com
	 *
	 * @return non-null but possibly empty list of bootstrap
	 * @throws IOException
	 */
	public List<User> getUsers() throws IOException
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_USERS));
			UsersWrapper response = fromJson(request, UsersWrapper.class);
			if (response != null && response.results != null)
				return response.results;
			return Collections.emptyList();
		}
		catch (HttpRequestException e)
		{
			throw e.getCause();
		}
	}
}
