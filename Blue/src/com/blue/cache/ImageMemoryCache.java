package com.blue.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class ImageMemoryCache {
	/**
	 * ���ڴ��ȡ�����ٶ������ģ�Ϊ�˸����޶�ʹ���ڴ棬����ʹ�������㻺�档 ǿ���û��治�����ױ����գ��������泣�����ݣ������õ�ת�������û��档
	 */
	private static final String TAG = "ImageMemoryCache";

	private static LruCache<String, Bitmap> mLruCache; // ǿ���û���

	private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // �����û���

	private static final int LRU_CACHE_SIZE = 4 * 1024 * 1024; // ǿ���û���������4MB

	private static final int SOFT_CACHE_NUM = 20; // �����û������

	// ������ֱ��ʼ��ǿ���û���������û���
	public ImageMemoryCache() {
		mLruCache = new LruCache<String, Bitmap>(LRU_CACHE_SIZE) {
			@Override
			// sizeOf����Ϊ����hashmap value�Ĵ�С
			protected int sizeOf(String key, Bitmap value) {
				if (value != null)
					return value.getRowBytes() * value.getHeight();
				else
					return 0;
			}
			
			@Override
			protected void entryRemoved(boolean evicted, String key,Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null) {
					// ǿ���û�����������ʱ�򣬻����LRU�㷨�����û�б�ʹ�õ�ͼƬת��������û���
					Log.d(TAG, "LruCache is full,move to SoftRefernceCache");
					mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
				}
			}
		};

		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_NUM, 0.75f, true) {
			private static final long serialVersionUID = 1L;

			/**
			 * ����������������20��ʱ����ɵ������ý��ᱻ����ʽ��ϣ�����Ƴ�
			 */
			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				if (size() > SOFT_CACHE_NUM) {
					Log.d(TAG, "should remove the eldest from SoftReference");
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * �ӻ����л�ȡͼƬ
	 */
	public Bitmap getBitmapFromMemory(String url) {
		Bitmap bitmap;

		// �ȴ�ǿ���û����л�ȡ
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				// ����ҵ��Ļ�����Ԫ���Ƶ�LinkedHashMap����ǰ�棬�Ӷ���֤��LRU�㷨�������ɾ��
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				Log.d(TAG, "get bmp from LruCache,url=" + url);
				return bitmap;
			}
		}

		// ���ǿ���û������Ҳ������������û������ң��ҵ���Ͱ��������������Ƶ�ǿ���û�����
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					// ��ͼƬ�ƻ�LruCache
					mLruCache.put(url, bitmap);
					mSoftCache.remove(url);
					Log.d(TAG, "get bmp from SoftReferenceCache, url=" + url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}
		return null;
	}

	/**
	 * ���ͼƬ������
	 */
	public void addBitmapToMemory(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}

	public void clearCache() {
		mSoftCache.clear();
	}
}