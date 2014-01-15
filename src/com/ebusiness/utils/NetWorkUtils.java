package com.ebusiness.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * �����������
 * */
public class NetWorkUtils {
	public static NetWorkUtils instance = null;
	private WifiManager mWifiManager = null;//���Wifi����
	private ConnectivityManager conn = null;//�����������
	private Context context = null;
	
	private NetWorkUtils(Context context){
		mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		this.context = context;
	}
	
	public static NetWorkUtils getInstance(Context context){
		if(instance == null){
			instance = new NetWorkUtils(context);
		}
		return instance;
	}
	
	public int checkStatues(){
		return mWifiManager.getWifiState();
	}
	
	public boolean checkNetStatues(){
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
	}
	
	public void gotoSetting(){
		new AlertDialog.Builder(context)
		    .setMessage("��⵽����ǰ�����粻���ã��Ƿ�ȥ��������")
		    .setPositiveButton("��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				if(android.os.Build.VERSION.SDK_INT > 10 ){
					intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				}else {
					intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				}
		        context.startActivity(intent);
				}
			})
	        .setNegativeButton("��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
					return;
			}
			}).create().show();	
		}
	
	/**
	 * ��������Ƿ�����
	 * */
	public boolean checkNet(){
		if(checkNetStatues()) return true;
		else {
			gotoSetting();
			return false;
		}
	}
}
