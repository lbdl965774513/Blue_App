package com.blue.ui.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blue.R;
import com.blue.adapter.NewsCommentAdapter;
import com.blue.app.BlueApplication;
import com.blue.bean.Article;
import com.blue.bean.ForwardNote;
import com.blue.bean.Note;
import com.blue.conninternet.DetailsDataRequest;
import com.blue.conninternet.ListDataRequest;
import com.blue.global.Evaluate;
import com.blue.lib.ptr.PullToRefreshBase;
import com.blue.lib.ptr.PullToRefreshBase.OnLastItemVisibleListener;
import com.blue.lib.ptr.PullToRefreshBase.OnRefreshListener;
import com.blue.lib.ptr.PullToRefreshListView;
import com.blue.lib.view.FlowLayout;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.lib.volley.toolbox.Volley;
import com.blue.ui.BaseActivity;
import com.blue.ui.layout.SharePopupWindow;
import com.blue.ui.news.commoper.CommentOperation;
import com.blue.util.DealText;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;
import com.blue.util.SmileyParser;
import com.blue.util.TimeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

/**
 * @author SLJM
 * @create 2014-3-21
 * @version 1.0
 * @desc 行业资讯、公司新闻 大咖秀详情
 */

public class NewsDetailsActivity extends BaseActivity implements OnClickListener,OnRefreshListener<ListView>,OnLastItemVisibleListener,OnItemClickListener{

	// 页面控件
	private PullToRefreshListView pullToRefreshListView;
	private ListView lv;
	private Button zan_btn, forward_btn, comment_btn;
	private ImageButton return_ib,collect_ib,share_ib;
	
	private View headerView;
	private ArrayList<Note> mCommentList_array;
	
	// intent传来的值
	private Intent intent;
	/**0: news 1 : note*/
	private int index;
	Boolean isCollection = false;
	//评论分页开始
	private int page_num = 0;
	//评论每页长度
	private int page_length = 5;
	private ArrayList<String> commentId_array;
	private ArrayList<String> commentUserId_array;
	private String article_id;
	private String info;
	private NewsCommentAdapter newsCommentAdapter;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private IWeiboShareAPI mWeiboShareAPI;
	private NewsHeaderView newsHeaderView;
	private NoteHeaderView noteHeaderView;
	private static final String TAG = "NewsDetailsActivity";
	/**
	 * @desc 大咖秀头部控件
	 */
	class NoteHeaderView{
		TextView name_header;
		TextView ctime_header;
		TextView phone_type_header;
		TextView zan_header;
		TextView comment_header;
		ImageView ic_header;
		FlowLayout img_fl_header;
		TextView content_header;
		//转发控件
		TextView content_re_header;
		FlowLayout img_re_header;
		RelativeLayout layout_re_header;
	}
	/**
	 * @desc 新闻头部控件
	 */
	class NewsHeaderView{
		TextView title_header;
		TextView ctime_header;
		TextView content_header;
		ImageView img_header;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_details);

		requestQueue = Volley.newRequestQueue(this);
		imageLoader = ImageLoader.getInstance();
		
