package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_CATEGORY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;
import static com.droidshop.core.Constants.Http.NO_CONTENT_RESPONSE;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.github.kevinsawicki.http.HttpRequest;

public class ProductApi extends BootstrapApi
{
	protected ProductApi(String username, String password)
	{
		super(username, password);
	}

	protected ProductApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	private class ProductWrapper extends BaseWrapper<Product>
	{
	}

	public List<Product> getProducts()
	{
		return getProducts(20);
	}

	public List<Product> getProducts(int productCount)
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS + "?size=" + productCount));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
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

	public Product getProductById(int id)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.get(URL_PRODUCTS + "/" + id));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null)
			{
				return response.getContent().get(0);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

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
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Product product)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.post(URL_PRODUCTS));
			if(request.created())
			{
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Product product)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.delete(URL_PRODUCTS + "/" + product.getId()));
			if(request.created())
			{
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(Product product)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.put(URL_PRODUCTS + "/" + product.getId()));
			if(request.getConnection().getResponseCode() == NO_CONTENT_RESPONSE)
			{
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
