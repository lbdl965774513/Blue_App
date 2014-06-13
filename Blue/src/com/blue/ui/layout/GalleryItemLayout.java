package com.blue.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blue.R;
import com.blue.lib.view.FlowLayout;

/**
 * @author SLJM
 * @create 2014-5-26
 * @desc �ҵ�����Զ��岼��
 *
 */
public class GalleryItemLayout extends RelativeLayout{
	
	private Context context;
	
	private TextView ctime_tv;
	private FlowLayout photo_fl;
	
	public GalleryItemLayout(Context context){
		super(context);
		this.context = context;
		initView();
	}

	public GalleryItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		
		initView();
		
	}

	public TextView getTextView(){
		return ctime_tv;
	}
	public FlowLayout getFlowLayout(){
		return photo_fl;
	}
	
	private void initView() {
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		//TODO  ����ط��ر���Ҫ,�ڶ�������,root�����null���򽫴�View��Ϊ��,��ʱ�ȿ���Ӧ�ô�View�е������ؼ��ˡ�
		// ���!null, ��Ĭ�ϵ�layout��ΪView�ĸ���  ����������  ture��Ҳ�ͽ��˽�����xml��ΪView��
		//		   false����ΪĬ�ϵ�xml����Ϊ����ͼView
//		View view = layoutInflater.inflate(R.layout.gallery_item, null);
		View view = layoutInflater.inflate(R.layout.gallery_item, this, true);
		
		ctime_tv = (TextView)view.findViewById(R.id.ctime_gallery_item_tv);
		photo_fl = (FlowLayout)view.findViewById(R.id.photo_gallery_item_fl);
	}
	
	

}
