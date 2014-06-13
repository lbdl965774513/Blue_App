package com.blue.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;


/**
 * @author SLJM
 * @create 2014-4-1
 * @desc 用户的发帖位置
 *
 */
public class UserLocation {
	
	private Context context;
	private static final String TAG ="UserLocation";
	
	public UserLocation(Context context){
		
		this.context = context;
	}
	public String getCurrentLocation(){
		
		//1.声明并实例化一个LocationManager对象，这个类提供了关于获取位置信息的服务，用于管理Android的定位服务。
		//这个类允许应用定期的更新地理位置信息，同时允许当到达指定地点时候激活相关的Intent
		
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		//2.声明并设置Criteria对象。这个类是用来选择合适的provider的，他设置了一些条件，通过对这些条件进行筛选，
		//系统得到最优的provider对象。条件有：精度，耗电量，海拔，方位，速度以及付费情况等
		
		Criteria criteria=new Criteria(); 
	    criteria.setAccuracy(Criteria.ACCURACY_FINE); //精度要求：ACCURACY_FINE(高)ACCURACY_COARSE(低)
	    criteria.setAltitudeRequired(false);              // 不要求海拔信息
	    criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH); //方位信息的精度要求：ACCURACY_HIGH(高)ACCURACY_LOW(低)
	    criteria.setBearingRequired(false);             // 不要求方位信息
        criteria.setCostAllowed(true);                     // 是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 对电量的要求  (HIGH、MEDIUM)
	     
	     //3.根据criteria的条件获取最合适的provider。第二个参数是指是否只返回当前处于激活状态的provider
        
        String provider=locationManager.getBestProvider(criteria,true);
        
        //4.设置监听器，并设置有关更新操作的一些条件（时间间隔，距离间隔），监听器会定期的更新位置信息。
       //这里设置了使用之前得到的最优provider，更新位置操作的时间间隔为1000毫秒，距离间隔为5米，绑定LListener监听器。

//        location.requestLocationUpdates(provider, 1000, 5, new LListener());
        
        //5.获取地理位置信息。返回provider最近获得的位置信息。

        Log.i(TAG, "定位方式"+provider);
        Location loc = locationManager.getLastKnownLocation(provider);
        
        return loc.toString();
	
	}
}
