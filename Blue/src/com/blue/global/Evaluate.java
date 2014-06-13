package com.blue.global;

import java.util.Map;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blue.adapter.KaXiuListAdapter;
import com.blue.app.BlueApplication;
import com.blue.app.BlueException;
import com.blue.bean.Note;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.RequestQueue;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.toolbox.JsonObjectRequest;
import com.blue.ui.news.NewsCommentActivity;
import com.blue.util.HttpUtils;
import com.blue.util.LoginUtils;
import com.blue.util.SubmitPara;


/**
 * @author SLJM
 * @create 2014-4-28
 * @desc ��  ת��  ���� 
 */
public class Evaluate{
	
	private String article_id;
	private Context context;
	private Note note;
	private Intent intent;
//	/** 0: ��  1:�ղ�   2:ת��*/
//	private int index;
	/**�Ǵ��ĸ�ҳ�洫������ 0:����ҳ��  1: �б�ҳ��*/
	private int number;
	private String user_id;
	private RequestQueue requestQueue = BlueApplication.requestQueue;
	private KaXiuListAdapter kaXiuListAdapter;
	private JsonObjectRequest jsonObjectRequest;
	private static final String TAG = "Evaluate";
	//����ҳ��
	public Evaluate(Context context,String article_id){
		this.context = context;
		this.article_id = article_id;
		number = 0;
		user_id = LoginUtils.getLoginUserid(context);
		intent = new Intent(context,NewsCommentActivity.class);
	}
	//�б�ҳ��
	public Evaluate(Context context,Note note){
		this.context = context;
		this.note = note;
		number = 1;
		user_id = LoginUtils.getLoginUserid(context);
		intent = new Intent(context,NewsCommentActivity.class);
	}
	
	//ɾ��ҳ��
	public Evaluate(Context context,Note note,KaXiuListAdapter kaXiuListAdapter){
		this.context = context;
		this.note = note;
		this.kaXiuListAdapter = kaXiuListAdapter;
		user_id = LoginUtils.getLoginUserid(context);
	}
	
	public void Collect(){
		
	}
	
	public void comment() {
		addArticleId();
		intent.putExtra("index", 2);
		context.startActivity(intent);
	}
	
	public void forward() {
		addArticleId();
		intent.putExtra("index", 1);
		context.startActivity(intent);
	}
	
	private void addArticleId() {
		if (number == 1) {
			intent.putExtra("article_id", note.id);
			Log.i(TAG, "article_id = " +note.id);
		}else{
			intent.putExtra("article_id", article_id);
			Log.i(TAG, "article_id = "+ article_id);
		}
	}
	
	/**�޵ĵ���¼�*/
	public void zan() {
		
		jsonObjectRequest = new JsonObjectRequest(Method.POST, HttpUtils.initArticleURL("up"), 
				SubmitPara.collection(user_id, note.id),new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "zan"+response );
						if (response != null) {
							analyzeData(response);
						}
					}
				}, null);

		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}
	
	private void analyzeData(JSONObject response) {
		
		Boolean isCode = response.optString("code").equals("1");
		
		if (isCode && response.optString("msg").equals("1")) {
			BlueException.toast("���޳ɹ�");
		}else if(isCode && response.optString("msg").equals("0")){
			BlueException.toast("ȡ������");
		}
	}
	
	/**�ղ�*/
	public void CollectionNote(){
		
		if (!BlueApplication.isLogin(context)) {
			return;
		}
		jsonObjectRequest = new JsonObjectRequest(Method.POST,HttpUtils.initArticleURL("collect"), pushtoMap(),new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "collect = "+response );
						String msg = response.optString("msg");
						String code = response.optString("code");
						
						if (code.equals("1")) {
							if (msg.equals("1")) {
								BlueException.toast("�ղسɹ�");
							}else{
								BlueException.toast("ȡ���ղ�");
							}
						}
					}
				}, null);

		requestQueue.add(jsonObjectRequest);
		requestQueue.start();
	}
	
	
	private Map<String,String> pushtoMap() {
		
		return number == 0 ? SubmitPara.collection(user_id, article_id) : SubmitPara.collection(user_id, note.id);
	}
	
	
}
