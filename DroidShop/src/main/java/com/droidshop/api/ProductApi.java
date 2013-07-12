package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Product;
import com.github.kevinsawicki.http.HttpRequest;

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

	private class ProductWrapper extends BaseWrapper<Product>
	{
		private ArrayList<Product> content;
		private Product product;

		@Override
		protected Product getOne()
		{
			return product;
		}

		@Override
		protected ArrayList<Product> getAll()
		{
			return content;
		}
	}

	public List<Product> getProducts()
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null)
			{
				return response.getEntities();
			}
		}
		catch (IOException e)
		{
		}

		return Collections.emptyList();
	}
}
