package com.droidshop.api;

import static com.droidshop.core.Constants.Http.APP_ID;
import static com.droidshop.core.Constants.Http.HEADER_APP_ID;
import static com.droidshop.core.Constants.Http.HEADER_REST_API_KEY;
import static com.droidshop.core.Constants.Http.REST_API_KEY;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.accounts.OperationCanceledException;

import com.droidshop.core.Constants;
import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.AbstractEntity;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.analytics.tracking.android.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Bootstrap API service
 */
public class BootstrapApi
{
	private final String apiKey;
	private String username;
	private String password;

	private UserAgentProvider userAgentProvider;

	/**
	 * GSON instance to use for all request with date format set up for proper parsing.
	 */
	public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

	/**
	 * You can also configure GSON with different naming policies for your API. Maybe your api is
	 * Rails
	 * api and all json values are lower case with an underscore, like this "first_name" instead of
	 * "firstName".
	 * You can configure GSON as such below.
	 * public static final Gson GSON = new
	 * GsonBuilder().setDateFormat("yyyy-MM-dd").setFieldNamingPolicy
	 * (LOWER_CASE_WITH_UNDERSCORES).create();
	 */

	/**
	 * Read and connect timeout in milliseconds
	 */
	private static final int TIMEOUT = 30 * 1000;

	protected static class JsonException extends IOException
	{

		private static final long serialVersionUID = 3774706606129390273L;

		/**
		 * Create exception from {@link JsonParseException}
		 *
		 * @param cause
		 */
		public JsonException(JsonParseException cause)
		{
			super(cause.getMessage());
			initCause(cause);
		}
	}

	/**
	 * Create bootstrap service
	 *
	 * @param username
	 * @param password
	 */
	protected BootstrapApi(final String username, final String password)
	{
		this.username = username;
		this.password = password;
		this.apiKey = null;
	}

	/**
	 * Create bootstrap service
	 *
	 * @param userAgentProvider
	 * @param apiKey
	 */
	protected BootstrapApi(final String apiKey, final UserAgentProvider userAgentProvider)
	{
		this.userAgentProvider = userAgentProvider;
		this.username = null;
		this.password = null;
		this.apiKey = apiKey;
	}

	/**
	 * Execute request
	 *
	 * @param request
	 * @return request
	 * @throws IOException
	 */
	protected HttpRequest execute(HttpRequest request) throws IOException
	{
		if (!configure(request).ok())
			throw new IOException("Unexpected response code: " + request.code());
		return request;
	}

	private HttpRequest configure(final HttpRequest request)
	{
		request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT).userAgent(userAgentProvider.get());

		if (isPostOrPut(request))
			request.contentType(Constants.Http.CONTENT_TYPE_JSON);
		return addCredentialsTo(request);
	}

	private boolean isPostOrPut(HttpRequest request)
	{
		return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
				|| request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

	}

	private HttpRequest addCredentialsTo(HttpRequest request)
	{
		// Required params for
		request.header(HEADER_REST_API_KEY, REST_API_KEY);
		request.header(HEADER_APP_ID, APP_ID);

		/**
		 * NOTE: This may be where you want to add a header for the api token that was saved when
		 * you
		 * logged in. In the bootstrap sample this is where we are saving the session id as the
		 * token.
		 * If you actually had received a token you'd take the "apiKey" (aka: token) and add it to
		 * the
		 * header or form values before you make your requests.
		 */

		/**
		 * Add the user name and password to the request here if your service needs username or
		 * password for each
		 * request. You can do this like this:
		 * request.basic("myusername", "mypassword");
		 */

		Log.d(request.toString());
		return request;
	}

	protected <V> V fromJson(HttpRequest request, Class<V> target) throws IOException
	{
		Reader reader = request.bufferedReader();
		try
		{
			return GSON.fromJson(reader, target);
		}
		catch (JsonParseException e)
		{
			throw new JsonException(e);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException ignored)
			{
				// Ignored
			}
		}
	}

	protected String getApiKey()
	{
		return apiKey;
	}

	protected String getUsername()
	{
		return username;
	}

	protected String getPassword()
	{
		return password;
	}

	protected void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public abstract class BaseWrapper<T extends AbstractEntity>
	{
		private ArrayList<Link> links;
		private Page page;
		private ArrayList<T> content;
		private T entity;

		protected List<T> getContent()
		{
			if (entity != null)
			{
				List<T> asList = new ArrayList<T>();
				asList.add(entity);
				return asList;
			}
			else if (content != null)
			{
				return content;
			}
			return Collections.emptyList();
		}

		public ArrayList<Link> getLinks()
		{
			return links;
		}

		public Page getPage()
		{
			return page;
		}
	}

	public class Link
	{
		private String rel;
		private String href;

		public String getRel()
		{
			return rel;
		}

		public void setRel(String rel)
		{
			this.rel = rel;
		}

		public String getHref()
		{
			return href;
		}

		public void setHref(String href)
		{
			this.href = href;
		}
	}

	public class Page
	{
		private int size;
		private int totalElements;
		private int totalPages;
		private int number;

		public int getSize()
		{
			return size;
		}

		public void setSize(int size)
		{
			this.size = size;
		}

		public int getTotalElements()
		{
			return totalElements;
		}

		public void setTotalElements(int totalElements)
		{
			this.totalElements = totalElements;
		}

		public int getTotalPages()
		{
			return totalPages;
		}

		public void setTotalPages(int totalPages)
		{
			this.totalPages = totalPages;
		}

		public int getNumber()
		{
			return number;
		}

		public void setNumber(int number)
		{
			this.number = number;
		}
	}

	// All available APIs
	public UserApi getUserApi() throws OperationCanceledException
	{
		UserApi userApi = new UserApi(apiKey, userAgentProvider);
		userApi.setUsername(this.username);
		userApi.setPassword(password);
		return userApi;
	}

	public ProductApi getProductApi() throws OperationCanceledException
	{
		ProductApi productApi = new ProductApi(apiKey, userAgentProvider);
		productApi.setUsername(this.username);
		productApi.setPassword(password);
		return productApi;
	}

	public OrderApi getOrderApi() throws OperationCanceledException
	{
		OrderApi orderApi = new OrderApi(apiKey, userAgentProvider);
		orderApi.setUsername(this.username);
		orderApi.setPassword(password);
		return orderApi;
	}

	public CustomerApi getCustomerApi() throws OperationCanceledException
	{
		CustomerApi customerApi = new CustomerApi(apiKey, userAgentProvider);
		customerApi.setUsername(this.username);
		customerApi.setPassword(password);
		return customerApi;
	}

	public ReservationApi getReservationApi() throws OperationCanceledException
	{
		ReservationApi reservationApi = new ReservationApi(apiKey, userAgentProvider);
		reservationApi.setUsername(this.username);
		reservationApi.setPassword(password);
		return reservationApi;
	}

	public CategoryApi getCategoryApi() throws OperationCanceledException
	{
		CategoryApi categoryApi = new CategoryApi(apiKey, userAgentProvider);
		categoryApi.setUsername(this.username);
		categoryApi.setPassword(password);
		return categoryApi;
	}
}
