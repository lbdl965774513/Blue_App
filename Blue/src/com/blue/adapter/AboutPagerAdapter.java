package com.blue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blue.ui.about.CaseFragment;
import com.blue.ui.about.ConnectionFragment;
import com.blue.ui.about.IntroductionFragment;

/**
 * @author SLJM
 * @deprecated ��������
 *   
 */
public class AboutPagerAdapter extends FragmentStatePagerAdapter  {
	
	private int viewpagerId;
	private FragmentManager fm;
	private final String[] TITLES = {"��˾���","��������","�ṩ�ķ���","���ǵ��Ŷ�","��ϵ����"};
	public AboutPagerAdapter(FragmentManager fm , int viewpagerId) {
		super(fm);
		this.fm = fm;
		this.viewpagerId = viewpagerId;
	}
	 @Override  
     public CharSequence getPageTitle(int position) {  
         return TITLES[position];  
     }
	 
	@Override
	public Fragment getItem(int position) {
		String name = makeFragmentName(position);
		Fragment frag = fm.findFragmentByTag(name);
		if (frag == null) {
			switch (position) {
			//��˾���
			case 0:
				frag = IntroductionFragment.getInstance(0);
				break;
			//��������
			case 1:
				frag = CaseFragment.getInstance(1);
				break;
			//�ṩ�ķ���
			case 2:
				frag = CaseFragment.getInstance(2);
				break;
			//���ǵ��Ŷ�
			case 3:
				frag = IntroductionFragment.getInstance(3);
				break;
			//��ϵ����
			case 4:
				frag = ConnectionFragment.getInstance();
				break;
			
			}
		}
		return frag;
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}
	
	private String makeFragmentName(int index) {
		return "android:switcher:" + viewpagerId + ":" + index;
	}

}
