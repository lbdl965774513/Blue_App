/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blue.lib.volley.toolbox;

import java.util.Map;

import org.apache.http.protocol.HTTP;

import com.blue.lib.volley.AuthFailureError;
import com.blue.lib.volley.NetworkResponse;
import com.blue.lib.volley.Request;
import com.blue.lib.volley.Response;
import com.blue.lib.volley.Response.ErrorListener;
import com.blue.lib.volley.Response.Listener;


/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 * 
 * @param <T>
 *            JSON type of response expected
 */
public abstract class JsonRequest<T> extends Request<T> {
	/** Charset for request. */
	private static final String PROTOCOL_CHARSET = HTTP.UTF_8;

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

	private final Listener<T> mListener;
	private final String mRequestBody;

	private Map<String, String> bodyMap;

	// public JsonRequest(int method, String url, String requestBody,
	// Listener<T> listener, ErrorListener errorListener) {
	// super(method, url, errorListener);
	// mListener = listener;
	// mRequestBody = requestBody;
	// }

	public JsonRequest(int method, String url, Map<String, String> body, Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mRequestBody = null;
		bodyMap = body;
	}

	@Override
	protected void deliverResponse(T response) {
		if (mListener != null)
			mListener.onResponse(response);
	}

	@Override
	abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

//	@Override
//	public String getBodyContentType() {
//		return PROTOCOL_CONTENT_TYPE;
//	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return bodyMap;
	}

}
