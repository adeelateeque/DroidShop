package com.droidshop.api;

import static com.droidshop.core.Constants.Http.NO_CONTENT_RESPONSE;
import static com.droidshop.core.Constants.Http.URL_CATEGORY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;
<<<<<<< HEAD
import static com.droidshop.core.Constants.Http.NO_CONTENT_RESPONSE;
=======
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23

import java.io.IOException;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.util.Ln;
import com.github.kevinsawicki.http.HttpRequest;

<<<<<<< HEAD
public class ProductApi extends BootstrapApi {
	protected ProductApi(String username, String password) {
		super(username, password);
	}

	protected ProductApi(String apiKey, UserAgentProvider userAgentProvider) {
		super(apiKey, userAgentProvider);
	}

	private class ProductWrapper extends BaseWrapper<Product> {
	}

	public List<Product> getProducts() {
		return getProducts(20);
	}

	public List<Product> getProducts(int productCount) {
		try {
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS
					+ "?size=" + productCount));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null) {
				return response.getContent();
			}
		} catch (IOException e) {
		}

		return Collections.emptyList();
	}

	public List<Product> getProductsForCategory(Category category) {
		HttpRequest request;
		try {
			request = execute(HttpRequest.get(URL_CATEGORY + "/"
					+ category.getId() + "/products"));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null) {
				return response.getContent();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
=======
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
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23

	public List<Product> getProduct(Long categoryId, Long productId) {
		HttpRequest request;
		try {
			request = execute(HttpRequest.get(URL_CATEGORY + "/"
					+ categoryId + "/products/" + productId));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null) {
				return response.getContent();
			}
<<<<<<< HEAD
		} catch (IOException e) {
			e.printStackTrace();
=======
		}
		catch (IOException e)
		{
			Ln.e(e);
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23
		}
		return null;
	}

<<<<<<< HEAD
	public boolean save(Product product) {
=======
	@Override
	public boolean save(Product product)
	{
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23
		HttpRequest request;
		try {
			request = execute(HttpRequest.post(URL_PRODUCTS));
			if (request.created()) {
				return true;
			}
<<<<<<< HEAD
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Product product) {
		HttpRequest request;
		try {
			request = execute(HttpRequest.delete(URL_PRODUCTS + "/"
					+ product.getId()));
			if (request.created()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
=======
		}
		catch (IOException e)
		{
			Ln.e(e);
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23
		}
		return false;
	}

	public boolean update(Product product) {
		HttpRequest request;
		try {
			request = execute(HttpRequest.put(URL_PRODUCTS + "/"
					+ product.getId()));
			if (request.getConnection().getResponseCode() == NO_CONTENT_RESPONSE) {
				return true;
			}
<<<<<<< HEAD
		} catch (IOException e) {
			e.printStackTrace();
=======
		}
		catch (IOException e)
		{
			Ln.e(e);
>>>>>>> 2293aad4e74dbe1bdf4bd40d32a7f07f18c22f23
		}
		return false;
	}

}
