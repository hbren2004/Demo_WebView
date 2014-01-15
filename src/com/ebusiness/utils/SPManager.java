package com.ebusiness.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Color;

public class SPManager {

	public static final String EB_TEXT_SIZE    = "ebTextSize";
	public static final String EB_TEXT_COLOR   = "EBTextColor";
	public static final String EB_READ_BG      = "EBReadBg";
	public static final String EB_CUSTOM_BG    = "EBCustomBg";
	
	public static final String IS_FULL_SCREEN  = "isFullScreen";
	public static final String IS_FIRST_LAUNCH = "isFirstLaunch";
	
	public static final String CONFIG          = "sysinfo.cfg";
	
	//客户端启动时读取配置文件初始化以下静态变量
	public static int      ebTextSize;
	public static int      ebTextColor;
	public static String   ebReadBg;
	public static String   ebCustomBg;
	
	public static boolean  isFullScreen;  //是否全屏
	public static boolean  isFirstLaunch; //是否第一次支持，打开帮助

	
	/**
	 * 初始化设置的相关变量,配置文件中不存在该变量则设置成默认值
	 * @param activity
	 */
	public static void init(Context context) {
		ebTextSize   = getTextSize(context);
		ebTextColor  = getTextColor(context);
		ebReadBg     = getReadBg(context);
		ebCustomBg   = getCustomBg(context);
		isFullScreen = getIsFullScreen(context);
		isFirstLaunch= getIsFirstLaunch(context);
	}

	public static int getTextSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getInt(EB_TEXT_SIZE, 16);
	}
	public static boolean setTextSize(Context context,int size) {
		ebTextSize = size;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putInt(EB_TEXT_SIZE,ebTextSize).commit();
	}
	public static int getTextColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getInt(EB_TEXT_COLOR,Color.BLACK);
	}
	public static boolean setTextColor(Context context,int color) {
		ebTextColor = color;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putInt(EB_TEXT_COLOR,ebTextColor).commit();
	}
	public static String getReadBg(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getString(EB_READ_BG,"0");
	}
	public static boolean setReadBg(Context context,String bg) {
		ebReadBg = bg;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putString(EB_READ_BG,ebReadBg).commit();
	}
	public static String getCustomBg(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getString(EB_CUSTOM_BG,"");
	}
	public static boolean setCustomBg(Context context,String bg) {
		ebCustomBg = bg;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putString(EB_CUSTOM_BG,ebCustomBg).commit();
	}	
		
	
	public static boolean getIsFullScreen(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getBoolean(IS_FULL_SCREEN,false);
	}
	public static boolean setIsFullScreen(Context context,boolean fullScreen){
		isFullScreen = fullScreen;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putBoolean(IS_FULL_SCREEN,isFullScreen).commit();
	}
	public static boolean getIsFirstLaunch(Context context){
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return isFirstLaunch = sp.getBoolean(IS_FIRST_LAUNCH, true);
	}
	public static boolean setIsFirstLaunch(Context context,boolean firstLaunch){
		isFirstLaunch = firstLaunch;
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.edit().putBoolean(IS_FIRST_LAUNCH,isFirstLaunch).commit();
	}

}
