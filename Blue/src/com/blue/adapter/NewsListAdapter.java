package com.blue.adapter;

import java.util.ArrayList;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.bean.Note;
import com.blue.global.Evaluate;
import com.blue.ui.layout.SharePopupWindow;
import com.blue.ui.news.NewsDetailsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 *@author SLJM
 *@create  2014-1-23
 *@version 1.0
 *@desc   新闻适配器(公司新闻和行业资讯)
 */

public class NewsListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Note> content_array;
	private ViewHolder holder;
	
	//赞个数
	int zan_count;
	//评论个数
	int comment_count;
	//转发个数
//	int forward_count;
	private Note note_item;
	
	private static final String TAG = "NewsListAdapter";
	
	private ImageLoader imageLoader;
	 
	public NewsListAdapter(Context context, ArrayList<Note> content_array) {
		
		this.context = context;
		this.content_array = content_array;
		
		imageLoader = ImageLoader.getInstance();
		
	}

	private static class ViewHolder {
		
		ImageView article_iv;
		TextView title_tv;
		TextView content_tv;
//		TextView ctime_tv;
		TextView zan_tv;
		TextView share_tv;
		TextView comment_tv;
//		FlowLayout img_fl;
//		ArrayList<ImageView> photo_list;
		
//		public ViewHolder(){
//			photo_list = new ArrayList<ImageView>(5);
//			
//			for (int i = 0; i < 5; i++) {
//				ImageView photo_iv = new ImageView(context);
//				photo_list.add(photo_iv);
//			}
//			photo_list.trimToSize();
//		}
		
	}

	@Override
	public int getCount() {
		return content_array == null ? 0 : content_array.size();

	}

	@Override
	public Note getItem(int position) {
		return (content_array == null || position > content_array.size()) ? null : content_array.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		note_item = getItem(position);
		if (note_item == null) {
			return convertView;
		}
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
			
			initView(convertView);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		addArticleList();
		
		ViewClickListener listener = new ViewClickListener(note_item);
		
		holder.zan_tv.setOnClickListener(listener);
		holder.share_tv.setOnClickListener(listener);
		holder.comment_tv.setOnClickListener(listener);
		
		return convertView;

	}
	private void initView(View convertView) {
		View view = convertView;
		
		holder.article_iv = (ImageView)view.findViewById(R.id.img_news_list_item_iv);
		holder.title_tv = (TextView) view.findViewById(R.id.title_news_list_item_tv);
		holder.content_tv = (TextView) view.findViewById(R.id.content_news_item_tv);
//		holder.ctime_tv = (TextView) view.findViewById(R.id.ctime_news_item_tv);
//		holder.img_fl = (FlowLayout) view.findViewById(R.id.img_news_list_item_fl);
		holder.zan_tv = (TextView) view.findViewById(R.id.zan_news_list_item_tv);
		holder.share_tv = (TextView) view.findViewById(R.id.share_news_list_item_tv);
		holder.comment_tv = (TextView) view.findViewById(R.id.comment_news_list_item_tv);
	}
	
	/** 添加文章列表信息 */
	private void addArticleList() {
		
			zan_count = Integer.parseInt(note_item.like_count);
//			forward_count = Integer.parseInt(note_item.re_count);
			comment_count = Integer.parseInt(note_item.comment_count);
			
//			holder.article_iv.setLayoutParams(new LayoutParams(186,140));
		    holder.article_iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageLoader.displayImage(note_item.user_icon, holder.article_iv);
			holder.title_tv.setText(note_item.title);
			holder.content_tv.setText(cutContentText(note_item.content));
//			holder.ctime_tv.setText(TimeUtil.getStandardDate(note_item.ctime));
			holder.zan_tv.setText(zan_count+"");
//			holder.forward_tv.setText(forward_count+"");
			holder.comment_tv.setText(comment_count+"");
			
			//当有图片的时候执行
//			if (!note_item.img.equals("")) {
//				holder.img_fl.setVisibility(View.VISIBLE);
//				ImageView imageView = holder.photo_list.get(0);
//				holder.img_fl.removeView(imageView);
//				
//				Log.i(TAG, "note_item info " + note_item.content);
//				Log.i(TAG, "note_item attach " + note_item.img);
//				imageView.setLayoutParams(new LayoutParams(150, 150));
//				holder.img_fl.addView(imageView);
//				imageLoader.displayImage(note_item.img,imageView);
//			}else{
//				holder.img_fl.setVisibility(View.GONE);
//				Log.i(TAG, "note_item attach " + note_item.img);
//			}
		
	}

	/**截取部分文章内容放在首页上*/
	private String cutContentText(String content) {
		String contentText = content;
		if (contentText.length() > 28) {
			contentText = contentText.substring(0, 28)+"...";
		}
		return contentText;
	}
	
	public ArrayList<Note> getAdapterData() {
		if (content_array == null) {
			content_array = new ArrayList<Note>();
		}
		return content_array;
	}
	
	/**添加第一页的数据*/
	public void addFirstPageData(ArrayList<Note> array) {
		if (array == null || array.isEmpty()) {
			return;
		}
		getAdapterData();
		content_array.clear();
		content_array.addAll(array);
		//http://www.cnblogs.com/sipher/articles/2429812.html
		content_array.trimToSize();
		notifyDataSetChanged();
	}
	
	/**添加其他页的数据*/
	public void addOtherPageData(ArrayList<Note> array) {
		if (array == null || array.isEmpty()) {
			return;
		}
		
		getAdapterData();
		content_array.addAll(array);
		content_array.trimToSize();
		notifyDataSetChanged();
		
	}

	class ViewClickListener implements OnClickListener{
		
		private IWeiboShareAPI mWeiboShareAPI = BlueApplication.mWeiboShareAPI;
		private Note note_item; 
		
		public ViewClickListener(Note item){
			note_item = item;
		}
		
		@Override
		public void onClick(View view) {
			
			Evaluate evaluate = new Evaluate(context, note_item);
			
			switch (view.getId()) {
			//赞
			case R.id.zan_news_list_item_tv:
				evaluate.zan();
				break;
			//评论
			case R.id.comment_kaxiu_list_item_tv:
				evaluate.comment();
				break;
			//分享
			case R.id.share_news_list_item_tv:
				new SharePopupWindow(context,note_item.content,mWeiboShareAPI).sharePop();
				break;

			default:
				break;
			}
		}
		
	}


}
