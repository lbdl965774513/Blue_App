package com.blue.conninternet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.blue.util.HttpUtils;

/**
 * @author SLJM
 * @create 2014-5-9
 * @desc 上传用户头像
 * 
 */
public class IconHttp {

	private Context context;
	private Map<String, String> icon_map;
	private Map<String, String> info_map;
	private static final String TAG = "PostHttp";
	private static HttpClient httpClient = new DefaultHttpClient();
	private Map<String,String> imgUrl_map;
	public IconHttp(Context context) {
		this.context = context;
	}

	public void uploadText(Map<String, String> content_map) throws ClientProtocolException,IOException, JSONException {

		info_map = content_map;
		HttpPost httpPost = new HttpPost(HttpUtils.initArticleURL("article_add"));
		MultipartEntity entity = new MultipartEntity();

		// 添加文本参数
		if (info_map != null && !info_map.isEmpty()) {
			for (Map.Entry<String, String> itemText : info_map.entrySet()) {
				entity.addPart(itemText.getKey(),new StringBody(itemText.getValue(), Charset.forName("UTF_8")));
				Log.i(TAG, itemText.getKey() + "---"+ itemText.getValue().toString());
			}
		}
		Log.i(TAG, "photo map = " + imgUrl_map);
		//添加上传图片后的返回的地址
		if (imgUrl_map != null && !imgUrl_map.isEmpty()) {
			for (Map.Entry<String, String> itemText : imgUrl_map.entrySet()) {
				entity.addPart(itemText.getKey(),new StringBody(itemText.getValue(), Charset.forName("UTF_8")));
//				entity.addPart("image",new StringBody("["+imgUrl_array.get(0)+"]", Charset.forName("UTF_8")));
				Log.i(TAG, itemText.getKey() + "---"+ itemText.getValue().toString());
			}
			
		}
	
		httpPost.setEntity(entity);

		// 使用execute方法发送HTTP GET请求，并返回HttpResponse对象
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// 使用getEntity方法获得返回结果
		String strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
		Log.i(TAG, "text response" + strResult);
	}

	public void uploadIcon(Map<String, String> map) throws ClientProtocolException, IOException, JSONException {

		this.icon_map = map;
		HttpPost httpPost = new HttpPost(HttpUtils.initArticleURL("site_info"));
		MultipartEntity entity = new MultipartEntity();

		// 添加用户头像参数
		if (icon_map != null && !icon_map.isEmpty()) {
			for (Map.Entry<String, String> itemPic : icon_map.entrySet()) {
				if (!itemPic.getValue().isEmpty()) {
					File file = new File(itemPic.getValue());
					ContentBody fileBody = new FileBody(file);
					Log.i(TAG, "upload pic is exists and picpath is-->"+ itemPic.getKey() + itemPic.getValue().toString());
					entity.addPart(itemPic.getKey(), fileBody);
				} else
					Log.e(TAG, "upload Pic is NOT exists!");
			}
		}
		httpPost.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		// 使用getEntity方法获得返回结果
		String strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
		Log.i(TAG, "icon response" + strResult);
		
		JSONObject root = new JSONObject(strResult);
		
		if (root.optString("code").equals("1")) {
			imgUrl_map = new HashMap<String,String>();
			imgUrl_map.put("attach", root.optString("msg"));
		}
	}

}
