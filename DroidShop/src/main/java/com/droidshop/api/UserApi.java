package com.droidshop.api;

import static com.droidshop.core.Constants.Http.URL_CUSTOMER;
import static com.droidshop.core.Constants.Http.CONTENT_TYPE_JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import com.droidshop.core.Constants.Http;
import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.User;
import com.droidshop.util.Ln;
import com.droidshop.util.Strings;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserApi extends BootstrapApi<User>
{
	protected UserApi(String username, String password)
	{
		super(username, password);
	}

	protected UserApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider, URL_CUSTOMER, UsersWrapper.class);
	}

	public class UsersWrapper extends BaseWrapper<User>
	{
	}

	public static User authenticateUser(String username, String password) throws IOException
	{
		UsersWrapper response;
		final String query = String.format("%s=%s&%s=%s", Http.PARAM_USERNAME, username, Http.PARAM_PASSWORD, password);
		try
		{
			// Can't use our api helper methods yet
			HttpRequest request = HttpRequest.get(Http.URL_AUTH + "?" + query).header(Http.HEADER_APP_ID, Http.APP_ID)
					.header(Http.HEADER_REST_API_KEY, Http.REST_API_KEY);

			Ln.d("Authentication response=%s", request.code());

			if (request.ok())
			{
				response = GSON.fromJson(Strings.toString(request.buffer()), UsersWrapper.class);
				if (response != null)
				{
					return response.getContent().get(0);
				}
			}
		}
		catch (HttpRequestException e)
		{
			throw e.getCause();
		}
		return null;
	}

	public static boolean registerUser(User user)
	{
		HttpRequest request;
		try
		{
			String jsonData = GSON.toJson(user);
			request = HttpRequest.post(URL_CUSTOMER).contentType(CONTENT_TYPE_JSON).send(jsonData);
			if (request.created())
			{
				return true;
			}
		}
		catch (HttpRequestException e)
		{
			Ln.e(e);
		}
		return false;
	}

	protected static class UserConverter implements JsonSerializer<User>
	{
		@Override
		public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context)
		{

			JsonObject obj = new JsonObject();
			obj.addProperty("id", src.getId());
			obj.addProperty("firstName", src.getFirstName());
			obj.addProperty("middleName", "");
			obj.addProperty("lastName", src.getLastName());
			obj.addProperty("address", src.getAddress());
			obj.addProperty("country", src.getCountry());
			obj.addProperty("email", src.getEmail());
			obj.addProperty("handphoneNo", src.getHandphoneNo());
			obj.addProperty("telephoneNo", src.getTelephoneNo());
			obj.addProperty("password", src.getPassword());
			obj.addProperty("userName", src.getUserName());
			obj.addProperty("secretAnswer", src.getSecretAnswer());
			obj.addProperty("secretQuestion", src.getSecretQuestion());
			obj.addProperty("status", User.Status.ACTIVE.toString());
			obj.addProperty("gender", src.getGender().toString());

			return obj;
		}
	}
}
