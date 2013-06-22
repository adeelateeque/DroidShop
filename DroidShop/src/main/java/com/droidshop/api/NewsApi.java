package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_NEWS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.News;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class NewsApi extends BootstrapApi
{

	protected NewsApi(String username, String password)
	{
		super(username, password);
	}

	protected NewsApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	protected static class NewsWrapper
	{
		private List<News> results;
	}

	 /**
     * Get all bootstrap News that exists on Parse.com
     *
     * @return non-null but possibly empty list of bootstrap
     * @throws IOException
     */
    public List<News> getNews() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_NEWS));
            NewsWrapper response = fromJson(request, NewsWrapper.class);
            if (response != null && response.results != null)
                return response.results;
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }
}
