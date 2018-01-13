/**
 * 
 */
package com.albert.utils;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/** 
* @ClassName: I18nUtil 
* @Description: 
* @author albert
* @date 2018年1月11日 上午11:24:05 
*  
*/
public class MessageUtil {
	private static ResourceBundle bundle;
	private static Map<String,String> messages = new HashMap<String,String>();
	static{
		Locale locale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("properties.i18n",locale);
		Enumeration<String> en = bundle.getKeys();
		while(en.hasMoreElements()){
			String string = en.nextElement();
			messages.put(string, bundle.getString(string));
		}
	}
	
	public static String getMessage(String key){
		String s = messages.get(key);
		if(StringUtils.isBlank(s)) return null;
		return s;
	}
	public static String formatMessage(String key,Object... arguments) {
		try {
			String msg = getMessage(key);
			String s = MessageFormat.format(msg, arguments);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Map<String,String> getMessages(){
		return messages;
	}
	public final static String userAgent = "SHUANGYI";
	
    public final static String configFile = "configFile";
	public final static String initError = "init.error";
	public final static String initSuccess = "init.success";
	public final static String serviceError = "service.error";
	public final static String settingMenu = "com.albert.ui.MainFrame.JMenu.SETTING";
	public final static String menuHome = "com.albert.ui.MainFrame.JMenu";
}
