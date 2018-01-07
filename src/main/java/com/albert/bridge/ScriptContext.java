/**
 * 
 */
package com.albert.bridge;

import com.albert.utils.MyException;

/** 
* @ClassName: ScriptContext 
* @Description: js 调用  java
* @author albert
* @date 2018年1月3日 下午4:04:31 
*  
*/
public interface ScriptContext {
	
	public void call(String params) throws MyException;
}
