package com.blue.bean;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
/**
 * @author SLJM
 * @create 2014-4-26
 * @desc ����(note)������(article)(details)
 */
public class Article {
	/**
	 * ����ID
	 */
	@SerializedName("id")
	public String id;
	/**
	 * �û�ͷ��
	 *
	 */
	@SerializedName("user_avatar")
	public String user_icon;
	/**
	 * �û�ID
	 */
	@SerializedName("user_id")
	public String user_id;
	/**
	 * ���۸���
	 */
	@SerializedName("comment_count")
	public String comment_count;
	/**
	 *  ����ʱ��
	 */
	@SerializedName("date_create")
	public String ctime;
	/**
	 *	�ֻ��ͺ�
	 */
	@SerializedName("phone_type")
	public String phone_type;
	/**
	 *	ת������
	 */
	@SerializedName("re_count")
	public String re_count;
	
	/**
	 *	��������
	 *1������2����ҳ 3������4����Ѷ  5:ת����
	 */
	@SerializedName("type")
	public String type;
	/**
	 *	ԭ������(���Ϊת��������)
	 */
	@SerializedName("re")
	public ForwardNote forwardNote;
	/**
	 *	��������
	 */
	@SerializedName("info")
	public String content;
	/**
	 *	�û��ǳ�
	 */
	@SerializedName("user_name")
	public String user_name;
	/**
	 *	���±���(ֻ����ҵ��Ѷ�������б�����ʾ,����û��)
	 */
	@SerializedName("title")
	public String title;
	/**
	 *  �޸���
	 */
	@SerializedName("like_count")
	public String like_count;
	
	/**
	 *  ͼƬ(����)
	 */
	@SerializedName("attach")
	public String img;
	
	/**
	 *  ͼƬ����
	 */
	@SerializedName("img")
	public ArrayList<NotePhoto> img_array;
	
}