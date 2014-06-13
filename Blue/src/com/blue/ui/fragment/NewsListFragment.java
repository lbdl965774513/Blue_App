package com.blue.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.blue.R;
import com.blue.adapter.KaXiuListAdapter;
import com.blue.adapter.NewsListAdapter;
import com.blue.app.BlueApplication;
import com.blue.bean.Note;
import com.blue.conninternet.ListDataRequest;
import com.blue.global.NewsOnItemClickListener;
import com.blue.lib.ptr.PullToRefreshBase;
import com.blue.lib.ptr.PullToRefreshBase.OnLastItemVisibleListener;
import com.blue.lib.ptr.PullToRefreshBase.OnRefreshListener;
import com.blue.lib.ptr.PullToRefreshListView;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.Response.Listener;
import com.blue.ui.news.NewsCommentActivity;
import com.blue.ui.user.LoginActivity;
import com.blue.ui.user.UserCenterActivity;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;

/**
 * @author SLJM
 * @create 2014-3-21
 * @version 1.0
 * @desc ��˾�����б�(0) �����б�(1)  
 */

public class NewsListFragment extends Fragment implements OnClickListener,OnLastItemVisibleListener, OnRefreshListener<ListView> {

	// ҳ��ؼ�
	private PullToRefreshListView pullToRefreshListView;
	// actionbar
	private ImageButton post_ib, user_center_ib;
	private TextView title_tv;
	// ��ʶ 0���������б�ҳ�� 1��������ҳ��
	private static int index;
	/** ���ӱ�ʶ(�ڼ�������)*/
	private int page_number = 0;
	// ��ҳ����
	private static final int page_length = 10;
	private NewsListAdapter newsListAdapter;
	private KaXiuListAdapter kaXiuListAdapter;
	private static final String TAG = "NewsListFragment";

	public NewsListFragment() {
		super();
	}

	/** �õ���ʶ 0���������б�ҳ�� 1��������ҳ�� */
	public static NewsListFragment getInstance(int index) {

		NewsListFragment newsListFragment = new NewsListFragment();
		NewsListFragment.index = index;

		return newsListFragment;
	}

	/** �ڶ��ν���ִ�� */
	public static void setIndex(int index) {
		NewsListFragment.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.home, container, false);

		initView(view, inflater);
		getNewsList(page_number);

		return view;

	}

	// ��ʼ������Ԫ��
	private void initView(View contentView, LayoutInflater inflater) {

		pullToRefreshListView = (PullToRefreshListView) contentView.findViewById(R.id.home_main_lv);
		user_center_ib = (ImageButton) contentView.findViewById(R.id.user_actionbar_ib);
		post_ib = (ImageButton) contentView.findViewById(R.id.post_actionbar_ib);
		title_tv = (TextView) contentView.findViewById(R.id.title_actionbar_tv);
		
//		pullToRefreshListView.getRefreshableView().setFastScrollEnabled(true);

		updateTitle();

		user_center_ib.setOnClickListener(this);
		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		pullToRefreshListView.setOnItemClickListener(new NewsOnItemClickListener(getActivity(),index));

	}

	/** ���� */
	private void updateTitle() {

		switch (index) {
		case 0:
			title_tv.setText("��˾����");
			post_ib.setVisibility(View.GONE);
			break;

		case 1:
			title_tv.setText("����");
			post_ib.setOnClickListener(this);
			break;
		}
	}
	
	/** ��ȡ��˾����(��������)�б���Ϣ */
	private void getNewsList(int page_number) {
		
		ListDataRequest listDataRequest = new ListDataRequest(Method.POST, HttpUtils.initArticleURL("article_list"), 
				getMap(page_number), new Listener<ArrayList<Note>>() {

					@Override
					public void onResponse(ArrayList<Note> response) {
						if (response != null && !response.isEmpty() ) {
							initAdapter(response);
						}
					}
				}, null);
		BlueApplication.requestQueue.add(listDataRequest);
		BlueApplication.requestQueue.start();
	}

	private Map<String, String> getMap(int pageNum) {
		Map<String, String> map = new HashMap<String, String>();

		switch (index) {
		// ��˾����
		case 0:
			map.put("start", Integer.toString(pageNum));
			map.put("type", "3");
			break;
		// ����
		case 1:
			map.put("start", Integer.toString(pageNum));
			break;
		}
		return map;
	}

	
	/**��ʼ��������*/
	private void initAdapter(ArrayList<Note> response) {
		
		switch (index) {
		//��˾�����б�
		case 0:
			InitNewsAdapter(response);
			break;
		//�����б�
		case 1:
			InitKaxiuAdapter(response);
			break;
		}
		pullToRefreshListView.onRefreshComplete();
	}
	
	/**��ʼ����˾����������*/
	private void InitNewsAdapter(ArrayList<Note> note_array) {
		if(newsListAdapter == null){
			newsListAdapter = new NewsListAdapter(getActivity(),note_array);
			pullToRefreshListView.setAdapter(newsListAdapter);
		}else{
			if (page_number == 0) {
				newsListAdapter.addFirstPageData(note_array);
			} else {
				newsListAdapter.addOtherPageData(note_array);
			}
		}
		
	}
	/**��ʼ�������б�������*/
	private void InitKaxiuAdapter(ArrayList<Note> note_array) {
		if(kaXiuListAdapter == null){
			kaXiuListAdapter = new KaXiuListAdapter(getActivity(),note_array,0);
			pullToRefreshListView.setAdapter(kaXiuListAdapter);
		}else{
			if (page_number == 0) {
				kaXiuListAdapter.addFirstPageData(note_array);
			} else {
				kaXiuListAdapter.addOtherPageData(note_array);
			}
		}
	}


	@Override
	public void onLastItemVisible() {
		page_number = page_number + page_length;
		getNewsList(page_number);

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();
		Context context = getActivity();

		switch (v.getId()) {
		case R.id.user_actionbar_ib:
			if (BlueApplication.isLogin(getActivity())) {
				intent.setClass(context, UserCenterActivity.class);
				intent.putExtra("user_id", LoginUtils.getLoginUserid(getActivity()));
			}else{
				intent.setClass(context,LoginActivity.class);
			}
			break;

		case R.id.post_actionbar_ib:
			intent.putExtra("index", 0);
			intent.setClass(context, NewsCommentActivity.class);
			break;
		}
		startActivity(intent);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		BlueApplication.requestQueue.stop();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		page_number = 0;
		getNewsList(page_number);
	}

}

