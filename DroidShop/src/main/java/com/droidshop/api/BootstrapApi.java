
package com.droidshop.api;

import static com.droidshop.core.Constants.Http.HEADER_PARSE_APP_ID;
import static com.droidshop.core.Constants.Http.HEADER_PARSE_REST_API_KEY;
import static com.droidshop.core.Constants.Http.PARSE_APP_ID;
import static com.droidshop.core.Constants.Http.PARSE_REST_API_KEY;

import java.io.IOException;
import java.io.Reader;

import android.accounts.OperationCanceledException;

import com.droidshop.core.Constants;
import com.droidshop.core.UserAgentProvider;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Bootstrap API service
 */
public class BootstrapApi {

	private final String apiKey;
    private String username;
    private String password;

    private UserAgentProvider userAgentProvider;

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     */
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * You can also configure GSON with different naming policies for your API. Maybe your api is Rails
     * api and all json values are lower case with an underscore, like this "first_name" instead of "firstName".
     * You can configure GSON as such below.
     *
     * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
     *
     */


    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;

    protected static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link JsonParseException}
         *
         * @param cause
         */
        public JsonException(JsonParseException cause) {
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
    protected BootstrapApi(final String username, final String password) {
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
    protected BootstrapApi(final String apiKey, final UserAgentProvider userAgentProvider) {
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
    protected HttpRequest execute(HttpRequest request) throws IOException {
        if (!configure(request).ok())
            throw new IOException("Unexpected response code: " + request.code());
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        request.userAgent(userAgentProvider.get());

        if(isPostOrPut(request))
            request.contentType(Constants.Http.CONTENT_TYPE_JSON); // All PUT & POST requests to Parse.com api must be in JSON - https://www.parse.com/docs/rest#general-requests
        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
               || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(HttpRequest request) {

        // Required params for
        request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY );
        request.header(HEADER_PARSE_APP_ID, PARSE_APP_ID);

        /**
         * NOTE: This may be where you want to add a header for the api token that was saved when you
         * logged in. In the bootstrap sample this is where we are saving the session id as the token.
         * If you actually had received a token you'd take the "apiKey" (aka: token) and add it to the
         * header or form values before you make your requests.
          */

        /**
         * Add the user name and password to the request here if your service needs username or password for each
         * request. You can do this like this:
         * request.basic("myusername", "mypassword");
         */

        return request;
    }

    protected <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
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

	//All available APIs
	//TODO exceptions thrown are not actual
	public UserApi getUserApi() throws OperationCanceledException
	{
		UserApi userApi = new UserApi(apiKey, userAgentProvider);
		userApi.setUsername(this.username);
		userApi.setPassword(password);
		return userApi;
	}

	public NewsApi getNewsApi() throws OperationCanceledException
	{
		NewsApi newsApi = new NewsApi(apiKey, userAgentProvider);
		newsApi.setUsername(this.username);
		newsApi.setPassword(password);
		return newsApi;
	}

	public CheckInApi getCheckInApi() throws OperationCanceledException
	{
		CheckInApi checkInApi = new CheckInApi(apiKey, userAgentProvider);
		checkInApi.setUsername(this.username);
		checkInApi.setPassword(password);
		return checkInApi;
	}
}
