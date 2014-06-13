package com.blue.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.blue.R;

/**
 * @author SLJM
 * @create 2014-1-24
 * @version 1.0
 * @desc Ã˚◊”œÍ«È  ≈‰∆˜
 */

public class PostDetailsAdapter extends BaseAdapter {

	private List<HashMap<String, String>> list;
	private Context context;
	private ViewHolder holder;

	public PostDetailsAdapter(Context context, ArrayList<HashMap<String, String>> list) {
		this.context = context;
		this.list = list;
	}

	private static class ViewHolder {
		ImageView list_img;
		TextView list_name;
		TextView list_content;
		TextView list_beforeTime;
		Button list_reply;
	}

	@Override
	public int getCount() {
		return list.size();

	}

	@Override
	public Object getItem(int position) {
		return null;

	}

	@Override
	public long getItemId(int position) {
		return 0;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.post_details_list_item, null);
//			holder.list_img = (ImageView)convertView.findViewById(R.id.list_img);
//			holder.list_name = (TextView) convertView.findViewById(R.id.list_name);
//			holder.list_content = (TextView)convertView.findViewById(R.id.list_content);
//			holder.list_beforeTime = (TextView)convertView.findViewById(R.id.list_beforeTime);
//			holder.list_reply = (Button)convertView.findViewById(R.id.list_reply);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String name = list.get(position).get("name");
		String content = list.get(position).get("content");
		String beforeTime = list.get(position).get("time");
		
		holder.list_name.setText(name);
		holder.list_content.setText(content);
		holder.list_beforeTime.setText(beforeTime);

		return convertView;

	}

}