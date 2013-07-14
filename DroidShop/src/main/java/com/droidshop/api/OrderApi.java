package com.droidshop.api;

import com.droidshop.core.UserAgentProvider;

public class OrderApi extends BootstrapApi {

	protected OrderApi(String username, String password) {
		super(username, password);
	}

	protected OrderApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

}
