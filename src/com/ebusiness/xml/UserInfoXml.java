package com.ebusiness.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ebusiness.utils.FileUtils;

public class UserInfoXml{
	public static String createXml(String userName)throws Exception{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("Req_UserInfo");
		Element user = root.addElement("UserName");
		user.setText(userName);
		return FileUtils.domToString(document);
	}
}
