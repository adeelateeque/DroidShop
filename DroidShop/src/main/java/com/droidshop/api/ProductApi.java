package com.droidshop.api;

import static com.droidshop.core.Constants.Http.CONTENT_TYPE_TEXT_URI_LSIT;
import static com.droidshop.core.Constants.Http.URL_CATEGORY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.util.Ln;
import com.droidshop.util.Strings;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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

	public class ProductWrapper extends BaseWrapper<Product>
	{
	}

	public List<Product> getProductsForCategory(Category category)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.get(URL_CATEGORY + "/" + category.getId() + "/products"), true);
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
	public long save(Product product)
	{
		long id = save(product);

		if (!product.getCategories().isEmpty())
		{
			ArrayList<String> categoryLinks = new ArrayList<String>();
			for (Category category : product.getCategories())
			{
				categoryLinks.add(category.getSelfHref());
			}
			try
			{
				execute(HttpRequest.post(URL_PRODUCTS + "/" + id + "/categories").contentType(CONTENT_TYPE_TEXT_URI_LSIT)
						.send(Strings.join(" ", categoryLinks)), false);
			}
			catch (IOException e)
			{
			}
		}
		return id;
	}

	@Override
	public boolean update(Product product)
	{
		return super.update(product);
	}

	protected static class ProductConverter implements JsonSerializer<Product>
	{
		@Override
		public JsonElement serialize(Product src, Type typeOfSrc, JsonSerializationContext context)
		{

			JsonObject obj = new JsonObject();
			obj.addProperty("id", src.getId());
			obj.addProperty("name", src.getName());
			obj.addProperty("description", src.getDescription());
			obj.add("price", BootstrapApi.GSON.toJsonTree(src.getPrice()));
			obj.addProperty("quantity", src.getQuantity());
			obj.addProperty("status", src.getStatus().toString());

			return obj;
		}
	}
}