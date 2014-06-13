package com.blue.bean;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * @author SLJM
 * @create 2014-4-30
 * @desc 如果为转发的帖子,此为原帖内容
 */
public class ForwardNote {
	
	@SerializedName("id")
	public String article_id;

	@SerializedName("user_id")
	public String user_id;
	
	@SerializedName("user_name")
	public String user_name;
	
	@SerializedName("info")
	public String content;
	
	@SerializedName("attach")
	public String img;
}
