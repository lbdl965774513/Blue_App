package com.blue.async;

import android.os.AsyncTask;
import android.widget.SimpleAdapter;


/**
 * @author SLJM
 * @create 2014-4-4
 * @desc ����"�ҵ�����"ҳ�����Ϣ
 *
 */
public class UploadDataAdapterAsync extends AsyncTask<String, Integer, String>{
	
	private String[] data_array;
	private SimpleAdapter simpleAdapter;
	
	public UploadDataAdapterAsync(String[] data_array,SimpleAdapter simpleAdapter){
		
		this.data_array = data_array;
		this.simpleAdapter = simpleAdapter;
	}

	
	/**
	 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
	 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
	 */
	@Override
	protected String doInBackground(String... params) {
		
		data_array[Integer.parseInt(params[0])] = params[1];
		return "success";
	}
	
	/**
	 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
	 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		simpleAdapter.notifyDataSetChanged();
	}
	
	/** �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ�������� */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
