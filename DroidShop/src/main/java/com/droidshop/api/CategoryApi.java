package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_CATEGORY;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.github.kevinsawicki.http.HttpRequest;

public class CategoryApi extends BootstrapApi<Category> {

	protected CategoryApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider, URL_CATEGORY, CategoryWrapper.class);
	}

	private class CategoryWrapper extends BaseWrapper<Category> {
	}

	public List<Category> getCategories() {
		try {
			HttpRequest request = execute(HttpRequest.get(URL_CATEGORY
					+ "?size=0"), true);
			CategoryWrapper response = fromJson(request, CategoryWrapper.class);
			if (response != null) {
				return response.getContent();
			}
		} catch (IOException e) {
		}

		return Collections.emptyList();
	}
}