		getIntentValues();
		initView();
		initHeaderView();
		getDetailsInfo();
		getCommentInfo(page_num);
		
	}

	/** 取出Intent传来的值*/
	private void getIntentValues() {
		
		intent = getIntent();
		article_id = intent.getStringExtra("article_id");
		index = intent.getIntExtra("index", -1);
		Log.i(TAG, "index(0:news 1:note) = " + index);
	}
	
	/** 初始化布局 */
	private void initView() {
		
		mWeiboShareAPI = BlueApplication.mWeiboShareAPI;
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.news_details_lv);
		return_ib = (ImageButton) findViewById(R.id.return_actionbar_ib);
		collect_ib = (ImageButton) findViewById(R.id.collect_actionbar_ib);
		share_ib = (ImageButton) findViewById(R.id.share_actionbar_ib);
		zan_btn = (Button) findViewById(R.id.zan_news_details_btn);
		forward_btn = (Button) findViewById(R.id.forward_news_details_btn);
		comment_btn = (Button) findViewById(R.id.comment_news_details_btn);

		lv = pullToRefreshListView.getRefreshableView();
		lv.setHeaderDividersEnabled(true);
		
		zan_btn.setOnClickListener(this);
		forward_btn.setOnClickListener(this);
		comment_btn.setOnClickListener(this);
		
		return_ib.setOnClickListener(this);
		collect_ib.setOnClickListener(this);
		share_ib.setOnClickListener(this);
		pullToRefreshListView.setOnItemClickListener(this);
		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		
	}

	/** 初始化头部布局 */
	private void initHeaderView() {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
		
		//新闻详情
		if(index == 0){
			headerView = layoutInflater.inflate(R.layout.news_details_header, lv,false);
			newsHeaderView = new NewsHeaderView();
			
			initNewsHeaderView(headerView,newsHeaderView);
		}
		//大咖秀详情
		else if(index == 1){
			headerView = layoutInflater.inflate(R.layout.note_details_header, lv,false);
			noteHeaderView = new NoteHeaderView();
			
			initNoteHeaderView(headerView,noteHeaderView);
		}

	}

	private void initNewsHeaderView(View headerView,NewsHeaderView newsHeaderView) {
		View view = headerView ;
		NewsHeaderView newsHeader = newsHeaderView;
		
		newsHeader.title_header = (TextView) view.findViewById(R.id.title_news_details_header_tv);
		newsHeader.ctime_header = (TextView) view.findViewById(R.id.ctime_news_details_header_tv);
		newsHeader.img_header = (ImageView) view.findViewById(R.id.img_news_details_header_tv);
		newsHeader.content_header = (TextView) view.findViewById(R.id.content_news_details_header_tv);
		
		
	}

	private void initNoteHeaderView(View headerView,NoteHeaderView noteHeaderView) {
		View view = headerView;
		NoteHeaderView noteHeader = noteHeaderView;
		
		noteHeader.name_header = (TextView) view.findViewById(R.id.name_note_details_tv);
		noteHeader.phone_type_header = (TextView) view.findViewById(R.id.phone_type_note_details_tv);
		noteHeader.ctime_header = (TextView) view.findViewById(R.id.ctime_note_details_tv);
		noteHeader.ic_header = (ImageView) view.findViewById(R.id.ic_note_details_iv);
		noteHeader.content_header = (TextView) view.findViewById(R.id.content_note_details_tv);
		noteHeader.img_fl_header = (FlowLayout) view.findViewById(R.id.img_note_details_fl);
		//赞,评论列表
		noteHeader.zan_header = (TextView) view.findViewById(R.id.zan_note_details_tv);
		noteHeader.comment_header = (TextView) view.findViewById(R.id.comment_note_details_tv);
		//转发
		noteHeader.layout_re_header = (RelativeLayout) view.findViewById(R.id.forward_note_details_layout);
		noteHeader.content_re_header = (TextView) view.findViewById(R.id.content_forward_note_tv);
		noteHeader.img_re_header = (FlowLayout) view.findViewById(R.id.img_forward_note_fl);
	}


	/** 获取文章详情数据 */
	private void getDetailsInfo() {
		
		DetailsDataRequest request = new DetailsDataRequest(Method.POST, HttpUtils.initArticleURL("article_info"),
				pulltoMap(), new Listener<Article>() {

					@Override
					public void onResponse(Article response) {
						Log.i(TAG, response.title);
						if (response != null ) {
							initDetailsData(response);
						}
					}
				}, null);
		requestQueue.add(request);
		requestQueue.start();
	}
	
	/**初始化文章详情部分*/
	private void initDetailsData(Article response) {
		info = response.content;
		
		switch (index) {
		//新闻详情
		case 0:
			initNewsDetails(response);
			break;
		//帖子详情
		case 1:
			initNoteDetails(response);
			break;

		default:
			break;
		}
//		int img_length = 0;
		
		lv.addHeaderView(headerView,null,false);
	}
	
	private void initNewsDetails(Article response) {
		Article article = response;
		
		newsHeaderView.title_header.setText(article.title);
		newsHeaderView.ctime_header.setText(TimeUtil.getStandardDate(article.ctime));
		newsHeaderView.content_header.setText(article.content);
		
		if (!TextUtils.isEmpty(article.content)) {
			newsHeaderView.img_header.setVisibility(View.VISIBLE);
			imageLoader.displayImage(article.img, newsHeaderView.img_header);
		}
		
	}

	private void initNoteDetails(Article response) {
		Article article = response;
		
		noteHeaderView.zan_header.setOnClickListener(this);
		noteHeaderView.comment_header.setOnClickListener(this);
		Boolean isForward = article.type.equals("5");
	
		noteHeaderView.name_header.setText(article.user_name);
		noteHeaderView.phone_type_header.setText("来自:"+article.phone_type);
		noteHeaderView.ctime_header.setText(TimeUtil.getStandardDate(article.ctime));
		
		SmileyParser parser = new SmileyParser(this);
		DealText.textViewSpan(this, noteHeaderView.content_header, parser.replace(info),"",  false);
		
//		noteHeaderView.content_header.setText(info);
		imageLoader.displayImage(article.user_icon,noteHeaderView.ic_header);
	
		if (!TextUtils.isEmpty(article.img)) {
			ImageView imageView = new ImageView(this);
			noteHeaderView.img_fl_header.removeAllViews();
//			imageView.setLayoutParams(new LayoutParams(300,300));
			noteHeaderView.img_fl_header.addView(imageView);
			imageLoader.displayImage(article.img,imageView);
		}
		
		if (isForward) {
			ForwardNote forwardNote = article.forwardNote;
			noteHeaderView.content_re_header.setText(forwardNote.user_name+":"+forwardNote.content);
			String forwardText = "@" + forwardNote.user_name + " : " + forwardNote.content;
			SpannableString spanableInfo = new SpannableString(forwardText);
			DealText.textViewSpan(this, noteHeaderView.content_re_header, spanableInfo,forwardNote.user_id,true);
			
			//当有图片的时候执行
			if (!TextUtils.isEmpty(forwardNote.img)) {
				ImageView forward_iv = new ImageView(this);
				noteHeaderView.img_re_header.setVisibility(View.VISIBLE);
				noteHeaderView.img_re_header.removeAllViews();
//				forward_iv.setLayoutParams(new LayoutParams(150, 150));
				noteHeaderView.img_re_header.addView(forward_iv);
				imageLoader.displayImage(forwardNote.img,forward_iv);
				Log.i(TAG, "forward img" + forwardNote.img);
			}else{
				noteHeaderView.img_re_header.setVisibility(View.GONE);
			}
		}else{
			noteHeaderView.layout_re_header.setVisibility(View.GONE);
		}
	}

