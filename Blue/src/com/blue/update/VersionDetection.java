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
	// �汾��Ϣ
	public static int localVersion = 0;
	public static int serverVersion = 0;

	private static RequestQueue requestQueue;
	private static String apkurlstring;
	private static String versionString;
	/** ��ȡ������������ */
	private static void getDetailsInfo(Context context) {
		requestQueue = BlueApplication.requestQueue;
		VersionRequest request = new VersionRequest(Method.POST, HttpUtils.initArticleURL("apk_url"),null, 
				new Listener<Version>() {
			
					private static final String TAG = "NewsDetailsActivity";
					public void onResponse(Version response) {
						Log.i(TAG, "��ȡ�汾��Ϣ"+response.VersionString);
						if (response != null) {
							InitDetailsData(response);
						}
					}
				}, null);

		requestQueue.add(request);
		requestQueue.start();
	}
	/**��ʼ���������鲿��*/
	private static void InitDetailsData(Version response) {
		Version version = response;
		versionString = version.VersionString;
		apkurlstring = version.ApkUrlString;
		System.out.println("��ȡ�汾��Ϣ"+versionString+"    ��ȡapk���ص�ַ��"+apkurlstring);
		}
	
	/**
	 * ��ȡapk���ص�ַ
	 * @return
	 */
	public static String getapkurlstring() {
		String ss =apkurlstring;
		return ss;
	}

	// ��ȡ����������İ汾��
	public static String check(Context context) {
		getDetailsInfo(context);
		System.out.println(" ��ȡ����������İ汾�š�versionString��    "+versionString);
		return versionString;
	}
	
	
}
