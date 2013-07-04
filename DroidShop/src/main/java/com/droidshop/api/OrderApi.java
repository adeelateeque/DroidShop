package com.droidshop.api;

import com.droidshop.core.UserAgentProvider;

public class OrderApi extends BootstrapApi {

	public OrderApi(String username, String password) {
		super(username, password);
	}

	public OrderApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

}
