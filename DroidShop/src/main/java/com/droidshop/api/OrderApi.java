package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_ORDER;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Order;
import com.github.kevinsawicki.http.HttpRequest;

public class OrderApi extends BootstrapApi
{

	protected OrderApi(String username, String password)
	{
		super(username, password);
	}

	protected OrderApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	public class OrderWrapper extends BaseWrapper<Order>
	{
	}

	public List<Order> getOrders()
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_ORDER +"?size=0"));
			OrderWrapper response = fromJson(request, OrderWrapper.class);
			if (response != null)
			{
				return response.getContent();
			}
		}
		catch (IOException e)
		{
		}

		return Collections.emptyList();
	}

}
