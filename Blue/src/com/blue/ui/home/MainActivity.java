package com.blue.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.blue.R;
import com.blue.app.BlueException;
import com.blue.ui.BaseActivity;
import com.blue.ui.fragment.AboutFragment;
import com.blue.ui.fragment.HomeFragment;
import com.blue.ui.fragment.NewsListFragment;

/**
 * 程序导航页
 * 
 * @author SLJM
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	/**
	 * 用于展示主页的Fragment
	 */
	private HomeFragment homeFragment;

	/**
	 * 用于展示公司新闻的Fragment
	 */
	private NewsListFragment newsFragment;

	/**
	 * 用于展示大咖秀的Fragment
	 */
	private NewsListFragment xaXiuFragment;

	/**
	 * 用于展示关于深蓝的Fragment
	 */
	private AboutFragment aboutFragment;

	/**
	 * 首页界面布局
	 */
	private View mainLayout;

	/**
	 * 公司新闻界面布局
	 */
	private View newsLayout;

	/**
	 * 大咖秀界面布局
	 */
	private View communityLayout;

	/**
	 * 关于深蓝界面布局
	 */
	private View aboutLayout;

	/**
	 * 在Tab布局上显示主页面图标的控件
	 */
	private ImageView homeImage;

	/**
	 * 在Tab布局上显示公司新闻图标的控件
	 */
	private ImageView newsImage;

	/**
	 * 在Tab布局上显示大咖秀图标的控件
	 */
	private ImageView communityImage;

	/**
	 * 在Tab布局上显示关于我们图标的控件
	 */
	private ImageView aboutImage;

	/**
	 * 在Tab布局上显示主页面标题的控件
	 */
	private TextView homeText;

	/**
	 * 在Tab布局上显示公司新闻标题的控件
	 */
	private TextView newsText;

	/**
	 * 在Tab布局上显示大咖秀标题的控件
	 */
	private TextView communityText;

	/**
	 * 在Tab布局上显示关于我们标题的控件
	 */
	private TextView aboutText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	
	private static final String TAG  = "MainActivity";
	
	//actionbar的管理,0:首页,公司新闻,关于深蓝  1:大咖秀
	int index = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			setContentView(R.layout.main);
	
		// 初始化布局元素
		initViews();
		
		fragmentManager = getSupportFragmentManager();
		index = 0;
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		
	}

	private void initViews() {
		mainLayout = findViewById(R.id.home_layout);
		newsLayout = findViewById(R.id.news_layout);
		communityLayout = findViewById(R.id.kaxiu_layout);
		aboutLayout = findViewById(R.id.about_layout);

		homeImage = (ImageView) findViewById(R.id.home_main_iv);
		newsImage = (ImageView) findViewById(R.id.news_main_iv);
		communityImage = (ImageView) findViewById(R.id.community_main_iv);
		aboutImage = (ImageView) findViewById(R.id.about_main_iv);

		homeText = (TextView) findViewById(R.id.home_main_tv);
		newsText = (TextView) findViewById(R.id.news_main_tv);
		communityText = (TextView) findViewById(R.id.community_main_tv);
		aboutText = (TextView) findViewById(R.id.about_main_tv);

		mainLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		communityLayout.setOnClickListener(this);
		aboutLayout.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout:
			index = 0;
			// 当点击了首页tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.news_layout:
			index = 0;
			// 当点击了公司新闻tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.kaxiu_layout:
			index = 1;
			// 当点击了大咖秀tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.about_layout:
			index = 0;
			// 当点击了关于深蓝tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param i
	 *            每个tab页对应的下标。0表示首页，1表示公司新闻，2表示大咖秀，3表示关于深蓝。
	 */
	private void setTabSelection(int i) {
		
		// 每次选中之前先清除掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (i) {
		case 0:
			// 当点击了首页tab时，改变控件的图片和文字颜色
			homeImage.setImageResource(R.drawable.news_selected);
			homeText.setTextColor(getResources().getColor(R.color.blue));
			if (homeFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				homeFragment = new HomeFragment().getInstance();
				transaction.add(R.id.content_main_fl,homeFragment);
				
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(homeFragment);
			}
			break;
		case 1:
			
			Log.i(TAG, "1");
			// 当点击了公司新闻tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.news_selected);
			newsText.setTextColor(getResources().getColor(R.color.blue));
			if (newsFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				newsFragment = NewsListFragment.getInstance(0);
				transaction.add(R.id.content_main_fl, newsFragment);
				
			} else {
				
				newsFragment.setIndex(0);
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(newsFragment);
			}
			break;
		case 2:
			// 当点击了大咖秀tab时，改变控件的图片和文字颜色
			communityImage.setImageResource(R.drawable.dakaxiu_selected);
			communityText.setTextColor(getResources().getColor(R.color.blue));
			if (xaXiuFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				xaXiuFragment = NewsListFragment.getInstance(1);
				transaction.add(R.id.content_main_fl, xaXiuFragment);
				
			} else {
				newsFragment.setIndex(1);
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(xaXiuFragment);
			}
			break;
		case 3:
		default:
			// 当点击了关于深蓝tab时，改变控件的图片和文字颜色
			aboutImage.setImageResource(R.drawable.about_selected);
			aboutText.setTextColor(getResources().getColor(R.color.blue));
			if (aboutFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				aboutFragment = new AboutFragment(getSupportFragmentManager());
				transaction.add(R.id.content_main_fl, aboutFragment);
				
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(aboutFragment);
			}
			break;
		}
		transaction.commit();
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		homeImage.setImageResource(R.drawable.news_unselected);
		homeText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setImageResource(R.drawable.news_unselected);
		newsText.setTextColor(Color.parseColor("#82858b"));
		communityImage.setImageResource(R.drawable.dakaxiu_unselected);
		communityText.setTextColor(Color.parseColor("#82858b"));
		aboutImage.setImageResource(R.drawable.about_unselected);
		aboutText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
		if (xaXiuFragment != null) {
			transaction.hide(xaXiuFragment);
		}
		if (aboutFragment != null) {
			transaction.hide(aboutFragment);
		}
	}
	/**
	 * 退出程序
	 * 
	 */
	private long mExitTime;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				BlueException.toast("再按一次退出程序");
				mExitTime = System.currentTimeMillis();
				
			}else {
				finish();
			}
			
		}
		return true;
	}
	
	

}