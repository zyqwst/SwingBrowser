package com.albert;

import com.albert.pojo.ConfigEntity;
import com.albert.utils.JsonUtil;
import com.albert.utils.MyException;
import com.google.gson.Gson;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) throws  MyException {
    	ConfigEntity config = new ConfigEntity();
    	config.setUrl("http://localhost:9000/ZYYK/");
    	Gson gson = JsonUtil.getGson();
    	JsonUtil.writeJson(gson.toJson(config));
	}
}
