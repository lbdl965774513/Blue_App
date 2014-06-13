package com.blue.global;

import com.blue.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author SLJM
 * @create 2014-5-24
 * @desc ´óÍ¼ dialog
 */
public class ImageDialog implements OnClickListener{
	
	private ImageLoader imageLoader;
	private String img_url;
	private Context context;
	private ImageView imageView;
	
	public ImageDialog(ImageView imageView,String url,Context context){
		this.imageView = imageView;
		this.img_url = url;
		this.context = context;
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public void onClick(View v) {
		
		
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.image_dialog, null);
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		ImageView img = (ImageView)view.findViewById(R.id.image_dialog_iv);
		
		imageLoader.displayImage(img_url, imageView);
		
		
	}
	
}
