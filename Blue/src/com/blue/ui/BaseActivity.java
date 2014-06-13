package com.blue.ui;

import com.blue.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author SLJM
 * @create 2014-1-21
 * @version 1.0
 * @desc 基础Activity，作为所有Activity的父类
 */

// XXX 这个类可以做很多事，框架已留。自由发挥
public abstract class BaseActivity extends FragmentActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.common_right_in,
				R.anim.common_zoom_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.common_right_in,
				R.anim.common_zoom_out);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.common_zoom_in,R.anim.common_right_out);
	}

}
