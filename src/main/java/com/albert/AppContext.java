/**
 * 
 */
package com.albert;

import org.apache.commons.lang.StringUtils;

import com.albert.pojo.ConfigEntity;
import com.albert.utils.JsonUtil;
import com.albert.utils.MyException;

/** 
* @ClassName: AppContext 
* @Description: 
* @author albert
* @date 2018年1月10日 下午4:51:40 
*  
*/
public class AppContext {
	private ConfigEntity config;
	
	public synchronized void init() throws MyException{
		try {
			String str = JsonUtil.readJson();
			ConfigEntity config = JsonUtil.getGson().fromJson(str, ConfigEntity.class);
			this.config = config;
			checkConfig(this.config);
		} catch (MyException e) {
			e.printStackTrace();
			throw new MyException("初始化失败："+e.getMessage());
		}
	}
	private static class LazyHolder {    
       private static final AppContext INSTANCE = new AppContext();    
    }    
    private AppContext (){}    
    public static final AppContext getInstance() {    
       return LazyHolder.INSTANCE;    
    }    
	public void checkConfig(ConfigEntity config) throws MyException{
		if(StringUtils.isBlank(config.getUrl())) throw new MyException("Host URL 为空");
	}
	public ConfigEntity getConfig() {
		return config;
	}
	public void setConfig(ConfigEntity config) {
		this.config = config;
	}
}
