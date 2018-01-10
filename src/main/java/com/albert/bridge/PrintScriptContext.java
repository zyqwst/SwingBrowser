/**
 * 
 */
package com.albert.bridge;

import com.albert.utils.HttpRequestUtil;
import com.albert.utils.JasperUtil;
import com.albert.utils.MyException;
import com.sy.domain.ResponseEntity;

import net.sf.jasperreports.engine.JasperPrint;

/** 
* @ClassName: PrintScriptContext 
* @Description: 
* @author albert
* @date 2018年1月3日 下午4:10:32 
*  
*/
public class PrintScriptContext implements ScriptContext {

	@Override
	public void call(String params) throws MyException {
		try {
			Object obj = HttpRequestUtil.request(params);
			ResponseEntity re = (ResponseEntity) obj;
			if(re.getStatus()==-1)throw new Exception(re.getMsg());
			JasperPrint print = (JasperPrint) re.getObj();
			JasperUtil.preview(print);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
