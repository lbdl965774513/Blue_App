package com.blue.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.format.Time;

public class TimeUtil {
	/**
	 * ��ʱ���תΪ����"�����ڶ��֮ǰ"���ַ���
	 * 
	 * @param timeStr
	 *            ʱ���
	 * @return
	 */
	public static String getStandardDate(String timeStr) {
		StringBuffer sb = new StringBuffer();
		if (timeStr == null) {
			return timeStr;
		} else {

			long t = Long.parseLong(timeStr);
			long time = System.currentTimeMillis() - (t * 1000);
			long mill = (long) Math.ceil(time / 1000);// ��ǰ

			long minute = (long) Math.ceil(time / 60 / 1000.0f);// ����ǰ

			long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// Сʱ

			long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// ��ǰ

			if (day - 1 > 0) {
				sb.append(getStrTime(timeStr));
			} else if (hour - 1 > 0) {
				if (hour >= 24) {
					sb.append("1��");
				} else {
					sb.append(hour + "Сʱǰ");
				}
			} else if (minute - 1 > 0) {
				if (minute == 60) {
					sb.append("1Сʱǰ");
				} else {
					sb.append(minute + "����ǰ");
				}
			} else if (mill - 1 > 0) {
				if (mill == 60) {
					sb.append("1����ǰ");
				} else {
					sb.append(mill + "��ǰ");
				}
			} else {
				sb.append("�ո�");
			}
		}
		return sb.toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��");

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

		String strtime = ":_____" + day + "�� " + hour + "ʱ " + minute
				+ "��    // " + sec + "��";
		return strtime;

	}

}

