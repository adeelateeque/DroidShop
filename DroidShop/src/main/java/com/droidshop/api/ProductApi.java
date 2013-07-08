package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Product;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.analytics.tracking.android.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ProductApi extends BootstrapApi
{

	protected ProductApi(String username, String password)
	{
		super(username, password);
	}

	public ProductApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	public List<Product> getProducts()
	{
		try{
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS));
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(request.body());
			Log.d(element.toString());
		}
		catch(IOException e)
		{

		}

		return Collections.emptyList();
	}
}
