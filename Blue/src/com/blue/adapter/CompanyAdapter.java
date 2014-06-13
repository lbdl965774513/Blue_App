package com.blue.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blue.R;

/**
 * @author SLJM
 * @create 2014-1-21
 * @version 1.0
 * @desc 用户中心
 */

public class CompanyAdapter extends BaseAdapter {

	private Context context;
	private List<HashMap<String, String>> comlist;
	private ViewHolder holder;

	public CompanyAdapter(Activity activity, ArrayList<HashMap<String, String>> comlist) {
		this.context = activity;
		this.comlist = comlist;
	}

	private static class ViewHolder {
		TextView item_name;
	}

	@Override
	public int getCount() {
		return comlist.size();

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
			convertView = LayoutInflater.from(context).inflate(R.layout.data_user_center_item, null);
			holder.item_name = (TextView) convertView.findViewById(R.id.item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String nameString = comlist.get(position).get("name");
		holder.item_name.setText(nameString);
		return convertView;

	}

}
