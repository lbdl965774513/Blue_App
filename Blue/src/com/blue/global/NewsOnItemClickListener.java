package com.blue.global;

import com.blue.bean.Note;
import com.blue.ui.news.NewsDetailsActivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author SLJM
 * @Create 2014-3-24
 *
 */
public class NewsOnItemClickListener implements OnItemClickListener{
	
	private Context context;
	/**  0: ÐÂÎÅ    1: Ìû×Ó*/
	private int index;

	public NewsOnItemClickListener(Context context,int index) {
		this.context = context;
		this.index = index;
//		Log.i("", msg)
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getItemAtPosition(position);
		Intent intent = null;
		if (obj != null && obj instanceof Note) {
			Note item = (Note) obj;
			String article_id = item.id;
			
			if(!TextUtils.isEmpty(article_id)){
				intent = new Intent(context, NewsDetailsActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("article_id",item.id);
				intent.putExtra("index", index);
			}
			if (intent != null) {
				context.startActivity(intent);
			}
		}
	
	}

}