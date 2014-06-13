package com.blue.update;

import com.blue.app.BlueApplication;
import com.blue.bean.Version;
import com.blue.conninternet.VersionRequest;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.Response.Listener;
import com.blue.util.HttpUtils;

import android.content.Context;
import android.util.Log;
public class VersionDetection {
	// 版本信息
	public static int localVersion = 0;
	public static int serverVersion = 0;

	private static RequestQueue requestQueue;
	private static String apkurlstring;
	private static String versionString;
	/** 获取文章详情数据 */
	private static void getDetailsInfo(Context context) {
		requestQueue = BlueApplication.requestQueue;
		VersionRequest request = new VersionRequest(Method.POST, HttpUtils.initArticleURL("apk_url"),null, 
				new Listener<Version>() {
			
					private static final String TAG = "NewsDetailsActivity";
					public void onResponse(Version response) {
						Log.i(TAG, "获取版本信息"+response.VersionString);
						if (response != null) {
							InitDetailsData(response);
						}
					}
				}, null);

		requestQueue.add(request);
		requestQueue.start();
	}
	/**初始化文章详情部分*/
	private static void InitDetailsData(Version response) {
		Version version = response;
		versionString = version.VersionString;
		apkurlstring = version.ApkUrlString;
		System.out.println("获取版本信息"+versionString+"    获取apk下载地址："+apkurlstring);
		}
	
	/**
	 * 获取apk下载地址
	 * @return
	 */
	public static String getapkurlstring() {
		String ss =apkurlstring;
		return ss;
	}

	// 获取服务器程序的版本号
	public static String check(Context context) {
		getDetailsInfo(context);
		System.out.println(" 获取服务器程序的版本号、versionString：    "+versionString);
		return versionString;
	}
	
	
}
