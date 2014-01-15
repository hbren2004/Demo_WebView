package com.ebusiness.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import android.util.Log;
import android.util.Xml;

public class ParseXmlUtils {
	
	public static void parseConfigData()throws Exception{
		// File file = new File(Constants._cfgFile);
		// InputStream is = new FileInputStream(file);
		// XmlPullParser parser = Xml.newPullParser();
		// parser.setInput(is,"UTF-8");
		// int eventType = parser.getEventType();
		// while(eventType != XmlPullParser.END_DOCUMENT){
		// switch(eventType){
		// case XmlPullParser.START_DOCUMENT : break;
		// case XmlPullParser.START_TAG :
		// if("vedioType".equals(parser.getName())){
		// Constants._vedioType = parser.nextText();
		// }else if("vedioRtsp".equals(parser.getName())){
		// Constants._vedioRtsp = parser.nextText();
		// }
		// break;
		// case XmlPullParser.END_TAG : break;
		// }
		// eventType = parser.next();
		// }
		// is.close();
	}
}
