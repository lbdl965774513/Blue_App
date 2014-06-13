package com.blue.conninternet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.blue.bean.Note;
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

/**
 * @author SLJM
 * @create 2014-4-26
 * @desc ºÃ≥–JsonRequest  º”‘ÿarray ˝æ›
 */
public class ListDataRequest extends JsonRequest<ArrayList<Note>> {

	public ListDataRequest(int method, String url,Map<String,String> contentBody, Listener<ArrayList<Note>> listener, ErrorListener errorlistener) {
		super(method, url, contentBody, listener, errorlistener);
	}

//	public ListDataRequest(String url, Map requestBody, Listener<ArrayList> listener,ErrorListener errorListener){
//		super(url, requestBody, listener, errorListener);
//	}

	@Override
	protected Response<ArrayList<Note>> parseNetworkResponse(NetworkResponse response) {
		try {
			return Response.success(parseStrToNoteArray(new String(response.data,
							HttpHeaderParser.parseCharset(response.headers))),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}
	
	private ArrayList<Note> parseStrToNoteArray(String resultStr) {
//		Log.i("resultStr", resultStr);
		Gson gson = new Gson();
		JsonParser jsonParse = new JsonParser();
		JsonObject rootObj = jsonParse.parse(resultStr).getAsJsonObject();
		Log.i("dataList", rootObj+"");
		ArrayList<Note> array = null;
		try {
			// parseResult(rootObj.getAsJsonObject("root"));
			String json = gson.toJson(rootObj.getAsJsonArray("msg"));
//			Log.i("json", json);
			array = gson.fromJson(gson.toJson(rootObj.getAsJsonArray("msg")),new TypeToken<ArrayList<Note>>() {}.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return array;
	}

//	private void parseResult(JsonObject resultElement) {
//		Gson gson = new Gson();
//
//		if (resultElement != null && !resultElement.isJsonNull() && resultElement.isJsonObject()) {
//			JsonElement forumElement = resultElement.get("forum");
//			if (forumElement != null && !forumElement.isJsonNull() && forumElement.isJsonArray()) {
//				gson.fromJson(gson.toJson(forumElement), new TypeToken<ArrayList>() {
//				}.getType());
//			}
//		}
//	}

	
}
