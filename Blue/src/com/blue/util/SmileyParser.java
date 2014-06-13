package com.blue.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blue.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class SmileyParser {
	public static final int[] DEFAULT_SMILEY_RES_IDS = {
			 R.drawable.f001,
			 R.drawable.f002,
			 R.drawable.f003,
			 R.drawable.f004,
			 R.drawable.f005,
			 R.drawable.f006,
			 R.drawable.f007,
			 R.drawable.f008,
			 R.drawable.f009,
			
			 R.drawable.f010,
			 R.drawable.f011,
			 R.drawable.f012,
			 R.drawable.f013,
			 R.drawable.f014,
			 R.drawable.f015,
			 R.drawable.f016,
			 R.drawable.f017,
			 R.drawable.f018,
			 R.drawable.f019,
			
			 R.drawable.f020,
			 R.drawable.f021,
			 R.drawable.f022,
			 R.drawable.f023,
			 R.drawable.f024,
			 R.drawable.f025,
			 R.drawable.f026,
			 R.drawable.f027,
			 R.drawable.f028,
			 R.drawable.f029,
			
			 R.drawable.f030,
			 R.drawable.f031 ,
			 R.drawable.f032 ,
			 R.drawable.f033 ,
			 R.drawable.f034 ,
			 R.drawable.f035 ,
			 R.drawable.f036 ,
			 R.drawable.f037 ,
			 R.drawable.f038 ,
			 R.drawable.f039 ,
			
			 R.drawable.f040 ,
			 R.drawable.f041 ,
			 R.drawable.f042 ,
			 R.drawable.f043 ,
			 R.drawable.f044 ,
			 R.drawable.f045 ,
			 R.drawable.f046 ,
			 R.drawable.f047 ,
			 R.drawable.f048 ,
			 R.drawable.f049 ,
			
			 R.drawable.f050 ,
			 R.drawable.f051 ,
			 R.drawable.f052,
			 R.drawable.f053 ,
			 R.drawable.f054 ,
			 R.drawable.f055 ,
			 R.drawable.f056 ,
			 R.drawable.f057 ,
			 R.drawable.f058 ,
			 R.drawable.f059 ,
			
			 R.drawable.f060 ,
			 R.drawable.f061 ,
			 R.drawable.f062 ,
			 R.drawable.f063 ,
			 R.drawable.f064 ,
			 R.drawable.f065 ,
			 R.drawable.f066 ,
			 R.drawable.f067 ,
			 R.drawable.f068 ,
			 R.drawable.f069 ,

//			R.drawable.biggrin, R.drawable.cry, R.drawable.fendou,
//			R.drawable.funk, R.drawable.huffy, R.drawable.ha, R.drawable.ku,
//			R.drawable.lol, R.drawable.loveliness, R.drawable.mad,
//			R.drawable.sad, R.drawable.shy, R.drawable.smile, R.drawable.sweat,
//			R.drawable.titter, R.drawable.tongue, R.drawable.yiwen,
//			R.drawable.yun, R.drawable.pig, R.drawable.handshake,
//			R.drawable.ok, R.drawable.victory, R.drawable.beer,
//			R.drawable.cake, R.drawable.call, R.drawable.hug, R.drawable.kiss,
//			R.drawable.moon, R.drawable.star_level1, R.drawable.time,
//			R.drawable.st_public, R.drawable.st_oneself, R.drawable.st_student,
//			R.drawable.st_teacher, R.drawable.tc_class,
//			R.drawable.tc_part_student, R.drawable.tc_public,
//			R.drawable.tc_teacher,

	};
	private Context mContext;
	private String[] mSmileyTexts;
	private Pattern mPattern;
	private HashMap<String, Integer> mSmileyToRes;
	public static final int DEFAULT_SMILEY_TEXTS = R.array.facename;

	public SmileyParser(Context context) {
		mContext = context;
		mSmileyTexts = mContext.getResources().getStringArray(
				DEFAULT_SMILEY_TEXTS);
		mSmileyToRes = buildSmileyToRes();
		mPattern = buildPattern();
	}

	private HashMap<String, Integer> buildSmileyToRes() {
		if (DEFAULT_SMILEY_RES_IDS.length != mSmileyTexts.length) {
			// Log.w("SmileyParser", "Smiley resource ID/text mismatch");
			throw new IllegalStateException("Smiley resource ID/text mismatch");
		}

		HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(
				mSmileyTexts.length);
		for (int i = 0; i < mSmileyTexts.length; i++) {
			smileyToRes.put(mSmileyTexts[i], DEFAULT_SMILEY_RES_IDS[i]);
		}

		return smileyToRes;
	}

	// 构建正则表达式
	private Pattern buildPattern() {
		StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);
		patternString.append('(');
		for (String s : mSmileyTexts) {
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		patternString.replace(patternString.length() - 1,
				patternString.length(), ")");

		return Pattern.compile(patternString.toString());
	}

	// 根据文本替换成图片
	public SpannableString replace(String test) {
		SpannableString spanableInfo = new SpannableString(test);
		Matcher matcher = mPattern.matcher(test);
		while (matcher.find()) {
			int resId = mSmileyToRes.get(matcher.group());
			Drawable drawable = mContext.getResources().getDrawable(resId);
			Resources res = mContext.getResources();
			int[] bits = res.getIntArray(R.array.bits);
			drawable.setBounds(0, 0, bits[0], bits[0]);
			ImageSpan imgSpan = new ImageSpan(drawable,
					ImageSpan.ALIGN_BASELINE);
			spanableInfo.setSpan(imgSpan, matcher.start(), matcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spanableInfo;
		/***********************************************/
	}

	class Clickable extends ClickableSpan implements OnClickListener {
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;
		}

		@Override
		public void onClick(View v) {
			mListener.onClick(v);
		}
	}
}
