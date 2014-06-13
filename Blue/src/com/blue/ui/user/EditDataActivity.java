package com.blue.ui.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.blue.R;
import com.blue.app.BlueApplication;
import com.blue.async.PostAsync;
import com.blue.bean.User;
import com.blue.conninternet.UserRequest;
import com.blue.lib.volley.Request.Method;
import com.blue.lib.volley.Response.Listener;
import com.blue.lib.volley.RequestQueue;
import com.blue.util.HttpUtils;
import com.blue.util.ImageCrop;
import com.blue.util.LoginUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author SLJM
 * @create 2014-5-6
 * @desc 编辑用户资料
 *
 */
public class EditDataActivity extends Activity implements OnItemClickListener,OnClickListener{
	
	private ImageView user_ic;
	private ListView edit_data_lv;
	private RelativeLayout ic_rl;
	private AlertDialog.Builder builder;
	private RequestQueue requestQueue = BlueApplication.requestQueue;
	private String list_array[] = {"昵称","性别","出生日期","地区","个性签名","兴趣爱好"}; 
	private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	/**判断用户点击的头像 还是list*/
	private int index;
	private int position;
	private User user;
	private String icon_uri;
	private ListAdapter listAdapter;
	private ImageCrop imageCrop ;
	private ImageLoader imageLoader;
	
	private static final int NAME = 0;
	private static final int ADDRESS = 1;
	private static final int SIGN = 2;
	private static final int INTEREST = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_data);
		
		initView();
		getUserData();
	}

	private void initView() {
		imageCrop = new ImageCrop(this);
		imageLoader = ImageLoader.getInstance();
		
		ic_rl = (RelativeLayout) findViewById(R.id.ic_edit_rl);
		user_ic = (ImageView) findViewById(R.id.ic_edit_data_iv);
		edit_data_lv = (ListView) findViewById(R.id.edit_data_lv);
		
//		View.OnClickListener date_dialog = new BirthOnClickListener(0);
		
		ic_rl.setOnClickListener(this);
		edit_data_lv.setOnItemClickListener(this);
		
	}
	private void initListData() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.user_ic, user_ic);
		
		String info[] = {user.user_name,user.genGender(),user.birth,user.address,user.signature,user.interest};

		for (int i = 0; i < 6; i++) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("text", list_array[i]);
			map.put("info", info[i]);
			list.add(map);
		}

		listAdapter = new SimpleAdapter(this, list, R.layout.user_data_list_item, new String[]{"text","info"},
				new int[]{R.id.text_edit_data_tv,R.id.info_edit_data_tv});
		edit_data_lv.setAdapter(listAdapter);
	}
	
	private void getUserData() {
		
		Map<String,String> map = PulltoMap();
		if (map == null) {
			return;
		}
		
		UserRequest userCenterRequest = new UserRequest(Method.POST, HttpUtils.initArticleURL("user_info"),
				map, new Listener<User>() {

					@Override
					public void onResponse(User response) {
						if (response != null) {
							
							user = response;
							initListData();
						}
					}
				}, null);

		requestQueue.add(userCenterRequest);
		requestQueue.start();
	}
	
	/** 得到user_idtoMap */
	private Map<String, String> PulltoMap() {
		
		if (BlueApplication.isLogin(this)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_id", LoginUtils.getLoginUserid(this));
			return map;
		}else
			return null;


	}

	/** 修改用户性别的dialog */
	private void editGenderDialog() {

		final String gender[] = { "男", "女" };
		builder = new AlertDialog.Builder(this);

		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.gender);
		builder.setSingleChoiceItems(gender, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create();
		builder.show();

	}
	/**出生日期的dialog*/
	private void onCreateDialog() {
		Calendar calendar = Calendar.getInstance(); 
		DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
				
			}
		};
		
		Dialog dialog = new DatePickerDialog(this,  
                dateSetListener,  
                calendar.get(Calendar.YEAR),  
                calendar.get(Calendar.MONTH),  
                calendar.get(Calendar.DAY_OF_MONTH)); 
	}

	

	private Map<String,String> iconText(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", user.user_id);
		map.put("url", HttpUtils.initArticleURL("user_update"));
		return map;
	}
	
	private Map<String,String> iconImg(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("image", icon_uri);
		return map;
	}
	
	private void getUpdateInfo(int requestCode,int resultCode,String content) {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("info", content);
		
		if (resultCode == Activity.RESULT_OK) {
			list.set(position, map);
		}
		
		listAdapter.notifyAll();
	}
	
	
	@Override
	public void onClick(View v) {
		
		index = 0;
		
		switch (v.getId()) {
		case R.id.ic_edit_rl:
			imageCrop.showDialog();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		index = 1;
		this.position = position;
		Intent intent = new Intent(this,EditInfoActivity.class);
		switch (position) {
		//昵称
		case 0:
			intent.putExtra("index", 0);
			startActivityForResult(intent, NAME);
			break;
		//性别
		case 1:
			editGenderDialog();
			break;
		//出生日期
		case 2:
			onCreateDialog();
	        break;
	    //地区
	    case 3:
	    	intent.putExtra("index", 1);
	    	startActivityForResult(intent, ADDRESS);
	    	break;
	    //个性签名
	    case 4:
	    	intent.putExtra("index", 2);
	    	startActivityForResult(intent, SIGN);
	    	break;
	    //兴趣爱好
	    case 5:
	    	intent.putExtra("index", 3);
	    	startActivityForResult(intent, INTEREST);
	    	break;

		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (data == null) {
			return;
		}
		
		switch (index) {
		//头像
		case 0:
			
			Uri picUri = imageCrop.onActivityResult(requestCode, resultCode, data);
			Log.i("pic url ", "返回的Uri = " + picUri);
			PostAsync postAsync = new PostAsync(this);
			if (picUri != null && !picUri.equals(picUri.EMPTY)) {
				String picUri_path = picUri.getPath();
				user_ic.setScaleType(ScaleType.FIT_CENTER);
				imageLoader.displayImage(picUri.toString(), user_ic);
				icon_uri = picUri_path;
				
				postAsync.execute(iconImg(),iconText());
			}
			break;
		//list
		case 1:
			getUpdateInfo(requestCode,resultCode,data.getStringExtra("info"));
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	/**
     * 成员内部类,此处为提高可重用性，也可以换成匿名内部类
     */
//    private class BirthOnClickListener implements View.OnClickListener {
//    	
//    	private int dialogId = 0;	//默认为0则不显示对话框
//
//    	public BirthOnClickListener(int dialogId) {
//    		this.dialogId = dialogId;
//    	}
//		@Override
//		public void onClick(View view) {
//			showDialog(dialogId);
//		}
//    	
//    }

}
