/**
 * 
 */
package com.albert.ui;

import java.awt.Window;

import javax.swing.JDialog;

/** 
* @ClassName: BaseDialog 
* @Description: 
* @author albert
* @date 2018年1月11日 上午9:01:59 
*  
*/
public abstract class BaseDialog extends JDialog {
	private static final long serialVersionUID = 1448524626579812884L;
	public BaseDialog(Window window) {
		super(window);
		setBounds(0,0,600,519);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(window);
        
		customizedInit();
	}
	
	public void customizedInit(){
		//...
	}

}
