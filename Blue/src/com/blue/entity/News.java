package com.blue.entity;

/**
 * @author SLJM
 * @create 2014-4-12
 * @desc 实体类   新闻(每条)
 *
 */
public class News {

	//评论数目
	public String comment_count;
	//内容
	private String content;
	//文章id
	private String id;
	//文章头像
	private String image;
	//文章标题
	private String title;
	//赞数目
	private String up_count;
	//用户id
	private String user_id;
	//创建时间
	private String ctime;
	//状态  1:桌面数据  2:单页   3:公司新闻   4:行业咨询
	private String status;
}
