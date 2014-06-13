package com.blue.adapter;


import com.blue.R;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * �û�����Adapter��
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-8-9
 */
public class GridViewFaceAdapter extends BaseAdapter {
	// ����Context
	private Context mContext;
	// ������������ ��ͼƬԴ
	private static int[] mImageIds = new int[] {
			 R.drawable.f001,R.drawable.f002,R.drawable.f003,R.drawable.f004,R.drawable.f005,R.drawable.f006,
			 R.drawable.f007,R.drawable.f008,R.drawable.f009,R.drawable.f010,R.drawable.f011,R.drawable.f012,
			 R.drawable.f013,R.drawable.f014,R.drawable.f015,R.drawable.f016,R.drawable.f017,R.drawable.f018,
			 R.drawable.f019,R.drawable.f020,R.drawable.f021,R.drawable.f022,R.drawable.f023,R.drawable.f024,
			 R.drawable.f025,R.drawable.f026,R.drawable.f027,R.drawable.f028,R.drawable.f029,R.drawable.f030,
			 R.drawable.f031,R.drawable.f032,R.drawable.f033,R.drawable.f034,R.drawable.f035,R.drawable.f036,
			 R.drawable.f037,R.drawable.f038,R.drawable.f039,R.drawable.f040,R.drawable.f041,R.drawable.f042,
			 R.drawable.f043,R.drawable.f044,R.drawable.f045,R.drawable.f046,R.drawable.f047,R.drawable.f048,
			 R.drawable.f049,R.drawable.f050,R.drawable.f051,R.drawable.f052,R.drawable.f053,R.drawable.f054,
			  R.drawable.f055,R.drawable.f056,R.drawable.f057,R.drawable.f058,R.drawable.f059,R.drawable.f060,
			 R.drawable.f061,R.drawable.f062,R.drawable.f063,R.drawable.f064,R.drawable.f065,R.drawable.f067,
			 R.drawable.f068,R.drawable.f069
		};

	public static int[] getImageIds() {
		return mImageIds;
	}

	public GridViewFaceAdapter(Context c) {
		mContext = c;
	}

	// ��ȡͼƬ�ĸ���
	public int getCount() {
		return mImageIds.length;
	}

	// ��ȡͼƬ�ڿ��е�λ��
	public Object getItem(int position) {
		return position;
	}

	// ��ȡͼƬID
	public long getItemId(int position) {
		return mImageIds[position];
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			// ����ͼƬn��n��ʾ
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// ������ʾ��������
			imageView.setScaleType(ImageView.ScaleType.CENTER);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(mImageIds[position]);
		final Resources res = this.mContext.getResources();
		String arr[] = res.getStringArray(R.array.facename);
		// String q = ;
		imageView.setTag(arr[position]);
		return imageView;
	}
}