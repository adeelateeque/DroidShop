package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_USERS;

import java.io.IOException;
import java.util.ArrayList;
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

	private class UsersWrapper extends BaseWrapper<User>
	{
		private ArrayList<User> content;
		private User user;

		@Override
		public ArrayList<User> getAll()
		{
			return content;
		}

		@Override
		public User getOne()
		{
			return user;
		}
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
			if(response != null)
			{
				return response.getEntities();
			}
		}
		catch (HttpRequestException e)
		{
			throw e.getCause();
		}
		return Collections.emptyList();
	}

	public static User authenticateUser(String username, String password) throws IOException
	{
		/*UsersWrapper response;
		final String query = String.format("%s=%s&%s=%s", Http.PARAM_USERNAME, username, Http.PARAM_PASSWORD, password);
		try
		{
			// Can't use our api helper methods yet
			HttpRequest request = HttpRequest.get(Http.URL_AUTH + "?" + query).header(Http.HEADER_APP_ID, Http.APP_ID)
					.header(Http.HEADER_REST_API_KEY, Http.REST_API_KEY);

			Ln.d("Authentication response=%s", request.code());

			if (request.ok())
			{
				response = GSON.fromJson(Strings.toString(request.buffer()), UsersWrapper.class);
				if(response != null)
				{
					return response.getEntities().get(0);
				}
			}
			return null;
		}
		catch (HttpRequestException e)
		{
			throw e.getCause();
		}*/
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);

		return user;
	}
}
