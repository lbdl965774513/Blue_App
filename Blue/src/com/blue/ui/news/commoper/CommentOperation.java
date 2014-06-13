package com.blue.ui.news.commoper;

import com.blue.R;
import com.blue.ui.news.NewsCommentActivity;
import com.blue.ui.user.UserCenterActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

/**
 * @author SLJM
 * @create 2014-5-26
 * @desc ��������ҳ��,����item�ĵ��
 * @param ����������
 * @param ԭ���۵�id
 *
 */
public class CommentOperation implements OnClickListener{
	
	private String mComment_id;
	private String user_id;
	private Context mContext;
	private AlertDialog.Builder mDialog;
	private AlertDialog mAlert;
	
	private static final String TAG = "CommentOperation";
	
	public CommentOperation(Context mContext,String id,String user_id){
		this.mContext = mContext;
		this.mComment_id = id;
		this.user_id = user_id;
		
	}
	
	public void showDialog(){
		
		
		final Resources resources = mContext.getResources();
		String dialog_text[] = resources.getStringArray(R.array.comment_operation);
		
		mDialog = new AlertDialog.Builder(mContext);
		
		mDialog.setItems(dialog_text, this);

		mAlert = mDialog.create();
		mAlert.show();
		

	}

	private void operstion(int position) {
		
		Intent intent = new Intent();
		
		switch (position) {
		//�Ӻ���
		case 0:
			
			break;
		//�ظ�������
		case 1:
			intent.setClass(mContext, NewsCommentActivity.class);
			intent.putExtra("index", 3);
			intent.putExtra("fid", mComment_id);
			break;
		//�鿴TA����ҳ
		case 2:
			intent.setClass(mContext, UserCenterActivity.class);
			intent.putExtra("user_id", user_id);
			Log.i(TAG, "user_id = " + user_id);
			break;
		//ȡ��
		case 3:
			mAlert.dismiss();
			break;
		default:
			break;
		}
		mContext.startActivity(intent);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		operstion(which);
	}
}
