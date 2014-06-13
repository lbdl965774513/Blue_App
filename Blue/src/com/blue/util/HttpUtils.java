package com.blue.util;


/**
 * @author SLJM
 *
 * 网络相关公共方法
 */
public class HttpUtils {
	/**文章评论模块接口*/
	public final static String article = "http://www.shenlanvip.com/index.php?g=Api&m=Api&a=";
	
	public static String initArticleURL(String url){
		return article+url;
	} 
}
