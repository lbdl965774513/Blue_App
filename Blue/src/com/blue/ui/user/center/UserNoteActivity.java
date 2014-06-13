package com.blue.ui.user.center;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blue.R;
import com.blue.adapter.KaXiuListAdapter;
import com.blue.app.BlueApplication;
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
import com.blue.ui.BaseActivity;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;
import com.blue.util.SubmitPara;

/**
 * @author SLJM
 * @create 2014-5-15
 * @desc 我的帖子列表
 *
 */
public class UserNoteActivity extends BaseActivity implements OnLastItemVisibleListener,OnRefreshListener<ListView>{
	
	private TextView empty_tv;
	private PullToRefreshListView pullToRefreshListView;
	
	private int note_number = 0;
	private KaXiuListAdapter kaXiuListAdapter;
	private RequestQueue requestQueue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_note);
		
		init();
		
	}
	
	private void init() {
		
		requestQueue = BlueApplication.requestQueue;
		
		empty_tv = (TextView) findViewById(R.id.empty_user_note_tv);
		pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.user_note_lv);
	
		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		pullToRefreshListView.setOnItemClickListener(new NewsOnItemClickListener(this,1));
		
		
		getNoteList(note_number);
		requestQueue.start();
	}

	private void getNoteList(int number) {
		
		if (!BlueApplication.isLogin(this)) {
			return;
		}
		
		ListDataRequest listDataRequest = new ListDataRequest(Method.POST, HttpUtils.initArticleURL("article_list"),
				SubmitPara.getNoteList(1, number, LoginUtils.getLoginUserid(this)), new Listener<ArrayList<Note>>() {

					@Override
					public void onResponse(ArrayList<Note> response) {
							initAdapter(response);
					}

				}, null);
		requestQueue.add(listDataRequest);
	}

	private void initAdapter(ArrayList<Note> note_array) {
		Log.i("UserNoteActivity", " is empty = " + note_array.isEmpty());
		
		if (note_array.isEmpty()) {
			empty_tv.setVisibility(View.VISIBLE);
			pullToRefreshListView.setEmptyView(empty_tv);
			return;
		}
		
			if(kaXiuListAdapter == null){
				kaXiuListAdapter = new KaXiuListAdapter(this,note_array,0);
				pullToRefreshListView.setAdapter(kaXiuListAdapter);
			}else{
				if (note_number == 0) {
					kaXiuListAdapter.addFirstPageData(note_array);
				} else {
					kaXiuListAdapter.addOtherPageData(note_array);
				}
			}
			pullToRefreshListView.onRefreshComplete();
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		note_number = note_number + 10;
		getNoteList(note_number);
		requestQueue.start();
		
		
	}

	@Override
	public void onLastItemVisible() {
		note_number = 0;
		getNoteList(note_number);
		requestQueue.start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		requestQueue.stop();
	}

}
