package com.blue.jpush;


import org.json.JSONException;
import org.json.JSONObject;

import com.blue.ui.news.NewsDetailsActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;


/**
 * �Զ��������
 * 
 * ������������ Receiver����
 * 1) Ĭ���û����������
 * 2) ���ղ����Զ�����Ϣ
 */
public class MyReceiver extends BroadcastReceiver {
	
	private String article_id;
	private String TITLE;
	private static final String TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		  TITLE = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//		  String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//		  String article_id = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		  String id_String = bundle.getString(JPushInterface.EXTRA_EXTRA);
		  try {
			JSONObject id_json = new JSONObject(id_String);
			article_id = id_json.optString("id");
			Log.i(TAG, "article_id = " + article_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Log.i(TAG, "���⣺"+type);
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] ����Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] ���յ������������Զ�����Ϣ: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	//processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] ���յ�����������֪ͨ");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] ���յ�����������֪ͨ��ID: " + notifactionId);
//            article_id = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//            Log.i(TAG, "article_id��"+article_id);
            
         //   processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] �û��������֪ͨ");
            
            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
           // System.out.println("EXTRA_MSG_ID:"+JPushInterface.EXTRA_MSG_ID);
            if(TITLE.equals("�����µ�����")){
            	Intent intent1 = new Intent(context,NewsDetailsActivity.class);
            	System.out.println("�����µ�����");
            	//TODO article_id == null 
            	intent1.putExtra("article_id", article_id);
            	intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	intent1.putExtra("index", 1);
            	context.startActivity(intent1);
            }
            if(TITLE.equals("�����µĵ���")){
            	Intent intent1 = new Intent(context,NewsDetailsActivity.class);
            	System.out.println("�����µĵ���");
            	intent1.putExtra("article_id", article_id);
            	intent1.putExtra("index", 1);
            	intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	context.startActivity(intent1);
            }
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] �û��յ���RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //��������� JPushInterface.EXTRA_EXTRA �����ݴ������룬������µ�Activity�� ��һ����ҳ��..
        	
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// ��ӡ���е� intent extra ����
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	//send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			 System.out.println("�Զ�����Ϣֵmessage��"+message+"  //extras:"+extras);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//						System.out.println("�Զ�����Ϣֵextras��"+extras);
//					}
//				} catch (JSONException e) {
//				}
//			}
//			context.sendBroadcast(msgIntent);
//		}
//	}
}