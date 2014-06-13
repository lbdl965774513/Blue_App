package com.blue.ui.share;

import android.content.Context;

import com.blue.app.BlueException;
import com.blue.global.Constants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.exception.WeiboShareException;

public class SinaShare {
	
	private Context context;
	private ImageObject imageObject;
	private TextObject textObject;
	private IWeiboShareAPI mWeiboShareAPI;
	public SinaShare(Context context,TextObject textObject,ImageObject imageObject,IWeiboShareAPI mWeiboShareAPI){
		this.context = context;
		this.textObject = textObject;
		this.imageObject = imageObject;
		this.mWeiboShareAPI = mWeiboShareAPI;
		
	}
	
	public void share(){
		
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Constants.APP_KEY);
		mWeiboShareAPI.registerApp(); //  ��Ӧ��ע�ᵽ΢���ͻ���
		try {
            // ���΢���ͻ��˻����Ƿ����������δ��װ΢���������Ի���ѯ���û�����΢���ͻ���
            if (mWeiboShareAPI.checkEnvironment(true)) {                    
                sendMessage();
            }
        } catch (WeiboShareException e) {
            e.printStackTrace();
            BlueException.toast(e.getMessage());
        }
	}
	private void sendMessage() {
		if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
//            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
//            if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                sendMultiMessage();
//            }
        }
//		else {
//            Toast.makeText(this, R.string.weibosdk_demo_not_support_api_hint, Toast.LENGTH_SHORT).show();
//        }
	}

	public void sendMultiMessage() {
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();// ��ʼ��΢���ķ�����Ϣ
		
		weiboMessage.textObject = textObject;
		weiboMessage.imageObject = imageObject;
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		mWeiboShareAPI.sendRequest(request); // ����������Ϣ��΢��������΢���������
	}
}
