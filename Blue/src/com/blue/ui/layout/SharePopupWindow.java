package com.blue.ui.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.blue.R;
import com.blue.app.BlueException;
import com.blue.ui.share.SinaShare;
import com.blue.ui.share.WeiXinShare;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

/**
 * @author SLJM
 * @create 2014-5-13
 * @desc 底部popupWindow
 * 
 */
public class SharePopupWindow implements OnItemClickListener, OnClickListener {

	private Context context;
	
	private View view;
	private GridView share_gv;
	private Button cancel_btn;
	
	private String content;
	private IWeiboShareAPI mWeiboShareAPI;
	private PopupWindow popupWindow;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	public SharePopupWindow(Context context,String content,IWeiboShareAPI mWeiboShareAPI) {

		this.context = context;
		this.content = content;
		this.mWeiboShareAPI = mWeiboShareAPI;
	}

	@SuppressWarnings("deprecation")
	public void sharePop() {

		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.share_popup_window, null);

		cancel_btn = (Button) view
				.findViewById(R.id.cancel_share_popup_window_btn);
		share_gv = (GridView) view.findViewById(R.id.share_popup_window_gv);

		initAdapter();
		share_gv.setAdapter(simpleAdapter);

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, false);
		// 需要设置一下此参数，点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		popupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		popupWindow.setFocusable(true);
		popupWindow.update();
		popupWindow.setHeight(270);
		popupWindow.showAtLocation(view, Gravity.BOTTOM,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// popupWindow.showAsDropDown(view);

		cancel_btn.setOnClickListener(this);
		share_gv.setOnItemClickListener(this);

	}

	private void initAdapter() {
		initMap();

		simpleAdapter = new SimpleAdapter(context, list,
				R.layout.share_popup_window_item,
				new String[] { "icon", "text" }, new int[] {
						R.id.logo_share_item_iv, R.id.text_share_item_tv });

	}

	private void initMap() {

		HashMap<String, Object> item_map;
		String[] text = context.getResources().getStringArray(R.array.text_share);
		int[] icon = { R.drawable.friend_share, R.drawable.community_share,
				R.drawable.sms_share, R.drawable.sina_share };

		for (int i = 0; i < icon.length; i++) {
			item_map = new HashMap<String, Object>();

			item_map.put("icon", icon[i]);
			item_map.put("text", text[i]);

			list.add(item_map);
		}

	}
	
	private void sendSMS() {
		Uri uri = Uri.parse("smsto:");             
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);             
		it.putExtra("sms_body", content);             
		context.startActivity(it);
	}
	
	private TextObject getTextObj(){
		TextObject textObject = new TextObject();
		textObject.text = content;
		return textObject;
	}
	
	private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.logo);
        imageObject.setImageObject(bitmapDrawable.getBitmap());
        return imageObject;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel_share_popup_window_btn:
			BlueException.toast(R.string.cancel);
			popupWindow.dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		WeiXinShare weiXinShare = new WeiXinShare();
		SinaShare sinaShare = new SinaShare(context,getTextObj(),getImageObj(),mWeiboShareAPI);
		switch (position) {
		//分享到微信好友
		case 0:
			weiXinShare.wechatShare(0, context,content);

			break;
		//分享到微信朋友圈
		case 1:
			weiXinShare.wechatShare(1, context,content);

			break;
		//分享到短信
		case 2:
			sendSMS();
			break;
		//分享到新浪微博
		case 3:
			sinaShare.share();
			break;
						

		default:
			break;
		}
	}

}