//TODO
	/** 获取评论信息数据 */
	private void getCommentInfo(int page_number) {
		
		ListDataRequest listDataRequest = new ListDataRequest(Method.POST, HttpUtils.initArticleURL("comment_list"), 
				PulltoMap(1,page_number), new Listener<ArrayList<Note>>() {

					@Override
					public void onResponse(ArrayList<Note> response) {
//						if (response != null && !response.isEmpty()) {
							initAdapter(response);
//						}
					}
				}, null); 

		requestQueue.add(listDataRequest);
		requestQueue.start();
	}
	
	

	/**初始化评论列表适配器*/
	private void initAdapter(ArrayList<Note> comment_array) {
		
		mCommentList_array = comment_array;
		
		if(newsCommentAdapter == null){
			newsCommentAdapter = new NewsCommentAdapter(this,comment_array);
			
			commentId_array = new ArrayList<String>();
			commentUserId_array = new ArrayList<String>();
			
			getCommmentId(commentId_array);
			getCommentUserId(commentUserId_array);
			
			pullToRefreshListView.setAdapter(newsCommentAdapter);
		}else{
			if (page_num == 0) {
				
				commentId_array.clear();
				commentUserId_array.clear();
				
				newsCommentAdapter.addFirstPageData(mCommentList_array);
				
				getCommmentId(commentId_array);
				getCommentUserId(commentUserId_array);
			} else {
				newsCommentAdapter.addOtherPageData(comment_array);
			}
		}
		
		pullToRefreshListView.onRefreshComplete();
	}
	
	/**获取评论列表的id*/
	private ArrayList<String> getCommmentId(ArrayList<String> commentId_array) {
		
		
		for (int i = 0; i < mCommentList_array.size(); i++) {
			
			commentId_array.add(mCommentList_array.get(i).id);
//			Log.i(TAG, "comment id = " + mCommentList_array.get(i).id);
		}
		return commentId_array;
	}
	
	/**获取评论人的user_id*/
	private ArrayList<String> getCommentUserId(ArrayList<String> commentUserId_array) {
		
		for (int i = 0; i < mCommentList_array.size(); i++) {
			
			commentUserId_array.add(mCommentList_array.get(i).user_id);
//			Log.i(TAG, "comment id = " + mCommentList_array.get(i).user_id);
		}

		return commentUserId_array;
	}
	
	/** 将获取文章详情(0) 评论信息(1)赞信息(2)转发信息(3)所需要的数据添加到Map中 */
	private Map<String, String> PulltoMap(int index,int pagenum_map) {

		Map<String, String> comment_map = new HashMap<String, String>();
		comment_map.put("article_id", article_id);
		
		switch (index) {
		case 1:
		case 2:
		case 3:
			comment_map.put("start", String.valueOf(pagenum_map));
			comment_map.put("length", String.valueOf(10));
			break;
		}

		return comment_map;
	}
	
	private Map<String,String> pulltoMap() {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("article_id", article_id);
		
		return map;
	}
