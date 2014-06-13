package com.blue.conninternet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import android.util.Log;

import com.blue.bean.User;
import com.blue.lib.volley.NetworkResponse;
import com.blue.lib.volley.ParseError;
import com.blue.lib.volley.Response;
import com.blue.lib.volley.Response.ErrorListener;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.HttpHeaderParser;
import com.blue.lib.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class UserRequest extends JsonRequest<User> {

	public UserRequest(int method, String url, Map<String, String> body,Listener<User> listener, ErrorListener errorListener) {
		super(method, url, body, listener, errorListener);
	}

	@Override
	protected Response<User> parseNetworkResponse(NetworkResponse response) {
		try {
			return Response.success(parseStrToNoteArray(new String(response.data,
							HttpHeaderParser.parseCharset(response.headers))),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}
	
	private User parseStrToNoteArray(String resultStr) {
		Gson gson = new Gson();
		JsonParser jsonParse = new JsonParser();
		JsonObject rootObj = jsonParse.parse(resultStr).getAsJsonObject();
		Log.i(" user = ", rootObj+"");
		User User = null;
		try {
			// parseResult(rootObj.getAsJsonObject("root"));
			String json = gson.toJson(rootObj.getAsJsonObject("msg"));
			User = gson.fromJson(gson.toJson(rootObj.getAsJsonObject("msg")),new TypeToken<User>() {}.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return User;
	}

}
