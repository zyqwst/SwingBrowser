package com.albert.utils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

public class HttpRequestUtil {

    /**
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     * @throws Exception 
     */
    public static Object request(String httpUrl) throws Exception {
        Object entity = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();// 正常访问
            connection.setConnectTimeout(10000);
            connection.connect();
            is = connection.getInputStream();
            ois = new ObjectInputStream(is);
            entity = ois.readObject();
        } catch (SocketException e) {
            throw new Exception("Connection timed out: connect");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
        	if(is!=null)
        	 is.close();
        	if(ois!=null)
             ois.close();
        }
        return entity;
    }
    
    public static void main(String[] args) throws Exception {

        String httpUrl = "http://122.112.220.48:5198/print?method=printdata&&key=aaa";
        Object en = HttpRequestUtil.request(httpUrl);
        System.out.println(en);
    }

}