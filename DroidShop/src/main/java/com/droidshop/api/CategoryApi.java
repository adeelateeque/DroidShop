package com.droidshop.api;

import com.droidshop.core.UserAgentProvider;

public class CategoryApi extends BootstrapApi {

	public CategoryApi(String username, String password) {
		super(username, password);
	}

	public CategoryApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

}
