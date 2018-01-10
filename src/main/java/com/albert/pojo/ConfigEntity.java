/**
 * 
 */
package com.albert.pojo;

import java.util.LinkedHashMap;

/** 
* @ClassName: ConfigEntity 
* @Description: 
* @author albert
* @date 2018年1月10日 下午4:31:24 
*  
*/
public class ConfigEntity {
	
	private LinkedHashMap<String,String> jasperPrinters;
	
	private String url;

	public LinkedHashMap<String,String> getJasperPrinters() {
		return jasperPrinters;
	}

	public void setJasperPrinters(LinkedHashMap<String,String> jasperPrinters) {
		this.jasperPrinters = jasperPrinters;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
