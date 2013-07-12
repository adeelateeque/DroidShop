package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.github.kevinsawicki.http.HttpRequest;

public class CategoryApi extends BootstrapApi
{

	public CategoryApi(String username, String password)
	{
		super(username, password);
	}

	public CategoryApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	public class CategoryWrapper extends BaseWrapper<Category>
	{
		private ArrayList<Category> content;
		private Category category;

		@Override
		protected Category getOne()
		{
			return category;
		}

		@Override
		protected ArrayList<Category> getAll()
		{
			return content;
		}
	}

	public List<Category> getCategories()
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS));
			CategoryWrapper response = fromJson(request, CategoryWrapper.class);
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
