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
 * @desc �쳣�������ʾ
 *
 */
public class BlueException extends Exception implements UncaughtExceptionHandler{

	private static final long serialVersionUID = 1L;
	// ������ʾ���쳣
	public static final int CUSTOM_EXCEPTION = 0;
	//���������쳣
	public static final int CONNECT_EXCEPTION = 1;
	//�������쳣
	public static final int NO_NETWORK = 2;
	//�������ӳ�ʱ�쳣
	public static final int CONNECT_TIMEOUT = 3;
	//���벻��ͬ�쳣
	public static final int PASSWD_NOSAME = 4;
	//����Ϊ���쳣
	public static final int NOT_BE_EMPTY = 5;
	//��ͣ�쳣
	public static final int PARSE_EXCEPTION = 6;
	//app�����쳣
	public static final int APP_CRASH = 7;
	//�����ļ��쳣
	public static final int CREATE_FILE_ERROR = 8;
	//û�и��������쳣
	public static final int NO_MORE_DATA = 9;
	//
	public static final int SOCKET_TIMEOUT = 10;
	//�������쳣
	public static final int NO_DATA = 11;
	//�����ʼ���ʽ�쳣
	public static final int EMAIL_FORMAT_ERROR = 12;
	//�����ɹ�
	public static final int OPERATION_SUCCEED = 13;
	
	public static final int HAS_BEEN_REVIEWED = 14;
	//���¼
	public static final int PLEASE_LOGIN = 15;
	
	private static final Context CONTEXT = BlueApplication.getContext();
	private UncaughtExceptionHandler defaultHandler;

	/**   
	    * ��ȡϵͳĬ�ϵ�UncaughtException������,   
	    * ���ø�CrashHandlerΪ�����Ĭ�ϴ�����   
	    */   
	public BlueException() {
		Thread.setDefaultUncaughtExceptionHandler(this);
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	/**   
	    * ��UncaughtException����ʱ��ת��ú���������   
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
		System.out.println("�Դ����쳣");
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
				Process.killProcess(Process.myPid()); // �رճ���
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
			//�����쳣
			case CONNECT_EXCEPTION:
				showMsg(R.string.connect_exception);
				break;
			//������
			case NO_NETWORK:
				showMsg(R.string.check_net);
				break;
			//���ӳ�ʱ
			case CONNECT_TIMEOUT:
				showMsg(R.string.connect_timeout);
				break;
			//������ͬ
			case PASSWD_NOSAME:
				showMsg(R.string.passwd_nosame);
				break;
			//���벻��Ϊ��
			case NOT_BE_EMPTY:
				showMsg(R.string.not_be_empty);
				break;
			//��������
			case PARSE_EXCEPTION:
				showMsg(R.string.parse_exception);
				break;
			//�����ļ�����
			case CREATE_FILE_ERROR:
				showMsg(R.string.create_file_error);
				break;
			//�������
			case APP_CRASH:
				showMsg(R.string.app_crash);
				break;
			//û�и�������
			case NO_MORE_DATA:
				showMsg(R.string.no_more_data);
				break;
			//��������Ӧ��ʱ
			case SOCKET_TIMEOUT:
				showMsg(R.string.socket_timeout);
				break;
			//û������
			case NO_DATA:
				showMsg(R.string.no_data);
				break;
			//���������ʽ����
			case EMAIL_FORMAT_ERROR:
				showMsg(R.string.email_format_error);
				break;
			//�����ɹ�
			case OPERATION_SUCCEED:
				showMsg(R.string.operation_succeed);
				break;
			//���Ѿ�������
			case HAS_BEEN_REVIEWED:
				showMsg(R.string.has_been_reviewed);
				break;
			//���ȵ�¼
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
