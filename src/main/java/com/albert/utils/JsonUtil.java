/**
 * 
 */
package com.albert.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** 
* @ClassName: JsonUtil 
* @Description: 
* @author albert
* @date 2018年1月10日 下午4:34:25 
*  
*/
public class JsonUtil {
	public final static String path =  JsonUtil.class.getResource("/sbconfig.json").getPath();
	
	public static Gson getGson(){
		return  new GsonBuilder()
		        .setPrettyPrinting()
		        .create();
	}
	
	public static String readJson() throws MyException{
		return readJson(path);
	}
	/**
	 * 从文件读取json
	 * @param path
	 * @return
	 * @throws MyException 
	 */
	public static String readJson(String path) throws MyException{
		StringBuffer str;
		try {
			File file = new File (path); 
			FileReader fileReader=new FileReader(file); 
			BufferedReader bufReader=new BufferedReader(fileReader);
			str = new StringBuffer();
			String line = null;
			while((line=bufReader.readLine())!=null){
				str.append(line);
			}
			bufReader.close();
			fileReader.close();
			return str.toString();
		} catch (FileNotFoundException e) {
			throw new MyException("文件未找到："+path);
		} catch (IOException e) {
			throw new MyException(e);
		}
	}
	
	public static void writeJson(String json) throws MyException{
		writeJson(path, json);
	}
	/**
	 * json字符串写入文件
	 * @param path
	 * @param json
	 * @throws MyException
	 */
	public static void writeJson(String path,String json) throws MyException{
		try {
			File file = new File(path);
			PrintWriter writer = new PrintWriter(file);
			writer.write(json);
			writer.close();
		} catch (FileNotFoundException e) {
			throw new MyException("文件未找到："+path);
		}
	}
}
