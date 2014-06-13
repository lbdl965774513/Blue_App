package com.blue.ui.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blue.R;
import com.blue.global.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

/**
 * ΢�ŷ��� ��������ṩһ��������ҳ��ʾ����������ο�����ʾ�����룩
 * 
 * @param flag
 *            (0:����΢�ź��ѣ�1������΢������Ȧ)
 */
public class WeiXinShare {

	private IWXAPI wxApi;
	
	public void wechatShare(int flag, Context context,String content) {
		// ʵ����
		wxApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
		wxApi.registerApp(Constants.APP_ID);
		
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.thinklancer.com/";
		
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = content;
		msg.description = "������д����";
		
		// �����滻һ���Լ��������ͼƬ��Դ
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),R.drawable.logo);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 50, 50, true);
		thumb.recycle();

		msg.setThumbImage(thumbBmp);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		
		wxApi.sendReq(req);
	}
}

// ����Ҫ����ĵط���Ӵ��룺
// wechatShare(0);//����΢�ź���
// wechatShare(1);//����΢������Ȧ
