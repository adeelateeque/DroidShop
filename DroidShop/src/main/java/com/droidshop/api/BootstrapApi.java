package com.droidshop.api;

import static com.droidshop.core.Constants.Http.APP_ID;
import static com.droidshop.core.Constants.Http.HEADER_APP_ID;
import static com.droidshop.core.Constants.Http.HEADER_REST_API_KEY;
import static com.droidshop.core.Constants.Http.NO_CONTENT_RESPONSE;
import static com.droidshop.core.Constants.Http.REST_API_KEY;
import static com.droidshop.core.Constants.Http.URL_PRODUCTS;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;

import com.droidshop.api.ProductApi.ProductConverter;
import com.droidshop.api.UserApi.UserConverter;
import com.droidshop.core.Constants;
import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.AbstractEntity;
import com.droidshop.model.Product;
import com.droidshop.model.User;
import com.droidshop.util.Ln;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.analytics.tracking.android.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Bootstrap API service
 */
public class BootstrapApi<T extends AbstractEntity> implements CrudApi<T>
{
	private final String apiKey;
	private String username;
	private String password;

	private UserAgentProvider userAgentProvider;
	private String endpoint;
	private Class<? extends BaseWrapper<T>> wrapperClass;

	/**
	 * GSON instance to use for all request with date format set up for proper parsing.
	 */
	public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
			.registerTypeAdapter(Currency.class, new CurrencyConverter())
			.registerTypeAdapter(Product.class, new ProductConverter()).registerTypeAdapter(User.class, new UserConverter())
			.registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();

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
	 * Create DroidShop API
	 *
	 * @param apiKey
	 * @param userAgentProvider
	 * @param endpoint
	 */
	protected BootstrapApi(final String apiKey, final UserAgentProvider userAgentProvider, String endpoint,
			Class<? extends BaseWrapper<T>> wrapperClass)
	{
		this.userAgentProvider = userAgentProvider;
		this.apiKey = apiKey;
		this.endpoint = endpoint;
		this.wrapperClass = wrapperClass;
	}

	public BootstrapApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		this.userAgentProvider = userAgentProvider;
		this.apiKey = apiKey;
	}

	/**
	 * Execute request
	 *
	 * @param request
	 * @param isJson
	 * @return request
	 * @throws IOException
	 */
	protected HttpRequest execute(HttpRequest request, Boolean isJson) throws IOException
	{
		if (!configure(request, isJson).ok())
			throw new IOException("Unexpected response code: " + request.code());
		return request;
	}

	private HttpRequest configure(final HttpRequest request, Boolean isJson)
	{
		request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT).userAgent(userAgentProvider.get());

		if (isPostOrPut(request) && isJson)
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
		request.header(HEADER_REST_API_KEY, REST_API_KEY);
		request.header(HEADER_APP_ID, APP_ID);

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

	public abstract class BaseWrapper<P extends AbstractEntity>
	{
		private ArrayList<Link> links;
		private Page page;
		private ArrayList<P> content;
		private P entity;

		protected List<P> getContent()
		{
			if (entity != null)
			{
				List<P> asList = new ArrayList<P>();
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

	public UserApi getUserApi()
	{
		UserApi userApi = new UserApi(apiKey, userAgentProvider);
		userApi.setUsername(this.username);
		userApi.setPassword(password);
		return userApi;
	}

	public ProductApi getProductApi()
	{
		ProductApi productApi = new ProductApi(apiKey, userAgentProvider);
		productApi.setUsername(this.username);
		productApi.setPassword(password);
		return productApi;
	}

	public OrderApi getOrderApi()
	{
		OrderApi orderApi = new OrderApi(apiKey, userAgentProvider);
		orderApi.setUsername(this.username);
		orderApi.setPassword(password);
		return orderApi;
	}

	public ReservationApi getReservationApi()
	{
		ReservationApi reservationApi = new ReservationApi(apiKey, userAgentProvider);
		reservationApi.setUsername(this.username);
		reservationApi.setPassword(password);
		return reservationApi;
	}

	public CategoryApi getCategoryApi()
	{
		CategoryApi categoryApi = new CategoryApi(apiKey, userAgentProvider);
		categoryApi.setUsername(this.username);
		categoryApi.setPassword(password);
		return categoryApi;
	}

	@Override
	public long save(T entity)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.post(endpoint).send(GSON.toJson(entity)), true);
			if (request.created())
			{
				return parseIdFromUrl(request.header(HttpRequest.HEADER_LOCATION), endpoint);
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}
		catch (NumberFormatException e)
		{
		}
		return 0;
	}

	public boolean update(T entity)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.put(URL_PRODUCTS + "/" + entity.getId()).send(GSON.toJson(entity)), true);
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

	@Override
	public boolean delete(T entity)
	{
		Long id = verifyAndGetId(entity);
		if (id != 0)
		{
			HttpRequest request;
			try
			{
				request = execute(HttpRequest.delete(endpoint + "/" + id), true);
				if (request.ok())
				{
					return true;
				}
			}
			catch (IOException e)
			{
				Ln.e(e);
			}
		}
		return false;
	}

	@Override
	public T findById(Long id)
	{
		HttpRequest request;
		try
		{
			request = execute(HttpRequest.get(endpoint + "/" + id), true);
			BaseWrapper<T> response = fromJson(request, wrapperClass);
			if (response != null && !response.getContent().isEmpty())
			{
				return response.getContent().get(0);
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}
		return null;
	}

	@Override
	public List<T> getAll()
	{
		return getAll(0);
	}

	@Override
	public List<T> getAll(int count)
	{
		try
		{
			HttpRequest request = execute(HttpRequest.get(endpoint + "?size=" + count), true);
			BaseWrapper<T> response = fromJson(request, wrapperClass);
			if (response != null)
			{
				return response.getContent();
			}
		}
		catch (IOException e)
		{
			Ln.e(e);
		}

		return Collections.emptyList();
	}

	protected long verifyAndGetId(T entity)
	{
		long id = 0;
		AbstractEntity item = (AbstractEntity) entity;
		if (item.getId() == 0 || item.getId() == null)
		{
			String selfRel = item.findLink("rel").getHref();
			try
			{
				id = parseIdFromUrl(selfRel, endpoint);
			}
			catch (NumberFormatException e)
			{
				return 0;
			}
		}
		return id;
	}

	protected long parseIdFromUrl(String url, String rootPath)
	{
		return Long.parseLong(url.substring(url.lastIndexOf(rootPath + "/"), +1));
	}
}

class DateTimeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime>
{
	@Override
	public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context)
	{
		return new JsonPrimitive(src.toString());
	}

	@Override
	public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		// return new DateTime(json.getAsString());
		return new DateTime();
	}
}

class CurrencyConverter implements JsonSerializer<Currency>, JsonDeserializer<Currency>
{
	@Override
	public JsonElement serialize(Currency src, Type srcType, JsonSerializationContext context)
	{
		return new JsonPrimitive(src.toString());
	}

	@Override
	public Currency deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		return Currency.getInstance(json.getAsString());
	}
}