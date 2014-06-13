package com.blue.ui.user.center;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.lib.view.FlowLayout;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.ui.layout.GalleryItemLayout;
import com.blue.util.HttpUtils;
import com.blue.util.SubmitPara;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class GalleryActivity extends Activity {

	private LinearLayout gallery_ll;
	private TextView empty_tv;

	private String user_id;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private static final String TAG = "GalleryActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		getIntentData();
		initView();
		getPhotoList();
	}

	private void getIntentData() {

		Intent intent = getIntent();
		user_id = intent.getStringExtra("user_id");
	}

	private void initView() {

		requestQueue = BlueApplication.requestQueue;
		imageLoader = ImageLoader.getInstance();

		empty_tv = (TextView) findViewById(R.id.empty_gallery_tv);
		gallery_ll = (LinearLayout) findViewById(R.id.gallery_layout);
	}

	private void getPhotoList() {

		if (TextUtils.isEmpty(user_id)) {
			return;
		}
		Log.i(TAG, "user_id = " + user_id);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, HttpUtils.initArticleURL("gallery"),
				SubmitPara.getUserInfo(user_id), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "Gallery = " + response);
						if (response != null && response.optString("code").equals("1")) {
							try {
								initGallery(response.getJSONArray("msg"));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}, null);

		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	private void initGallery(JSONArray jsonArray) {
		
		if (jsonArray.length() == 0) {
			empty_tv.setVisibility(View.VISIBLE);
			return;
		}

		JSONArray photo_list = jsonArray;
		gallery_ll.removeAllViews();
		for (int i = 0; i < photo_list.length(); i++) {
			JSONObject photo_item = photo_list.optJSONObject(i);
			JSONArray photo_array = photo_item.optJSONArray("attach");

			GalleryItemLayout layout = new GalleryItemLayout(this);
			FlowLayout flowLayout = layout.getFlowLayout();
			TextView ctime_tv = layout.getTextView();
			
			ctime_tv.setText(editFontSize(photo_item.optString("date_publish")));

			for (int j = 0; j < photo_array.length(); j++) {

				ImageView imageView = new ImageView(this);

				imageLoader.displayImage(photo_array.optString(j), imageView);
				flowLayout.addView(imageView);

			}
			gallery_ll.addView(layout);
		}
	}
	
	private SpannableString editFontSize(String ctime) {
		SpannableString spannableString = new SpannableString(ctime);
		spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}
	
	

	@Override
	protected void onStop() {
		super.onStop();
		requestQueue.stop();
	}

}
