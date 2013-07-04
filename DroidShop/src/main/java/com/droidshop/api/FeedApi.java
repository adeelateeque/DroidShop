package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_NEWS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.FeedItem;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class FeedApi extends BootstrapApi
{

	protected FeedApi(String username, String password)
	{
		super(username, password);
	}

	protected FeedApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	protected static class FeedItemWrapper
	{
		private List<FeedItem> results;
	}

	 /**
     * Get all bootstrap FeedItem that exists on Parse.com
     *
     * @return non-null but possibly empty list of bootstrap
     * @throws IOException
     */
    public List<FeedItem> getFeedItems() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_NEWS));
            FeedItemWrapper response = fromJson(request, FeedItemWrapper.class);
            if (response != null && response.results != null)
                return response.results;
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }
}
