package com.albert;

import java.util.LinkedHashSet;
import java.util.Set;

import com.albert.pojo.ConfigEntity;
import com.albert.pojo.ConfigEntity.JasperForPrinter;
import com.albert.utils.JsonUtil;
import com.albert.utils.MyException;
import com.google.gson.Gson;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) throws  MyException {
    	Set<JasperForPrinter> printer = new LinkedHashSet<JasperForPrinter>();
    	ConfigEntity config = new ConfigEntity();
    	config.setUrl("http://localhost:9000/ZYYK/");
    	config.setJasperPrinters(printer);
    	printer.add(config.genPrinter("入库单", "打印机1", 1));
    	Gson gson = JsonUtil.getGson();
    	String str = gson.toJson(config);
    	System.out.println(str);
    	JsonUtil.writeJson(str);
	}
}
