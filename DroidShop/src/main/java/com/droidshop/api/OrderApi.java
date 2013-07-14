package com.droidshop.api;

import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Order;

public class OrderApi extends BootstrapApi {

	protected OrderApi(String username, String password) {
		super(username, password);
	}

	protected OrderApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

	public List<Order> getOrders()
	{
		return Collections.emptyList();
	}

}
