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
 * @desc 我的相册自定义布局
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
		//TODO  这个地方特别重要,第二个参数,root：如果null，则将此View作为根,此时既可以应用此View中的其他控件了。
		// 如果!null, 则将默认的layout作为View的根。  第三个参数  ture：也就将此解析的xml作为View根
		//		   false：则为默认的xml，做为根视图View
//		View view = layoutInflater.inflate(R.layout.gallery_item, null);
		View view = layoutInflater.inflate(R.layout.gallery_item, this, true);
		
		ctime_tv = (TextView)view.findViewById(R.id.ctime_gallery_item_tv);
		photo_fl = (FlowLayout)view.findViewById(R.id.photo_gallery_item_fl);
	}
	
	

}
