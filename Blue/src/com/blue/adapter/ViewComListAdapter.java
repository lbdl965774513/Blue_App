//package com.blue.adapter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import com.blue.activity.R;
//
///**
// * @author SLJM
// * @create 2014-1-22
// * @version 1.0
// * @desc 查看所有评论适配器
// */
//
//public class ViewComListAdapter extends BaseAdapter implements OnClickListener {
//
//	private Context context;
//	private List<HashMap<String, String>> list;
//	private ViewHolder holder;
//
//	public ViewComListAdapter(Context context, ArrayList<HashMap<String, String>> list) {
//		this.context = context;
//		this.list = list;
//	}
//
//	private static class ViewHolder {
//		ImageView touxiangImg;//这张图片是在界面布局上写死的没有在适配器上获取
//		TextView unameTv;
//		RatingBar gradeRb;
//		TextView contentTv;
//		TextView timeTv;
//		Button badBtn;
//		Button goodBtn;
//	}
//
//	@Override
//	public int getCount() {
//		return list.size();
//
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return null;
//
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return 0;
//
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.view_comment_list_item, null);
//			holder.touxiangImg = (ImageView)convertView.findViewById(R.id.touxiangImg);
//			holder.unameTv = (TextView) convertView.findViewById(R.id.uname);
//			holder.gradeRb = (RatingBar) convertView.findViewById(R.id.review_grade);
//			holder.contentTv = (TextView) convertView.findViewById(R.id.content);
//			holder.timeTv = (TextView) convertView.findViewById(R.id.time);
//			holder.badBtn = (Button) convertView.findViewById(R.id.bad);
//			holder.goodBtn = (Button) convertView.findViewById(R.id.good);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		String name = list.get(position).get("name");
//		float grade = (float) 3.5;
//		String content = list.get(position).get("content");
//		String time = list.get(position).get("time");
//		String good = list.get(position).get("good");
//		String bad = list.get(position).get("bad");
//		holder.unameTv.setText(name);
//		holder.gradeRb.setRating(grade);
//		holder.contentTv.setText(content);
//		holder.timeTv.setText(time);
//		holder.goodBtn.setText(good);
//		holder.badBtn.setText(bad);
//
//		holder.goodBtn.setTag(position);
//		holder.badBtn.setTag(position);
//		holder.goodBtn.setOnClickListener(this);
//		holder.badBtn.setOnClickListener(this);
//		return convertView;
//
//	}
//
//	@Override
//	public void onClick(View v) {
//	}
//
//}
