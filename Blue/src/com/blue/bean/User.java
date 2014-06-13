package com.blue.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.annotations.SerializedName;

/**
 * @author SLJM
 * @create 2014-5-6
 * @desc �û���Ϣ
 *
 */


public class User {
	/**
	 * ������Ŀ
	 */
	@SerializedName("date_log")
	public String note_num;
	/**
	 * �û�����
	 *
	 */
	@SerializedName("birth")
	public String birth;
	/**
	 * �û�ͷ��
	 */
	@SerializedName("attach")
	public String user_ic;
	/**
	 * ���۸���
	 */
	@SerializedName("password")
	public String password;
	/**
	 *  �û�ID
	 */
	@SerializedName("id")
	public String user_id;
	/**
	 *  �û�����
	 */
	@SerializedName("age")
	public String age;
	/**
	 *	�û���ַ
	 */
	@SerializedName("address")
	public String address;
	/**
	 *	�û���������
	 */
	@SerializedName("email")
	public String email;
	
	/**
	 * �û�����Ȥ
	 */
	@SerializedName("interest")
	public String interest;
	/**
	 *	�û��ǳ�
	 */
	@SerializedName("name")
	public String user_name;
	/**
	 *	�Ա�
	 */
	@SerializedName("gender")
	public int gender;
	/**
	 *	ע��ʱ��
	 */
	@SerializedName("date_reg")
	public String date_reg;
	/**
	 *	�û�ǩ��
	 */
	@SerializedName("signature")
	public String signature;
	/**
	 *  �û�QQ
	 */
	@SerializedName("user_qq")
	public String user_qq;
	
	/**
	 *  �ֻ�
	 */
	@SerializedName("mobile")
	public String user_phone;
	
	public String genGender(){
		
		if(gender == 1){
			return "��";
		}else{
			return "Ů";
		}
//		return gender == 1 : "��" ? "Ů"; 
	}
	
}
