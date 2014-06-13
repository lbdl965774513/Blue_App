package com.blue.ui.user.center;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blue.R;
import com.blue.adapter.KaXiuListAdapter;
import com.blue.bean.Note;
import com.blue.conninternet.ListDataRequest;
import com.blue.global.NewsOnItemClickListener;
import com.blue.lib.ptr.PullToRefreshBase;
import com.blue.lib.ptr.PullToRefreshBase.OnLastItemVisibleListener;
import com.blue.lib.ptr.PullToRefreshBase.OnRefreshListener;
import com.blue.lib.ptr.PullToRefreshListView;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.Volley;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;
import com.blue.util.SubmitPara;

/**
 * @author SLJM
 * @create 2014-4-16
 * @desc 我的收藏
 *
 */
public class CollectionNoteActivity extends Activity implements OnLastItemVisibleListener, OnRefreshListener<ListView> {
	
	private TextView empty_tv;
	private PullToRefreshListView pullToRefreshListView;
	
	private RequestQueue requestQueue;
	private KaXiuListAdapter adapter;
	/** 帖子标识(第几条帖子)*/
	private int page_number = 0;
	private static final String TAG = "CollectionNoteActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection);
		
		initView();
		getCollectionList(page_number);
	}
	
	private void initView() {
		requestQueue = Volley.newRequestQueue(this);
		
		empty_tv = (TextView) findViewById(R.id.empty_collection_tv);
		pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.collection_lv);
		
		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		pullToRefreshListView.setOnItemClickListener(new NewsOnItemClickListener(this, 1));
		
	}

	/**请求网络获取收藏数据*/
	private void getCollectionList(int number) {
		
		String user_id = LoginUtils.getLoginUserid(this);
		if (TextUtils.isEmpty(user_id)) {
			return;
		}
		Log.i(TAG, "user_id = " + user_id);
		ListDataRequest dataRequest = new ListDataRequest(Method.POST, HttpUtils.initArticleURL("collect_list"), 
				SubmitPara.getNoteList(1, number, user_id), new Listener<ArrayList<Note>>() {
					@Override
					public void onResponse(ArrayList<Note> response) {
						Log.i(TAG, "collection list = " + response);
							initAdapter(response);
						pullToRefreshListView.onRefreshComplete();
					}
				}, null);
		
		requestQueue.add(dataRequest);
		requestQueue.start();
	}
	
	/**初始化适配器*/
	private void initAdapter(ArrayList<Note> note_array) {
		
		if (note_array.isEmpty()) {
			empty_tv.setVisibility(View.VISIBLE);
			pullToRefreshListView.setEmptyView(empty_tv);
			return;
		}
		
		if(adapter == null){
			adapter = new KaXiuListAdapter(this,note_array,1);
			pullToRefreshListView.setAdapter(adapter);
		}else{
			if (page_number == 0) {
				adapter.addFirstPageData(note_array);
			} else {
				adapter.addOtherPageData(note_array);
			}
		}
	}
	
	/**删除我的收藏列表里的帖子*/
	public void deleteCollect(){
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		requestQueue.stop();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		page_number = 0;
		getCollectionList(page_number);
	}

	@Override
	public void onLastItemVisible() {
		page_number = page_number + 10;
		getCollectionList(page_number);
	}

}
