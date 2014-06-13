package com.blue.bean;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.annotations.SerializedName;

/**
 * @author SLJM
 * @create 2014-4-26
 * @desc 帖子(note)或文章(article)(list)
 *
 */


public class Note {
	/**
	 * 帖子ID
	 */
	@SerializedName("id")
	public String id;
	/**
	 * 用户头像
	 *
	 */
	@SerializedName("user_avatar")
	public String user_icon;
	/**
	 * 用户ID
	 */
	@SerializedName("user_id")
	public String user_id;
	/**
	 * 评论个数
	 */
	@SerializedName("comment_count")
	public String comment_count;
	
	/**
	 *  创建时间
	 */
	@SerializedName("date_create")
	public String ctime;
	/**
	 *	手机型号
	 */
	@SerializedName("phone_type")
	public String phone_type;
	/**
	 *	转发个数
	 */
	@SerializedName("re_count")
	public String re_count;
	/**
	 *	原帖内容(如果为转发的帖子)
	 */
	@SerializedName("re")
	public ForwardNote forwardNote;
	
	/**
	 *	帖子类型
	 *1：帖子2：单页 3：新闻4：资讯  5:转发帖
	 */
	@SerializedName("type")
	public String type;
	
	
	/**
	 *	帖子内容
	 */
	@SerializedName("info")
	public String content;
	/**
	 *	用户昵称
	 */
	@SerializedName("user_name")
	public String user_name;
	/**
	 *	文章标题(只在行业资讯和文章列表中显示,大咖秀没有)
	 */
	@SerializedName("title")
	public String title;
	/**
	 *  赞个数
	 */
	@SerializedName("like_count")
	public String like_count;
	/**
	 *  如果为我的收藏,此为收藏的原帖
	 */
	@SerializedName("article_info")
	public Note article_info;
	
	/**
	 *  单张图片
	 */
	@SerializedName("attach")
	public String img;
	/**
	 *  图片数组
	 */
	@SerializedName("img")
	public ArrayList<NotePhoto> img_array;
	
}
