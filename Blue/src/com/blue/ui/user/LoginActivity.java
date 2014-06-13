package com.blue.ui.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.app.BlueException;
import com.blue.jpush.ExampleUtil;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.ui.BaseActivity;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;

/**
 * @author SLJM
 * @create 2014-3-23
 * @version 1.0
 * @desc 登录功能
 */

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText name_et;
	private EditText password_et;
	private Button loginBtn;
	private CheckBox autoLogin;
	private TextView register_tv;
	
	private String account = "";
	private String password = "";
	private String user_id = "";
	private RequestQueue requestQueue = BlueApplication.requestQueue;
	private static final String TAG = "LoginActivity";

	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private static final String CONSUMER_KEY = "1094032784";// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://www.sina.com.cn";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		initView();
	}

	private void initView() {
		name_et = (EditText) findViewById(R.id.name_login_et);
		password_et = (EditText) findViewById(R.id.password_login_et);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		register_tv = (TextView) findViewById(R.id.register_login_tv);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);

		loginBtn.setOnClickListener(this);
		register_tv.setOnClickListener(this);
	}

	/** 初始化数据 */
	private Boolean initData() {
		
		account = name_et.getText().toString();
		password = password_et.getText().toString();
		if (account.equals("") || password.equals("")|| account == null || password == null) {
			BlueException.toast(BlueException.NOT_BE_EMPTY);
			return false;
		} else{
			return true;
		}
	}

	/** 登录信息封装成一个Map */
	private Map<String, String> PulltoMap() {
		Map<String, String> loginInfo_map = new HashMap<String, String>();

		loginInfo_map.put("name", account);
		loginInfo_map.put("password", password);
		Log.i(TAG,"map"+loginInfo_map);
		return loginInfo_map;
	}

	/** 提交登录信息数据 */
	private void submitLoginInfo() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, HttpUtils.initArticleURL("login"),
				PulltoMap(), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG,"login info = " + response);
						
						if (response != null) {
							try {
								analyticalData(response);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}, null);
		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	/** 解析返回的数据 
	 * @throws JSONException */
	private void analyticalData(JSONObject response) throws JSONException {
		
		JSONObject loginData_obj = response;
		String code = loginData_obj.optString("code");
		
		if(code.equals("1")){
			user_id = loginData_obj.optString("msg");
			LoginUtils.saveLoginUserid(user_id, this);
			BlueException.toast("登录成功");
//			startActivity(new Intent(this,MainActivity.class));
			setAlias(user_id);
			this.finish();
			
		}
		else{
			BlueException.toast("登录失败");
		}
		
		
	}
	private void setAlias(String alias) {
		if (TextUtils.isEmpty(alias)) {
			Toast.makeText(this, "辅导书",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(this, "放大法",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// 调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}

	private final Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d(TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;

			case MSG_SET_TAGS:
				Log.d(TAG, "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i(TAG, "Unhandled msg - " + msg.what);
			}
		}
	};
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias),1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}
			// ExampleUtil.showToast(logs, getApplicationContext());
		}

	};

	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_TAGS, tags),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			
			if(!initData())
				return;
			
			submitLoginInfo();
			break;
			
		case R.id.register_login_tv:
			startActivity(new Intent(this,RegisterActivity.class));
			break;
		
//		case R.id.qqlogin_login_btn:
//			mWeibo.authorize(this, new AuthDialogListener(this));
			
		default:
			break;
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
    }

}
