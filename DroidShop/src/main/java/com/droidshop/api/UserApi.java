package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_USERS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.droidshop.core.Constants.Http;
import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.User;
import com.droidshop.util.Ln;
import com.droidshop.util.Strings;

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

	public static User authenticateUser(String username, String password) throws IOException
	{
		User model = null;
		final String query = String.format("%s=%s&%s=%s", Http.PARAM_USERNAME, username, Http.PARAM_PASSWORD, password);
		try
		{
			//Can't use our api helper methods yet
			HttpRequest request = HttpRequest.get(Http.URL_AUTH + "?" + query)
                    .header(Http.HEADER_APP_ID, Http.APP_ID)
                    .header(Http.HEADER_REST_API_KEY, Http.REST_API_KEY);


            Ln.d("Authentication response=%s", request.code());

            if(request.ok()) {
            	Ln.d(request.body());
                model = GSON.fromJson(Strings.toString(request.buffer()), User.class);
            }

            return model;
		}
		catch (HttpRequestException e)
		{
			throw e.getCause();
		}
	}
}
