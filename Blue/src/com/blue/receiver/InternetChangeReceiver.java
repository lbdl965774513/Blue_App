package com.blue.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author SLJM
 * @create 2014-4-10
 * @desc 监听网络连接状态的改变
 * 
 */
public class InternetChangeReceiver extends BroadcastReceiver {
	String packnameString = null;
//	private static final String TAG = "InternetListener";

	@Override
	public void onReceive(Context context, Intent intent) {
		packnameString = context.getPackageName();

		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
			// unconnect network
//			Toast.makeText(context, "unconnect network", Toast.LENGTH_LONG).show();
		} else {
			// connect network
//			Toast.makeText(context, "connect network", Toast.LENGTH_LONG).show();
		}
	}
}
