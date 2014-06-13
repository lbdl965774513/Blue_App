package com.blue.ui.user.center;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.bean.User;
import com.blue.conninternet.UserRequest;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.util.HttpUtils;
import com.blue.util.SubmitPara;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author SLJM
 * @create 2014-04-03
 * @desc 用户资料
 * 
 */
public class UserDataActivity extends Activity {

	private ImageView icon_iv;
	private TextView name_tv;
	private TextView age_tv;
	private TextView constellation_tv;
	private ImageView gender_iv;
	private ListView user_data_lv;
	
	private String user_id;
	private SimpleAdapter simpleAdapter;
	private RequestQueue requestQueue;
	private static final String TAG = "UserDataActivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_data);
		
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		Intent intent = getIntent();
		user_id = intent.getStringExtra("user_id");
	}
	/** 初始化 */
	private void initView() {
		
		requestQueue = BlueApplication.requestQueue;
		
		user_data_lv = (ListView) findViewById(R.id.data_data_lv);
		icon_iv = (ImageView) findViewById(R.id.head_data_iv);
		gender_iv = (ImageView) findViewById(R.id.gender_data_iv);
		name_tv = (TextView)findViewById(R.id.name_data_tv);
		age_tv = (TextView)findViewById(R.id.age_data_tv);
		constellation_tv = (TextView)findViewById(R.id.constellation_data_tv);
		
		getUserInfo();
		
	}
	
	private void getUserInfo() {
		
		if (TextUtils.isEmpty(user_id)) {
			return;
		}
		UserRequest userRequest = new UserRequest(Method.POST, HttpUtils.initArticleURL("user_info"), 
				SubmitPara.getUserInfo(user_id), new Listener<User>() {

					@Override
					public void onResponse(User response) {
						Log.i(TAG, "user = " + response);
						if (response != null) {
							initListData(response);
							
						}
					}
		}, null);
		
		requestQueue.add(userRequest);
		requestQueue.start();
	}
	
	private void initHead(User para) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		User user = para;
		//TODO  待修改  星座  年龄 性别(图标)
		name_tv.setText(user.user_name);
		imageLoader.displayImage(user.user_ic, icon_iv);
//		age_tv.setText(user.age);
		
	}

	
	private void initListData(User user) {
		
		initHead(user);
	    
		List<HashMap<String,String>> listData = new ArrayList<HashMap<String,String>>();
		String data[] = {user.user_name,user.genGender(),user.birth,user.address,user.signature,user.interest};
		String text[] = getResources().getStringArray(R.array.user_data);
		HashMap<String,String> map;
		
		for (int i = 0; i < 6; i++) {
			
			map = new HashMap<String,String>();
			
			map.put("data", data[i]);
			map.put("text", text[i]);
			
			listData.add(map);
		}
		
		simpleAdapter = new SimpleAdapter(this,listData,R.layout.user_data_list_item, new String[] { "text", "data" },
				new int[] { R.id.text_edit_data_tv,R.id.info_edit_data_tv });
		user_data_lv.setAdapter(simpleAdapter);
	}
	
	
}
