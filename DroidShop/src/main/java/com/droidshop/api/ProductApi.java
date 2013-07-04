package com.droidshop.api;

import com.droidshop.core.UserAgentProvider;

public class ProductApi extends BootstrapApi {

	protected ProductApi(String username, String password) {
		super(username, password);
	}

	public ProductApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

}
