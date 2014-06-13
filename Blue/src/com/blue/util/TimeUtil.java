package com.blue.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.format.Time;

public class TimeUtil {
	/**
	 * 将时间戳转为代表"距现在多久之前"的字符串
	 * 
	 * @param timeStr
	 *            时间戳
	 * @return
	 */
	public static String getStandardDate(String timeStr) {
		StringBuffer sb = new StringBuffer();
		if (timeStr == null) {
			return timeStr;
		} else {

			long t = Long.parseLong(timeStr);
			long time = System.currentTimeMillis() - (t * 1000);
			long mill = (long) Math.ceil(time / 1000);// 秒前

			long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

			long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

			long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

			if (day - 1 > 0) {
				sb.append(getStrTime(timeStr));
			} else if (hour - 1 > 0) {
				if (hour >= 24) {
					sb.append("1天");
				} else {
					sb.append(hour + "小时前");
				}
			} else if (minute - 1 > 0) {
				if (minute == 60) {
					sb.append("1小时前");
				} else {
					sb.append(minute + "分钟前");
				}
			} else if (mill - 1 > 0) {
				if (mill == 60) {
					sb.append("1分钟前");
				} else {
					sb.append(mill + "秒前");
				}
			} else {
				sb.append("刚刚");
			}
		}
		return sb.toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}

	public static String getTime() {

		Time time = new Time("GMT+8");
		time.setToNow();
		int day = time.monthDay;
		int minute = time.minute;
		int hour = time.hour;
		int sec = time.second;

		String strtime = ":_____" + day + "日 " + hour + "时 " + minute
				+ "分    // " + sec + "秒";
		return strtime;

	}

}