//TODO
	/**赞列表*/
	private void getZanList(int page_number) {
		ListDataRequest listDataRequest = new ListDataRequest(Method.POST, HttpUtils.initArticleURL("up_list"), 
				PulltoMap(2, page_number), new Listener<ArrayList<Note>>() {

			@Override
			public void onResponse(ArrayList<Note> response) {
				if (!response.isEmpty()) {
					initAdapter(response);
				}
			}
		}, null);
		requestQueue.add(listDataRequest);
		requestQueue.start();
	}
	
	/** 赞的网络访问 */
	public void ZanonClick() {

		Map<String, String> map = ZanMap();

		if (map == null || map.isEmpty())
			return;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, HttpUtils.initArticleURL("up"),
				map,new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "zan"+response );
						AnalyzeZanData(response);
					}
				}, null);

		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}

	private Map<String, String> ZanMap() {
		
		String user_id = LoginUtils.getLoginUserid(this);
		Log.i(TAG, "user_id" + user_id);
		Map<String, String> map = new HashMap<String, String>();
		
		if (BlueApplication.isLogin(this)) {
			
			map.put("article_id", article_id);
			map.put("user_id", user_id);
		}


		return map;
	}

	/**解析赞的返回数据*/
	public void AnalyzeZanData(JSONObject response) {
		
		Drawable left_img;
		
		if (response.optString("msg").equals("1")) {
			left_img = getResources().getDrawable(R.drawable.zan_press);
			left_img.setBounds(0, 0, left_img.getMinimumWidth(), left_img.getMinimumHeight());
//			zan_btn.setText("赞成功");
			zan_btn.setTextColor(getResources().getColor(R.color.orange));
			zan_btn.setCompoundDrawables(left_img, null, null, null);
		} else {
			Toast.makeText(NewsDetailsActivity.this, "取消赞", Toast.LENGTH_SHORT).show();
		}

	}
	
	@Override
	public void onClick(View view) {
		
		Evaluate evaluate = new Evaluate(this,article_id);
		Resources resources = NewsDetailsActivity.this.getResources();
		
		switch (view.getId()) {
		//
		case R.id.return_actionbar_ib:
			this.finish();
			break;
		case R.id.share_actionbar_ib:
			new SharePopupWindow(this,info,mWeiboShareAPI).sharePop();
			break;
		case R.id.collect_actionbar_ib:
			Log.i(TAG,"isCollection = " + isCollection);
			if (isCollection) {
				isCollection = false;
				collect_ib.setImageDrawable(resources.getDrawable(R.drawable.collect_unpress));
			}else{
				isCollection = true;
				collect_ib.setImageDrawable(resources.getDrawable(R.drawable.collect_press));
			}
			
			evaluate.CollectionNote();
			break;
		//赞列表
		case R.id.zan_note_details_tv:
			page_num = 0;
			getZanList(page_num);
			break;
		//评论列表
		case R.id.comment_note_details_tv:
			page_num = 0;
			getCommentInfo(page_num);
			break;
		// 赞
		case R.id.zan_news_details_btn:
			ZanonClick();
			break;
		// 转发
		case R.id.forward_news_details_btn:
			evaluate.forward();
			break;
		// 评论
		case R.id.comment_news_details_btn:
			evaluate.comment();
			break;
		default:
			break;
		}

	}
	
	@Override
	public void onLastItemVisible() {
		page_num = page_length + page_num;
		getCommentInfo(page_num);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		page_num = 0;
		getCommentInfo(page_num);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		requestQueue.stop();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.i(TAG, "position = " + position);
		Log.i(TAG, "size = " + commentId_array.size());
		
//		CommentOperation operation = new CommentOperation(this, commentId_array.get(position - 2),
//				commentUserId_array.get(position - 2));
//		
//		operation.showDialog();
	}
	
}
