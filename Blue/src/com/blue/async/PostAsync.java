package com.blue.async;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.blue.conninternet.PostHttp;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author SLJM
 * @create 2014-4-10
 * @desc 发帖异步类
 * 
 */
public class PostAsync extends AsyncTask<Map<String,String>, Integer, String> {

	private Context context;
	private PostHttp postHttp;
	ProgressDialog dialog;
	//上传图片的个数
	int pic_count;
	//0 :发帖失败  1:发帖成功
	String result = "error";
	
	public PostAsync(Context context,int pic_count) {
		this.context = context;
		this.pic_count = pic_count;
		dialog = new ProgressDialog(context);
	}
	public PostAsync(Context context){
		this.context = context;
		dialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	/** 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置 */
	@Override
	protected void onPreExecute() {
		
		dialog.setTitle("提示");
		dialog.setMessage("玩命上传中,请稍后...");
		dialog.show();
		
	}

	/**
	 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
	 */
	@Override
	protected String doInBackground(Map<String,String>... params) {

			postHttp = new PostHttp(context);
			
//			for (int i = 0; i < pic_count; i++) {
				try {
					postHttp.uploadPhoto(params[0]);
					result = postHttp.uploadText(params[1]);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
//			}
			return result;
		}

	/**
	 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
	 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	 */
	@Override
	protected void onPostExecute(String result) {
		dialog.dismiss();
		Log.i("PostAsync", "result = "+result);
		if (result.equals("success")) {
			Log.i("---", "---");
			Toast.makeText(context, "发帖成功,请返回大咖秀列表查看吧O(∩_∩)O~",Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "发帖失败~~~~(>_<)~~~~ ",Toast.LENGTH_SHORT).show();

		}
		

	};


}
