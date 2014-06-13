package com.blue.util;

import java.io.File;
import java.io.FileOutputStream;

import com.blue.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


/**
 * @author SLJM
 * @create 2014-4-9
 * @desc ��ȡͼƬ�Ĺ�����
 *
 */
public class ImageCrop{
	
	private Uri photo_location;
	public static final int PICK_FROM_CAMERA = 1;
	public static final int CROP_FROM_CAMERA = 2;
	public static final int PICK_FROM_FILE = 3;
	private int OUT_PUT_X = 200, OUT_PUT_Y = 200;
	private AlertDialog dialog;
	private String[] items = new String[] { "�����ѡ��", "����", "ȡ��" };
	//�ļ���·��
	private static final String FOLDER_PATH = "/sdcard/SLJM/photo";
	private Activity context;
	
	public ImageCrop(Activity context){
		this.context = context;
	}
	
	public void showDialog() {
		if (dialog == null) {
			dialog = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle("ѡ��ͼƬ��Դ")
					.setItems(items, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int position) {
							switch (position) {
							case 0:
								LocationPhoto();
								break;
							case 1:
								CameraPhoto();
								break;

							default:
								break;
							}
						}
					}).create();
		}
		dialog.show();
	}
	
	/**ͨ�����ջ�ȡͼƬ*/
	public void CameraPhoto(){
		
		photo_location = getPicPath();
		if(photo_location == null)
			return;
		
		Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photo_location);
		Log.i("ImageCrop", "camera");
		context.startActivityForResult(intentCamera, PICK_FROM_CAMERA);
		
		
	}
	
	/**ͨ�����ػ�ȡͼƬ*/
	public void LocationPhoto(){
		
		photo_location = getPicPath();
		if(photo_location == null)
			return;
		
		Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT, null);
		intentFile.setType("image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intentFile.putExtra("crop", "false");
		// aspectX aspectY �ǿ�ߵı���
//	    intentFile.putExtra("aspectX", 1);
//	    intentFile.putExtra("aspectY", 1);
		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
		intentFile.putExtra("outputX", OUT_PUT_X);
		intentFile.putExtra("outputY", OUT_PUT_Y);
		intentFile.putExtra("scale", true);
		intentFile.putExtra("return-data", false);
		intentFile.putExtra(MediaStore.EXTRA_OUTPUT, photo_location);
		intentFile.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intentFile.putExtra("noFaceDetection", false); // no face detection
		Log.i("ImageCrop", "save before is exec ?");
		context.startActivityForResult(intentFile, PICK_FROM_FILE);
	}
	
	public Uri onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri photoUri = null;
		if (resultCode != context.RESULT_OK)
			return null;
		switch (requestCode) {
		case PICK_FROM_CAMERA:
			Log.i("ImageCrop", "camera"+photo_location);
			photoUri = photo_location;
//			photoUri = doCrop(photo_location, CROP_FROM_CAMERA);
			break;
		case PICK_FROM_FILE:
			photoUri = photo_location;
			break;
		case CROP_FROM_CAMERA:
			photoUri = photo_location;
			break;
		}
		return photoUri;
	}
	
	private Uri doCrop(Uri uri, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
//		 intent.putExtra("aspectX", 1);
//		 intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", OUT_PUT_X);
		intent.putExtra("outputY", OUT_PUT_Y);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		context.startActivityForResult(intent, requestCode);
		return photo_location;
	}
	
	/**��ȡͼƬ·��*/
	private Uri getPicPath() {
		
		Uri pic_loc = null;
		if(!isSDcard()){
			Toast.makeText(context, "��SD card", Toast.LENGTH_SHORT).show();
		}
		//SystemClock.currentThreadTimeMillis(); // �ڵ�ǰ�߳��������е�ʱ��
		pic_loc = Uri.fromFile(new File(FOLDER_PATH, "pic_" + SystemClock.currentThreadTimeMillis() + ".jpg"));
		
		return pic_loc; 
	}
	
	/**�ж�SD card �Ƿ����*/
	private Boolean isSDcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			CreateFolder();
		   return true;
		  } else {
		   return false;
		  }
	}
	
	/**�½��ļ���,�޸�Ȩ��*/
	private void CreateFolder() {
		File destDir = new File(FOLDER_PATH);
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }

//		//�޸�Ȩ��
//
//		 FileOutputStream fos;   
//
//		 fos = openFileOutput("filename" , MODE_WORLD_READABLE);

	}
}
