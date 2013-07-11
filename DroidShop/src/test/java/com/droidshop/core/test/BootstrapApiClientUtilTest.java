package com.droidshop.core.test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.droidshop.api.BootstrapApi;
import com.droidshop.model.Product;
import com.droidshop.model.User;

/**
 * Unit tests of client API
 */
public class BootstrapApiClientUtilTest
{
	BootstrapApi api;

	@Before
	public void setup()
	{
		//api = new BootstrapApi("demo@androidbootstrap.com", "foobar");
	}

	@Test
	@Ignore("Requires the API to use basic authentication.")
	public void shouldCreateClient() throws Exception
	{
		List<User> users = api.getUserApi().getUsers();

		assertThat(users.get(0).getUserName(), notNullValue());
	}

	@Test
	@Ignore
	public void getProductsTest() throws Exception
	{
		List<Product> products = api.getProductApi().getProducts();

		assertThat(products, notNullValue());
	}
}
