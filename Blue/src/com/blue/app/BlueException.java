package com.blue.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

import com.blue.R;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

/**
 * @author SLJM
 * @create 2014-4-21
 * @desc 异常处理和提示
 *
 */
public class BlueException extends Exception implements UncaughtExceptionHandler{

	private static final long serialVersionUID = 1L;
	// 各种提示和异常
	public static final int CUSTOM_EXCEPTION = 0;
	//网络连接异常
	public static final int CONNECT_EXCEPTION = 1;
	//无网络异常
	public static final int NO_NETWORK = 2;
	//网络连接超时异常
	public static final int CONNECT_TIMEOUT = 3;
	//密码不相同异常
	public static final int PASSWD_NOSAME = 4;
	//不能为空异常
	public static final int NOT_BE_EMPTY = 5;
	//暂停异常
	public static final int PARSE_EXCEPTION = 6;
	//app崩溃异常
	public static final int APP_CRASH = 7;
	//创建文件异常
	public static final int CREATE_FILE_ERROR = 8;
	//没有更多数据异常
	public static final int NO_MORE_DATA = 9;
	//
	public static final int SOCKET_TIMEOUT = 10;
	//无数据异常
	public static final int NO_DATA = 11;
	//电子邮件格式异常
	public static final int EMAIL_FORMAT_ERROR = 12;
	//操作成功
	public static final int OPERATION_SUCCEED = 13;
	
	public static final int HAS_BEEN_REVIEWED = 14;
	//请登录
	public static final int PLEASE_LOGIN = 15;
	
	private static final Context CONTEXT = BlueApplication.getContext();
	private UncaughtExceptionHandler defaultHandler;

	/**   
	    * 获取系统默认的UncaughtException处理器,   
	    * 设置该CrashHandler为程序的默认处理器   
	    */   
	public BlueException() {
		Thread.setDefaultUncaughtExceptionHandler(this);
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	/**   
	    * 当UncaughtException发生时会转入该函数来处理   
	    */  
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		handleException(thread, ex);
		
	}
	
	public static final void toast(int what) {
		Message msg = toastHandler.obtainMessage();
		msg.what = what;
		toastHandler.sendMessage(msg);
	}

	public static final void toast(String message) {
		Message msg = toastHandler.obtainMessage();
		msg.what = 0;
		msg.obj = message;
		toastHandler.sendMessage(msg);
	}
	
	
	private void handleException(final Thread thread, final Throwable ex) {
		System.out.println("自处理异常");
		if (ex == null && defaultHandler != null) {
			defaultHandler.uncaughtException(thread, ex);
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				BlueException.toast(BlueException.APP_CRASH);
				String log = getCrashInfo(ex);
//				Rest rest = new RestImpl();
//				rest.sendLog(log);
				Process.killProcess(Process.myPid()); // 关闭程序
			}
		}).start();

	}
	
	private String getCrashInfo(Throwable ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);
		ex.printStackTrace(ps);
		String log = out.toString();
		return log;
	}
	
	private static final Handler toastHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CUSTOM_EXCEPTION:
				showMsg(msg.obj.toString());
				break;
			//网络异常
			case CONNECT_EXCEPTION:
				showMsg(R.string.connect_exception);
				break;
			//无网络
			case NO_NETWORK:
				showMsg(R.string.check_net);
				break;
			//连接超时
			case CONNECT_TIMEOUT:
				showMsg(R.string.connect_timeout);
				break;
			//密码相同
			case PASSWD_NOSAME:
				showMsg(R.string.passwd_nosame);
				break;
			//输入不能为空
			case NOT_BE_EMPTY:
				showMsg(R.string.not_be_empty);
				break;
			//解析错误
			case PARSE_EXCEPTION:
				showMsg(R.string.parse_exception);
				break;
			//创建文件错误
			case CREATE_FILE_ERROR:
				showMsg(R.string.create_file_error);
				break;
			//程序崩溃
			case APP_CRASH:
				showMsg(R.string.app_crash);
				break;
			//没有更多数据
			case NO_MORE_DATA:
				showMsg(R.string.no_more_data);
				break;
			//服务器响应超时
			case SOCKET_TIMEOUT:
				showMsg(R.string.socket_timeout);
				break;
			//没有数据
			case NO_DATA:
				showMsg(R.string.no_data);
				break;
			//电子邮箱格式错误
			case EMAIL_FORMAT_ERROR:
				showMsg(R.string.email_format_error);
				break;
			//操作成功
			case OPERATION_SUCCEED:
				showMsg(R.string.operation_succeed);
				break;
			//你已经操作过
			case HAS_BEEN_REVIEWED:
				showMsg(R.string.has_been_reviewed);
				break;
			//请先登录
			case PLEASE_LOGIN:
				showMsg(R.string.please_login);
				break;
			}
		}
	};
	
	private static void showMsg(String msg) {
		Toast.makeText(CONTEXT, msg, Toast.LENGTH_SHORT).show();
	}

	private static void showMsg(int resId) {
		Toast.makeText(CONTEXT, resId, Toast.LENGTH_SHORT).show();
	}













}
