package com.blue.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blue.R;



/**
 * @author SLJM
 * @desc actionbar自定义布局
 * @create 2014-6-13
 *
 */
public class ActionBarLayout extends RelativeLayout {
	
	private Context mContext;
	
	private ImageButton return_ib;
	private ImageButton userCenter_ib;
	private TextView title_tv;
	
	public ActionBarLayout(Context context){
		super(context);
		mContext = context;
	}

	public ActionBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}
	
	public ImageButton getLeftView(){
		return return_ib;
	}
	public ImageButton getRightView(){
		return userCenter_ib;
	}
	
	public void setTitleText(String title){
		title_tv.setText(title);
	}
	
	public void setLeftImage(int id){
		return_ib.setImageResource(id);
	}
	
	public void setRightImage(int id){
		userCenter_ib.setImageResource(id);
	}
	public void setLeftVisibility(int isVisibility){
		
		return_ib.setVisibility(isVisibility == 0 ? View.INVISIBLE : View.VISIBLE);
	}
	public void setRightVisibility(int isVisibility){
		
		userCenter_ib.setVisibility(isVisibility == 0 ? View.INVISIBLE : View.VISIBLE);
	}

	private void initView() {
		
		Log.i("ActionBar", "mContext = " + mContext);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		
		Log.i("ActionBar", "mContext = " + mContext);
		View view = inflater.inflate(R.layout.actionbar, this,true);
		Log.i("ActionBar", "View = " + view);
		
		return_ib = (ImageButton) view.findViewById(R.id.post_actionbar_ib);
		userCenter_ib = (ImageButton) view.findViewById(R.id.user_actionbar_ib);
		title_tv = (TextView) view.findViewById(R.id.title_actionbar_tv);
		
	}

}
