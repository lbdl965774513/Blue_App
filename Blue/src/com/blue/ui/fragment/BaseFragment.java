package com.blue.ui.fragment;

import com.blue.R;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.ListFragment;

/**
 * @author SLJM
 * @create 2014-1-21
 * @version 1.0
 * @desc fragment的基类
 */

// XXX 这个类也可以做很多事，框架已留。自由发挥
public abstract class BaseFragment extends ListFragment {
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.common_right_in, R.anim.common_zoom_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.common_right_in, R.anim.common_zoom_out);
	}
}
