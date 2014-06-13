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
 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
 * 
 * @param flag
 *            (0:分享到微信好友，1：分享到微信朋友圈)
 */
public class WeiXinShare {

	private IWXAPI wxApi;
	
	public void wechatShare(int flag, Context context,String content) {
		// 实例化
		wxApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
		wxApi.registerApp(Constants.APP_ID);
		
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.thinklancer.com/";
		
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = content;
		msg.description = "这里填写内容";
		
		// 这里替换一张自己工程里的图片资源
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

// 在需要分享的地方添加代码：
// wechatShare(0);//分享到微信好友
// wechatShare(1);//分享到微信朋友圈
