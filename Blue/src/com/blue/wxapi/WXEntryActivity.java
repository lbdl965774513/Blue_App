package com.blue.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.app.BlueException;
import com.blue.global.Constants;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;
	private static final String TAG = "WXEntryActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
		
//		LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"  
//      + resp.errStr, 1);  

		BlueException.toast("fenxiang");
	}

	@Override
	public void onResp(BaseResp resp) {
//		LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"  
//         + resp.errStr, 1);  

		BlueException.toast(R.string.errcode_success);
		Log.i(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"+ resp.errStr);
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			// 分享成功
			BlueException.toast(R.string.errcode_success);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			// 分享取消
			BlueException.toast(R.string.errcode_cancel);
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			// 分享拒绝
			BlueException.toast(R.string.errcode_deny);
			break;
		}
		
		finish();
	}
}
