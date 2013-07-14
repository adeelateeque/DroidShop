package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_CATEGORY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Product;
import com.github.kevinsawicki.http.HttpRequest;

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
		try {
			HttpRequest request = execute(HttpRequest.get(URL_PRODUCTS
					+ "?size=0"));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null) {
				return response.getContent();
			}
		} catch (IOException e) {
		}

		return Collections.emptyList();
	}

	public List<Product> getProductsList(Integer i) {
		try {
			HttpRequest request = execute(HttpRequest.get(URL_CATEGORY + "/"
					+ i + "products"));
			ProductWrapper response = fromJson(request, ProductWrapper.class);
			if (response != null) {
				return response.getContent();
			}
		} catch (IOException e) {
		}

		return Collections.emptyList();
	}
}
