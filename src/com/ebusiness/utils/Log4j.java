package com.ebusiness.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class Log4j {
	
	public final static String path = Constants._sys._appDir+"logs/log_"+DateUtils.getDate()+".text";
	private static File file = new File(path);
	
    public static void i(String TAG,String content){
    	Log.i(TAG + " whty ", content);
		FileOutputStream fileOStream;
		try {
			File dir = new File(file.getParent());
			if(!dir.exists()){
				dir.mkdirs();
			}
			if(!file.exists()){
				 try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fileOStream = new FileOutputStream(file,true);
			String log = "["+DateUtils.getCurrentTime(DateUtils.DEFAULT_DATE_FORMAT)+"]"+TAG+"  "+content +"\r\n";
			fileOStream.write(log.getBytes());
			fileOStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
	}

}
