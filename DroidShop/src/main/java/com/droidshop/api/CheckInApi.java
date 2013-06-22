package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_CHECKINS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.CheckIn;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class CheckInApi extends BootstrapApi
{

	protected CheckInApi(String username, String password)
	{
		super(username, password);
	}

	protected CheckInApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	private static class CheckInWrapper
	{

		private List<CheckIn> results;

	}

	/**
	 * Get all bootstrap Checkins that exists on Parse.com
	 *
	 * @return non-null but possibly empty list of bootstrap
	 * @throws IOException
	 */
	public List<CheckIn> getCheckIns() throws IOException
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_CHECKINS));
			CheckInWrapper response = fromJson(request, CheckInWrapper.class);
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
