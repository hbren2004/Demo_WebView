package com.ebusiness.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.xmlpull.v1.XmlPullParser;

import com.ebusiness.utils.HttpUtils.MsgParams;
import com.ebusiness.xml.UserInfoXml;

import android.util.Log;
import android.util.Xml;

public class HttpRequest {
	
	private static String TAG = "HttpRequest";
	
	/**
	 * 向服务端请求用户登录
	 * @return 
	 */
	public static Map<String,Object> reqUserLoginAction()throws Exception{
		final Map<String,Object> map = new HashMap<String,Object>();
		HttpUtils.doPost("",UserInfoXml.createXml(""),new MsgParams(){
			public List<NameValuePair> createParams(){
				return null;
			}
			public void dealXML(String responseXml){
				XmlPullParser parser = Xml.newPullParser();
				String result = "0";
				try{
					parser.setInput(new StringReader(responseXml));
					int eventType = parser.getEventType();
					while(eventType != XmlPullParser.END_DOCUMENT && "0".equals(result)){
						switch(eventType){
						case XmlPullParser.START_DOCUMENT : break;
						case XmlPullParser.START_TAG : 
							if("result".equals(parser.getName())){
								result = parser.nextText();
								map.put("result",result);
							}
						case XmlPullParser.END_TAG : 
							map.put("data","");
							break;		 							
						}
						eventType = parser.next();
					}
				}catch(Exception e){
					Log.e(TAG,"reqUserLoginAction Error : "+e);
				}
			}
		});
		return map;
	}	
	
}
