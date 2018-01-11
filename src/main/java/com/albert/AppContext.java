/**
 * 
 */
package com.albert;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.albert.pojo.ConfigEntity;
import com.albert.utils.JsonUtil;
import com.albert.utils.MessageUtil;
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
	private Map<String,String> i18nMap;
	private final Log log = LogFactory.getLog(getClass());
	private synchronized void initConfig() {
		try {
			String str = JsonUtil.readJson();
			ConfigEntity config = JsonUtil.getGson().fromJson(str, ConfigEntity.class);
			this.config = config;
			checkConfig(config);
		} catch (MyException e) {
			e.printStackTrace();
			log.error(MessageUtil.formatMessage(MessageUtil.initError, MessageUtil.configFile),e);
		}
	}
	private synchronized void initI18n() {
		try {
			Map<String,String> map = MessageUtil.getMessages();
			this.i18nMap = map;
		} catch (Exception e) {
			log.error("i18n init failure");
		}
	}
	private static class LazyHolder {    
       private static final AppContext INSTANCE = new AppContext();    
    }    
    private AppContext (){
    	initConfig();
    	initI18n();
    }    
    public static final AppContext getInstance() {    
       return LazyHolder.INSTANCE;    
    }    
	public void checkConfig(ConfigEntity config) throws MyException{
		if(StringUtils.isBlank(config.getUrl())) throw new MyException("Host URL is blank,you have to set it;");
	}
	public ConfigEntity getConfig() {
		return config;
	}
	public Map<String,String> getI18nMap() {
		return i18nMap;
	}
}
