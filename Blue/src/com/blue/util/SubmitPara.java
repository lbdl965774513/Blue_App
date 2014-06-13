package com.blue.util;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * @author SLJM
 * @desc 添加上传的map
 * @create 2014-5-14
 * @parameters 
 *
 */
public class SubmitPara {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	/**登录*/
	public static Map<String,String> login(String name,String password){
		
		map.put("name", name);
		map.put("password", password);
		
		return map;
	}
	/**注册*/
	public static Map<String,String> register(String name,String password){
		
		map.put("name", name);
		map.put("password", password);
		
		return map;
	}
	/**获取用户信息*/
	public static Map<String,String> getUserInfo(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
	//TODO 此处待修改
	/**用户信息更新*/
	public static Map<String,String> updateUserInfo(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
	/**获取文章列表
	 * @param type  1：帖子2：单页3：新闻4：咨询 6：案例7：服务
	 * @param start  分页开始
	 * @return
	 */
	public static Map<String,String> getNoteList(int type,int start){
		
		map.put("type", String.valueOf(type));
		map.put("start", String.valueOf(start));
		
		return map;
	}
	/**获取我的帖子列表
	 * @param type  1：帖子2：单页3：新闻4：咨询 6：案例7：服务
	 * @param start  分页开始
	 * @return
	 */
	public static Map<String,String> getNoteList(int type,int start,String user_id){
		
		map.put("type", String.valueOf(type));
		map.put("start", String.valueOf(start));
		map.put("user_id", user_id);
		
		return map;
	}
	/**获取文章详情
	 * @param article_id  7：公司简介  8：提供的服务  9：我们的团队  40：联系我们
	 * @return
	 */
	public static Map<String,String> getNoteDetails(String article_id){
		
		map.put("article_id", article_id);
		
		return map;
	}
	/**添加文章
	 * @param img  如果用图片上传,不用volley请求数据  所以此处不需要img参数
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
	/**转发帖子
	 * @param user_id  原帖的用户id
	 * @param article_id  原帖的id
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
	/**删除帖子*/
	public static Map<String,String> deleteNote(String user_id,String article_id){
		
		map.put("user_id", user_id);
		map.put("article_id", article_id);
		
		return map;
	}
	/**评论列表*/
	public static Map<String,String> commentList(String article_id,String start){
		
		map.put("article_id", article_id);
		map.put("start", start);
		
		return map;
	}
	/**添加评论*/
	public static Map<String,String> addComment(String article_id,String user_id,String info){
		
		map.put("article_id", article_id);
		map.put("user_id", user_id);
		map.put("info", info);
		
		return map;
	}
	/**点赞列表*/
	public static Map<String,String> zanList(String article_id){
		
		map.put("article_id", article_id);
		
		return map;
	}
	/**收藏  点赞*/
	public static Map<String,String> collection(String user_id,String article_id){
		
		map.put("user_id", user_id);
		map.put("article_id", article_id);
		
		return map;
	}
	/**收藏列表*/
	public static Map<String,String> collectList(String user_id){
		
		map.put("user_id", user_id);
		
		return map;
	}
//	/**添加文章*/
//	public static Map<String,String> login(String name,String password){
//		
//		map.put("name", name);
//		map.put("password", password);
//		
//		return map;
//	}
}
