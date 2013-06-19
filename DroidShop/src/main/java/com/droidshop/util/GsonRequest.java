package com.droidshop.util;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.droidshop.BootstrapApplication;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T>
{
	private final Gson mGson;
	private final Class<T> mClass;
	private final Listener<T> mListener;

	private final Map<String, String> mParams;

	/**
	 * @param method
	 * @param url
	 * @param params
	 *            A {@link HashMap} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> params, Listener<T> listener,
			ErrorListener errorListener)
	{
		super(method, url, errorListener);
		this.mClass = clazz;
		this.mListener = listener;
		this.mParams = params;
		this.mGson = new Gson();
	}

	/**
	 * @param method
	 * @param url
	 * @param params
	 *            A {@link HashMap} to post with the request. Null is allowed
	 *            and indicates no parameters will be posted along with request.
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> params, Listener<T> listener,
			ErrorListener errorListener, Gson gson)
	{
		super(method, url, errorListener);
		this.mClass = clazz;
		this.mListener = listener;
		this.mParams = params;
		this.mGson = gson;
	}

	@Override
	protected Map<String, String> getParams()
	{
		return mParams;
	}

	/*
	 * (non-Javadoc)
	 * @see com.android.volley.Request#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap()))
		{
			headers = new HashMap<String, String>();
		}

		BootstrapApplication.getInstance().addSessionCookie(headers);

		return headers;
	}

	@Override
	protected void deliverResponse(T response)
	{
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response)
	{
		// Since we don't know which of the two underlying network vehicles
		// will Volley use, we have to handle and store session cookies manually
		BootstrapApplication.getInstance().checkSessionCookie(response.headers);

		try
		{
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(mGson.fromJson(json, mClass), HttpHeaderParser.parseCacheHeaders(response));
		}
		catch (UnsupportedEncodingException e)
		{
			return Response.error(new ParseError(e));
		}
		catch (JsonSyntaxException e)
		{
			return Response.error(new ParseError(e));
		}
	}
}