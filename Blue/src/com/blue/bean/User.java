package com.blue.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.annotations.SerializedName;

/**
 * @author SLJM
 * @create 2014-5-6
 * @desc 用户信息
 *
 */


public class User {
	/**
	 * 帖子数目
	 */
	@SerializedName("date_log")
	public String note_num;
	/**
	 * 用户生日
	 *
	 */
	@SerializedName("birth")
	public String birth;
	/**
	 * 用户头像
	 */
	@SerializedName("attach")
	public String user_ic;
	/**
	 * 评论个数
	 */
	@SerializedName("password")
	public String password;
	/**
	 *  用户ID
	 */
	@SerializedName("id")
	public String user_id;
	/**
	 *  用户年龄
	 */
	@SerializedName("age")
	public String age;
	/**
	 *	用户地址
	 */
	@SerializedName("address")
	public String address;
	/**
	 *	用户电子邮箱
	 */
	@SerializedName("email")
	public String email;
	
	/**
	 * 用户的兴趣
	 */
	@SerializedName("interest")
	public String interest;
	/**
	 *	用户昵称
	 */
	@SerializedName("name")
	public String user_name;
	/**
	 *	性别
	 */
	@SerializedName("gender")
	public int gender;
	/**
	 *	注册时间
	 */
	@SerializedName("date_reg")
	public String date_reg;
	/**
	 *	用户签名
	 */
	@SerializedName("signature")
	public String signature;
	/**
	 *  用户QQ
	 */
	@SerializedName("user_qq")
	public String user_qq;
	
	/**
	 *  手机
	 */
	@SerializedName("mobile")
	public String user_phone;
	
	public String genGender(){
		
		if(gender == 1){
			return "男";
		}else{
			return "女";
		}
//		return gender == 1 : "男" ? "女"; 
	}
	
}
