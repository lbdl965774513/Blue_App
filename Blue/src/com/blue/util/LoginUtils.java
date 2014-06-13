package com.blue.util;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author SLJM
 * 
 *         处理用户登录后的user_id
 */
public class LoginUtils {

	/** 保存登录时的user_id */
	public static void saveLoginUserid(String user_id, Context context) {
		SharedPreferences sharedPreferences;
		SharedPreferences.Editor editor;

		sharedPreferences = context.getSharedPreferences("Login_Info",Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putString("user_id", user_id);

		editor.commit();
	}

	/** 取出登录时的user_id */
	public static String getLoginUserid(Context context) {
		
		SharedPreferences sharedPreferences;
		String user_id = "";
		
		sharedPreferences = context.getSharedPreferences("Login_Info",Context.MODE_PRIVATE);
		user_id = sharedPreferences.getString("user_id", "");
		
		Log.i("sharePreferences  user_id", user_id);
		
		return user_id;

	}
}
