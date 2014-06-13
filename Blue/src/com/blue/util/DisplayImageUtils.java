package com.blue.util;

import com.blue.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class DisplayImageUtils {
	public static DisplayImageOptions displayImage() {

		DisplayImageOptions imageOptions;
		imageOptions = new DisplayImageOptions.Builder()
		// ����ͼƬ���ص�ʱ����ʾ��ͼƬ
				.showImageOnLoading(R.drawable.ic_launcher)
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.ic_launcher)
				// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheInMemory(true)
				// �������ص�ͼƬ�Ƿ񻺴���SD����
				.cacheOnDisc(true).considerExifParams(true).build();
		return imageOptions;

	}
}
