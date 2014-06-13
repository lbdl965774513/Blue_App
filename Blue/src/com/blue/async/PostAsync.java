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
 * @desc �����첽��
 * 
 */
public class PostAsync extends AsyncTask<Map<String,String>, Integer, String> {

	private Context context;
	private PostHttp postHttp;
	ProgressDialog dialog;
	//�ϴ�ͼƬ�ĸ���
	int pic_count;
	//0 :����ʧ��  1:�����ɹ�
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
	/** �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ�������� */
	@Override
	protected void onPreExecute() {
		
		dialog.setTitle("��ʾ");
		dialog.setMessage("�����ϴ���,���Ժ�...");
		dialog.show();
		
	}

	/**
	 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
	 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
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
	 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
	 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	 */
	@Override
	protected void onPostExecute(String result) {
		dialog.dismiss();
		Log.i("PostAsync", "result = "+result);
		if (result.equals("success")) {
			Log.i("---", "---");
			Toast.makeText(context, "�����ɹ�,�뷵�ش����б��鿴��O(��_��)O~",Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "����ʧ��~~~~(>_<)~~~~ ",Toast.LENGTH_SHORT).show();

		}
		

	};


}