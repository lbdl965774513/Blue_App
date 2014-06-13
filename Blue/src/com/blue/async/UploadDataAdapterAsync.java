package com.blue.async;

import android.os.AsyncTask;
import android.widget.SimpleAdapter;


/**
 * @author SLJM
 * @create 2014-4-4
 * @desc 更新"我的资料"页面的信息
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
	 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
	 */
	@Override
	protected String doInBackground(String... params) {
		
		data_array[Integer.parseInt(params[0])] = params[1];
		return "success";
	}
	
	/**
	 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
	 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		simpleAdapter.notifyDataSetChanged();
	}
	
	/** 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
