package com.blue.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;


/**
 * @author SLJM
 * @create 2014-4-1
 * @desc �û��ķ���λ��
 *
 */
public class UserLocation {
	
	private Context context;
	private static final String TAG ="UserLocation";
	
	public UserLocation(Context context){
		
		this.context = context;
	}
	public String getCurrentLocation(){
		
		//1.������ʵ����һ��LocationManager����������ṩ�˹��ڻ�ȡλ����Ϣ�ķ������ڹ���Android�Ķ�λ����
		//���������Ӧ�ö��ڵĸ��µ���λ����Ϣ��ͬʱ��������ָ���ص�ʱ�򼤻���ص�Intent
		
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		//2.����������Criteria���������������ѡ����ʵ�provider�ģ���������һЩ������ͨ������Щ��������ɸѡ��
		//ϵͳ�õ����ŵ�provider���������У����ȣ��ĵ��������Σ���λ���ٶ��Լ����������
		
		Criteria criteria=new Criteria(); 
	    criteria.setAccuracy(Criteria.ACCURACY_FINE); //����Ҫ��ACCURACY_FINE(��)ACCURACY_COARSE(��)
	    criteria.setAltitudeRequired(false);              // ��Ҫ�󺣰���Ϣ
	    criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH); //��λ��Ϣ�ľ���Ҫ��ACCURACY_HIGH(��)ACCURACY_LOW(��)
	    criteria.setBearingRequired(false);             // ��Ҫ��λ��Ϣ
        criteria.setCostAllowed(true);                     // �Ƿ�������
        criteria.setPowerRequirement(Criteria.POWER_LOW); // �Ե�����Ҫ��  (HIGH��MEDIUM)
	     
	     //3.����criteria��������ȡ����ʵ�provider���ڶ���������ָ�Ƿ�ֻ���ص�ǰ���ڼ���״̬��provider
        
        String provider=locationManager.getBestProvider(criteria,true);
        
        //4.���ü��������������йظ��²�����һЩ������ʱ�����������������������ᶨ�ڵĸ���λ����Ϣ��
       //����������ʹ��֮ǰ�õ�������provider������λ�ò�����ʱ����Ϊ1000���룬������Ϊ5�ף���LListener��������

//        location.requestLocationUpdates(provider, 1000, 5, new LListener());
        
        //5.��ȡ����λ����Ϣ������provider�����õ�λ����Ϣ��

        Log.i(TAG, "��λ��ʽ"+provider);
        Location loc = locationManager.getLastKnownLocation(provider);
        
        return loc.toString();
	
	}
}
