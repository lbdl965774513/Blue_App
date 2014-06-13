package com.blue.util;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * @author SLJM
 * @desc ����ϴ���map
 * @create 2014-5-14
 * @parameters 
 *
 */
public class SubmitPara {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	/**��¼*/
	public static Map<String,String> login(String name,String password){
		
		map.put("name", name);
		map.put("password", password);
		
		return map;
	}
	/**ע��*/
	public static Map<String,String> register(String name,String password){
		
		map.put("name", name);
		map.put("password", password);
		
		return map;
	}
	/**��ȡ�û���Ϣ*/
	public static Map<String,String> getUserInfo(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
	//TODO �˴����޸�
	/**�û���Ϣ����*/
	public static Map<String,String> updateUserInfo(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
	/**��ȡ�����б�
	 * @param type  1������2����ҳ3������4����ѯ 6������7������
	 * @param start  ��ҳ��ʼ
	 * @return
	 */
	public static Map<String,String> getNoteList(int type,int start){
		
		map.put("type", String.valueOf(type));
		map.put("start", String.valueOf(start));
		
		return map;
	}
	/**��ȡ�ҵ������б�
	 * @param type  1������2����ҳ3������4����ѯ 6������7������
	 * @param start  ��ҳ��ʼ
	 * @return
	 */
	public static Map<String,String> getNoteList(int type,int start,String user_id){
		
		map.put("type", String.valueOf(type));
		map.put("start", String.valueOf(start));
		map.put("user_id", user_id);
		
		return map;
	}
	/**��ȡ��������
	 * @param article_id  7����˾���  8���ṩ�ķ���  9�����ǵ��Ŷ�  40����ϵ����
	 * @return
	 */
	public static Map<String,String> getNoteDetails(String article_id){
		
		map.put("article_id", article_id);
		
		return map;
	}
	/**�������
	 * @param img  �����ͼƬ�ϴ�,����volley��������  ���Դ˴�����Ҫimg����
	 * @param password
	 * @return
	 */
	public static Map<String,String> addNote(String user_id,String info,String phone_type){
		
		map.put("user_id", user_id);
		map.put("info", info);
		map.put("phone_type", phone_type);
		
		return map;
	}

	/***/
	/**ת������
	 * @param user_id  ԭ�����û�id
	 * @param article_id  ԭ����id
	 * @param info
	 * @param phone_type
	 * @return
	 */
	public static Map<String,String> forwardNote(String user_id,String article_id,String info,String phone_type){
		
		map.put("user_id", user_id);
		map.put("article_id", article_id);
		map.put("info", info);
		map.put("phone_type", phone_type);
		
		return map;
	}
	/**ɾ������*/
	public static Map<String,String> deleteNote(String user_id,String article_id){
		
		map.put("user_id", user_id);
		map.put("article_id", article_id);
		
		return map;
	}
	/**�����б�*/
	public static Map<String,String> commentList(String article_id,String start){
		
		map.put("article_id", article_id);
		map.put("start", start);
		
		return map;
	}
	/**�������*/
	public static Map<String,String> addComment(String article_id,String user_id,String info){
		
		map.put("article_id", article_id);
		map.put("user_id", user_id);
		map.put("info", info);
		
		return map;
	}
	/**�����б�*/
	public static Map<String,String> zanList(String article_id){
		
		map.put("article_id", article_id);
		
		return map;
	}
	/**�ղ�  ����*/
	public static Map<String,String> collection(String user_id,String article_id){
		
		map.put("user_id", user_id);
		map.put("article_id", article_id);
		
		return map;
	}
	/**�ղ��б�*/
	public static Map<String,String> collectList(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
//	/**�������*/
//	public static Map<String,String> login(String name,String password){
//		
//		map.put("name", name);
//		map.put("password", password);
//		
//		return map;
//	}
}
