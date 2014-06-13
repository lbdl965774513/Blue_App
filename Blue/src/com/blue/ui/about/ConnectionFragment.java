package com.blue.ui.about;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blue.R;
import com.blue.app.BlueException;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.lib.volley.toolbox.Volley;
import com.blue.util.HttpUtils;

/**
 * @author SLJM
 * @desc 联系我们
 *
 */
public class ConnectionFragment extends Fragment implements OnClickListener,TextWatcher  {
	
	private TextView number;
	private TextView address;
	private EditText message;
	private Button submit;
	private String phone_number;
	private CharSequence message_cs;
	private RequestQueue requestQueue;
	private static final String TAG = "ConnectionFragment";
	
	public static ConnectionFragment getInstance() {
		ConnectionFragment frag = new ConnectionFragment();
		return frag;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.connection_about, container, false);
		
		initView(view, inflater);
		GetConnInfo();
		
		return view;
	}
	
	
	/**初始化*/
	private void initView(View contentView, LayoutInflater inflater) {
		requestQueue = Volley.newRequestQueue(getActivity());
		
		number = (TextView)contentView.findViewById(R.id.number_tv);
		address = (TextView)contentView.findViewById(R.id.address_tv);
		message = (EditText)contentView.findViewById(R.id.message_ev);
		submit = (Button)contentView.findViewById(R.id.submit_btn);
		
		number.setOnClickListener(this);
		message.addTextChangedListener(this);
		submit.setOnClickListener(this);
	}
	
	private void GetConnInfo() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, HttpUtils.initArticleURL("site_info"),
				null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, response+"");
						if (response != null && response.optString("code").equals("1")) {
							InitData(response);
						}
					}

				}, null);
		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	private void InitData(JSONObject response) {
		JSONObject jsonObject;
		try {
			jsonObject = response.getJSONObject("msg");
			phone_number = jsonObject.optString("service_phone");
			number.setText(phone_number);
			address.setText("地址:"+jsonObject.optString("service_address")+"\n电话:"
					+phone_number+"\nE-mail:"+jsonObject.optString("service_email"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	private void CallPhone() {
		//用intent启动拨打电话   
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone_number));  
        startActivity(intent);
	}
	
	private void Submit() {
		
		if (message_cs.equals("")) 
			return;
			
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("article_id", String.valueOf(40));
		map.put("info", message_cs.toString());
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, 
				HttpUtils.initArticleURL("comment_list"), map, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "submit"+response);
						if (response !=null || response.optString("code").equals("1")) {
							BlueException.toast("提交成功");
						}
					}
				}, null);
		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	@Override
	public void onStop() {
		super.onStop();
		requestQueue.stop();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.number_tv:
			CallPhone();
			break;

		case R.id.submit_btn:
			Submit();
			break;
		}
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		submit.setBackgroundColor(getResources().getColor(R.color.green));
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
		message_cs = arg0;
	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (arg0.equals("")) {
			submit.setBackgroundColor(getResources().getColor(R.color.gray_btn));
		} else {
			submit.setBackgroundColor(getResources().getColor(R.color.green));

		}
	}

}
