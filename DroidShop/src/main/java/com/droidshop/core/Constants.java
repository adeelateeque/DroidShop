package com.droidshop.core;

/**
 * DroidShop constants
 */
public class Constants
{

	public static class Auth
	{
		private Auth()
		{
		}

		/**
		 * Account type id
		 */
		public static final String DROIDSHOP_ACCOUNT_TYPE = "com.droidshop";

		/**
		 * Account name
		 */
		public static final String DROIDSHOP_ACCOUNT_NAME = "droidshop";

		/**
		 * Provider id
		 */
		public static final String DROIDSHOP_PROVIDER_AUTHORITY = "com.droidshop.sync";

		/**
		 * Auth token type
		 */
		public static final String AUTHTOKEN_TYPE = DROIDSHOP_ACCOUNT_TYPE;

		/**
		 * PARAM_CONFIRMCREDENTIALS
		 */
		public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";

		/**
		 * PARAM_AUTHTOKEN_TYPE
		 */
		public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
	}

	/**
	 * All HTTP is done through a REST style API
	 */
	public static class Http
	{
		private Http()
		{
		}

		/**
		 * Base URL for all requests
		 */
		public static final String URL_BASE = "https://localhost/api";

		/**
		 * Authentication URL
		 */
		public static final String URL_AUTH = URL_BASE + "/login/customer";

		/**
		 * List Users URL
		 */
		public static final String URL_USERS = URL_BASE + "/1/users";

		public static final String URL_PRODUCTS = URL_BASE + "/product";

		/**
		 * List FeedItem URL
		 */
		public static final String URL_NEWS = URL_BASE + "/1/classes/News";

		/**
		 * List Checkin's URL
		 */
		public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";

		public static final String APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
		public static final String REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
		public static final String HEADER_REST_API_KEY = "X-Parse-REST-API-Key";
		public static final String HEADER_APP_ID = "X-Parse-Application-Id";
		public static final String CONTENT_TYPE_JSON = "application/json";
		public static final String PARAM_USERNAME = "username";
		public static final String PARAM_PASSWORD = "password";
		public static final String SESSION_TOKEN = "sessionToken";
	}

	public static class Extra
	{
		private Extra()
		{
		}

		public static final String NEWS_ITEM = "news_item";

		public static final String USER = "user";

	}

	public static class Intent
	{
		private Intent()
		{
		}

		/**
		 * Action prefix for all intents created
		 */
		public static final String INTENT_PREFIX = "com.droidshop.";
	}

	public static class Notification
	{
		private Notification()
		{
		}

		public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
	}

}
