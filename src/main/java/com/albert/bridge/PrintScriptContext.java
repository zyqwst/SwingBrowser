/**
 * 
 */
package com.albert.bridge;

import com.albert.utils.MyException;

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
		System.out.println(params);
	}
}
