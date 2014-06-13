package com.blue.ui.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.app.BlueException;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.lib.volley.toolbox.Volley;
import com.blue.ui.BaseActivity;
import com.blue.util.HttpUtils;

/**
 * @author SLJM
 * @create 2014-3-26
 * @version 1.0
 * @desc 注册页面
 */

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private EditText uname_et;
	private EditText password_et;
	private EditText mail_et;
	private EditText confirmpwd_et;
	private Button register_btn;
	
	private String uname, password, confirmpwd;

	private RequestQueue requestQueue = BlueApplication.requestQueue;

	private static final String TAG = "RegisterActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initView();
		initData();
	}

	private void initView() {

		uname_et = (EditText) findViewById(R.id.uname_register_et);
		password_et = (EditText) findViewById(R.id.passwd_register_et);
		confirmpwd_et = (EditText) findViewById(R.id.passwd2_register_et);
		mail_et = (EditText) findViewById(R.id.mail_register_et);
		register_btn = (Button) findViewById(R.id.register_register_btn);

		register_btn.setOnClickListener(this);
	}

	/** v检查用户注册信息 */
	private Boolean checkRegisterInfo() {

		uname = uname_et.getText().toString();
		password = password_et.getText().toString();
		confirmpwd = confirmpwd_et.getText().toString();

		if (uname == null || uname.equals("") || password == null|| password.equals("") 
				|| confirmpwd == null|| confirmpwd.equals("")) {
			BlueException.toast("注册信息填写不完整");
			return false;
			
		} else if (!password.equals(confirmpwd)) {
			
			BlueException.toast("两次密码输入不一致");
			return false;
		}else
		    return true;
	}

	/** 得到注册所需要的信息 toMap */
	private Map<String,String> PulltoMap() {
		
		Map<String,String> map = new HashMap<String,String>();
			
		map.put("name",uname );
		map.put("password", password);
			
		Log.i(TAG, "map"+map);
		return map;
		
	}

	/** 提交注册数据 */
	private void postRegisterInfo() {

		if (!checkRegisterInfo())
			return;

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, HttpUtils.initArticleURL("register"),
				PulltoMap(), new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						Log.i(TAG, "register" + response);
						if (response != null && response.optString("code").equals("1")) {
							BlueException.toast("注册成功,快去登录吧");
							startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
							finish();
						}
					}
				}, null);

		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	private void initData() {
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.register_register_btn:
			postRegisterInfo();
			break;

		default:
			break;
		}
	}

}
