package com.blue.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.blue.R;
import com.blue.bean.Emotion;
import com.blue.ui.user.UserCenterActivity;

/**
 * 处理字体高亮
 * @author liyannan
 */
public class DealText {
	private static String user_id;
	private static List<Emotion> emotions = new ArrayList<Emotion>();
	/**
	 * @param mContext
	 * @param textview
	 * @param content
	 * @param hasClick
	 *            是否添加click
	 */
	public static void textViewSpan(Context mContext, TextView textview,SpannableString content,String id, boolean hasClick) {
		
		user_id = id;
		List<PositionItem> list = paresString2(content);
		Spannable span = new SpannableString(content);
		
		for (PositionItem pi : list) {
			if (pi.getPrefixType() == 4) {
				String imageName = "";
				for (Emotion em : emotions) {
					if (em.getPhrase().equals(pi.getContent())) {
						imageName = em.getSaveName2();
						break;
					}
				}
				try {
					Field f = (Field) R.drawable.class.getDeclaredField(imageName);
					int eId = f.getInt(R.drawable.class);
					Drawable drawable = mContext.getResources().getDrawable(eId);
					if (drawable != null) {
						ImageSpan imgSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
						span.setSpan(imgSpan, pi.start, pi.end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					} else {
						span.setSpan(
								new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue)),
								pi.start, pi.end,
								Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				} catch (Exception e) {
					// TODO: handle exception
					span.setSpan(
							new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue)),
								pi.start, pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			} else {
				if (hasClick) {
					span.setSpan(new TextClickSapn(mContext, pi), pi.start,pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				} else {
					span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue)),
							pi.start, pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
		textview.setText(span);
		if (hasClick)
			textview.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public static List<PositionItem> paresString(String content) {
		String regex = "@[^\\s:：《]+([\\s:：《]|$)|#(.+?)#|http://t\\.cn/\\w+|\\[(.*?)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		boolean b = m.find();
		List<PositionItem> list = new ArrayList<PositionItem>();
		while (b) {
			int start = m.start();
			int end = m.end();
			String str = m.group();
			list.add(new PositionItem(start, end, str, content.length()));
			b = m.find(m.end() - 1);
		}
		return list;
	}

	/**
	 * 这个是处理一条信息有多个#...
	 * 
	 * @param content
	 * @return
	 */
	public static List<PositionItem> paresString2(SpannableString content) {
		String regex = "@[^\\s:：《]+([\\s:：《]|$)|#(.+?)#|http://t\\.cn/\\w+|\\[(.*?)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		boolean b = m.find();
		List<PositionItem> list = new ArrayList<PositionItem>();
		int count = 0;
		int lastIndex = 0;
		while (b) {

			int start = m.start();
			int end = m.end();
			String str = m.group();
			if (str.startsWith("#")) {
				count++;
				if (count % 2 == 0) {
					b = m.find(lastIndex);
					continue;
				}
			}
			list.add(new PositionItem(start, end, str, content.length()));
			b = m.find(m.end() - 1);
			try {
				lastIndex = m.start() + 1;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return list;
	}

	private static class TextClickSapn extends ClickableSpan {
		private PositionItem item;
		private Context mContext;
		public TextClickSapn(Context context, PositionItem item) {
			// TODO Auto-generated constructor stub
			this.item = item;
			this.mContext = context;
		}
		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			switch (item.getPrefixType()) {
			case 1:
					Intent it_person = new Intent(mContext, UserCenterActivity.class);
					it_person.putExtra("user_id", user_id);
					mContext.startActivity(it_person);
				break;
			case 2://话题
				break;
			case 3://网络连接
				break;
			default:
				break;
			}
		}
	}
	public static class PositionItem {
		public int start;
		public int end;
		private String content;
		private int strLenght;
		public PositionItem(int start, int end, String content, int strLenght) {
			// TODO Auto-generated constructor stub
			this.start = start;
			this.end = end;
			this.content = content;
			this.strLenght = strLenght;
		}

		public PositionItem(int start, int end, String content) {
			// TODO Auto-generated constructor stub
			this.start = start;
			this.end = end;
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public String getContentWithoutPrefix() {
			switch (getPrefixType()) {
			case 1:
				if (end == strLenght)
					return content.substring(1, strLenght);
				return content.substring(1, content.length() - 1);
			case 2:
				return content.substring(1, content.length() - 1);
			case 3:
				return content;
			default:
				return content;
			}
		}

		/**
		 * 1 @ 人物 2 # 话题 3 http://t.cn/ 短链 4 [ 表情
		 * 
		 * @return
		 */
		public int getPrefixType() {
			if (content.startsWith("@"))
				return 1;
			if (content.startsWith("#"))
				return 2;
			if (content.startsWith("http://"))
				return 3;
			if (content.startsWith("["))
				return 4;
			return -1;
		}
	}

	/**
	 * 在SD卡的根目录创建文件夹，并在文件夹内创建文件，如果文件夹为null，则在SD卡根目录下直接创建文件
	 * 
	 * @param dir
	 * @param fileName
	 *            not null and ""
	 * @return file
	 */
	public static File createFile(String dir, String fileName) {
		File file = null;
		try {
			if (dir == null)
				file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + fileName);
			else
				file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ File.separator
						+ dir
						+ File.separator + fileName);

			File parent = file.getParentFile();
			if (parent != null && !parent.exists())
				parent.mkdirs();
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
