package com.blue.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blue.R;
import com.blue.bean.Note;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author SLJM
 * @create 2014-5-4
 * @desc 开发案例适配器
 * 
 */
public class CaseAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Note> note_array;
	private ImageLoader imageLoader;
	private Note note_item;
	private ViewHolder holder;

	public CaseAdapter(Context context, ArrayList<Note> note_array) {
		this.context = context;
		this.note_array = note_array;
		imageLoader = ImageLoader.getInstance();
	}

	private static class ViewHolder {
		TextView title;
		TextView content;
		TextView share;
		ImageView image;

	}

	@Override
	public int getCount() {
		return note_array == null ? 0 : note_array.size();

	}

	@Override
	public Note getItem(int position) {
		return (note_array == null || position > note_array.size()) ? null: note_array.get(position);

	}

	@Override
	public long getItemId(int position) {
		return 0;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		note_item = note_array.get(position);

		if (note_item == null) {
			return convertView;
		}

		if (convertView == null) {

			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.case_items_about, null);

			holder.title = (TextView) convertView.findViewById(R.id.title_case_about_tv);
			holder.content = (TextView) convertView.findViewById(R.id.content_case_about_tv);
			holder.share = (TextView) convertView.findViewById(R.id.share_case_about_tv);
			holder.image = (ImageView) convertView.findViewById(R.id.img_case_about_iv);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		addCaseList();
		return convertView;

	}
	//TODO
	private void addCaseList() {
		holder.title.setText(note_item.title);
		holder.content.setText(note_item.content);
		
	}
	
	public ArrayList<Note> getAdapterData() {
		if (note_array == null) {
			note_array = new ArrayList<Note>();
		}
		return note_array;
	}

	/** 添加第一页的数据 */
	public void addFirstPageData(ArrayList<Note> array) {
		if (array == null || array.isEmpty()) {
			return;
		}
		getAdapterData();
		note_array.clear();
		note_array.addAll(array);
		// http://www.cnblogs.com/sipher/articles/2429812.html
		note_array.trimToSize();
		notifyDataSetChanged();
	}

	/** 添加其他页的数据 */
	public void addOtherPageData(ArrayList<Note> array) {
		if (array == null || array.isEmpty()) {
			return;
		}

		getAdapterData();
		note_array.addAll(array);
		note_array.trimToSize();
		notifyDataSetChanged();

	}
}
