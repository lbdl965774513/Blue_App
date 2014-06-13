package com.blue.util;

import com.blue.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class DisplayImageUtils {
	public static DisplayImageOptions displayImage() {

		DisplayImageOptions imageOptions;
		imageOptions = new DisplayImageOptions.Builder()
		// 设置图片加载的时候显示的图片
				.showImageOnLoading(R.drawable.ic_launcher)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)
				// 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).considerExifParams(true).build();
		return imageOptions;

	}
}
