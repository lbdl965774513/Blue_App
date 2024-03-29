package com.blue.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blue.R;
import com.blue.adapter.AboutPagerAdapter;
import com.blue.app.BlueApplication;
import com.blue.lib.view.PagerSlidingTabStrip;
import com.blue.ui.user.UserCenterActivity;
import com.blue.util.LoginUtils;

/**
 * @author SLJM
 * @create 2014-3-21
 * @version 1.0
 * @desc 关于深蓝
 */

@SuppressLint("ValidFragment")
public class AboutFragment extends Fragment implements OnClickListener {

	private static FragmentManager fm;
	private ImageButton post_ib,user_center_ib;
	private TextView title_tv;
	private PagerSlidingTabStrip about_psts;
	private ViewPager about_vp;
	private AboutPagerAdapter aboutPagerAdapter;

	public AboutFragment() {
		super();
	}
	
	public AboutFragment(FragmentManager fm){
		this.fm = fm;
	}
	
	public static AboutFragment getIntance (){
		
		AboutFragment aboutFragment = new AboutFragment();
		
		return aboutFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// position = getArguments().getInt(POSITION);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View aboutView = inflater.inflate(R.layout.about, container, false);

		initView(aboutView, inflater);

		return aboutView;
	}

	// 初始化布局元素
	private void initView(View contentView, LayoutInflater inflater) {
		
		
		about_psts = (PagerSlidingTabStrip) contentView.findViewById(R.id.about_psts);
		about_vp = (ViewPager) contentView.findViewById(R.id.about_vp);
		//actionbar
		post_ib = (ImageButton) contentView.findViewById(R.id.post_actionbar_ib);
		title_tv = (TextView) contentView.findViewById(R.id.title_actionbar_tv);
		user_center_ib = (ImageButton) contentView.findViewById(R.id.user_actionbar_ib);
		
		post_ib.setVisibility(View.GONE);
		title_tv.setText("关于深蓝");
		user_center_ib.setOnClickListener(this);

		aboutPagerAdapter = new AboutPagerAdapter(fm, R.id.about_vp);

		about_vp.setAdapter(aboutPagerAdapter);
		about_psts.setViewPager(about_vp);

		about_psts.setIndicatorColor(getResources().getColor(R.color.blue));
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.user_actionbar_ib:
			if(BlueApplication.isLogin(getActivity())){
				intent.setClass(getActivity(), UserCenterActivity.class);
				intent.putExtra("user_id", LoginUtils.getLoginUserid(getActivity()));
				startActivity(intent);
				
			}
			break;

		}
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putInt("currentColor", getResources().getColor(R.color.blue));
	// }

	// @Override
	// public void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// currentColor = savedInstanceState.getInt("currentColor");
	// }

}
