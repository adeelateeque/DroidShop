package com.droidshop.api;

import static com.droidshop.core.Constants.Http.NO_CONTENT_RESPONSE;
import static com.droidshop.core.Constants.Http.URL_CATEGORY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.util.Ln;
import com.github.kevinsawicki.http.HttpRequest;

public class ProductApi extends BootstrapApi<Product>
{
	protected ProductApi(String username, String password)
	{
		super(username, password);
	}

	protected ProductApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider, URL_PRODUCTS, ProductWrapper.class);
	}

	public class ProductWrapper extends BaseWrapper<Product>{}

	public List<Product> getProductsForCategory(Category category)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.get(URL_CATEGORY + "/" + category.getId() + "/products"));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null)
			{
				return response.getContent();
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}
		return null;
	}

	@Override
	public boolean save(Product product)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.post(URL_PRODUCTS));
			if (request.created())
			{
				return true;
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}
		return false;
	}

	public boolean update(Product product)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.put(URL_PRODUCTS + "/" + product.getId()));
			if (request.getConnection().getResponseCode() == NO_CONTENT_RESPONSE)
			{
				return true;
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}
		return false;
	}

}