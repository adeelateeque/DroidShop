package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_ORDER;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Order;

public class OrderApi extends BootstrapApi<Order>
{
	protected OrderApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider, URL_ORDER, OrderWrapper.class);
	}

	public class OrderWrapper extends BaseWrapper<Order>
	{
	}
}
