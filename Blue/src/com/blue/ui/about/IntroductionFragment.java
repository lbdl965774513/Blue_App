package com.blue.ui.about;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blue.R;
import com.blue.app.BlueException;
import com.blue.bean.Article;
import com.blue.conninternet.DetailsDataRequest;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.lib.volley.toolbox.Volley;
import com.blue.util.DisplayImageUtils;
import com.blue.util.HttpUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author SLJM
 * @create 2014-3-28
 * @desc 关于深蓝  0: 公司简介  3: 我们的团队
 */
public class IntroductionFragment extends Fragment  {
	
	private ImageView image;
	private TextView content;
	private ImageLoader imageLoader;
	/**0: 公司简介  3: 我们的团队*/
	private static int index;
	private String url;
	private RequestQueue requestQueue;
	
	private static final String TAG = "IntroductionFragment";
	
	public static IntroductionFragment getInstance(int page) {
		index = page;
		IntroductionFragment frag = new IntroductionFragment();
		
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View introductionView = inflater.inflate(R.layout.introduce_about, container,false); 
		
		initView(introductionView,inflater);
		getInfo();
		
		return introductionView;
	}
	
	/**初始化控件*/
	private void initView(View contentView,LayoutInflater inflater) {
		
		imageLoader = ImageLoader.getInstance();
		requestQueue = Volley.newRequestQueue(getActivity());
		url = HttpUtils.initArticleURL("article_info");
		
		image = (ImageView)contentView.findViewById(R.id.introduce_about_iv);
		content = (TextView)contentView.findViewById(R.id.introduce_about_tv);
	}
	private Map<String,String> PushtoMap() {
		Map <String,String> map = new HashMap<String, String>();
		
		switch (index) {
		case 0:
			map.put("article_id", String.valueOf(7));
			break;
		case 3:
			map.put("article_id", String.valueOf(9));
			break;
		}
		return map;

	}

	/**获取网络数据*/
	private void getInfo() {
		
		DetailsDataRequest detailsDataRequest = new DetailsDataRequest(Method.POST, url, PushtoMap(),
				new Listener<Article>() {
					@Override
					public void onResponse(Article response) {
						Log.i(TAG,"response" + response.title);
						if (response != null) {
							initData(response);
						}
					}
				}, null);
		
		requestQueue.add(detailsDataRequest);
		requestQueue.start();
	}
	
	/**初始化数据*/
	private void initData(Article response) {
		Article article = response;
		
		content.setText(article.content);
		imageLoader.displayImage(article.img, image);
	}


	@Override
	public void onStop() {
		super.onStop();
		requestQueue.stop();
	}

}
