package com.droidshop.api;

import com.droidshop.core.UserAgentProvider;

public class CustomerApi extends BootstrapApi{

	public CustomerApi(String username, String password) {
		super(username, password);
	}

	public CustomerApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

}
