/**
 * 
 */
package com.albert.ui;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;

/** 
* @ClassName: SettingDialog 
* @Description: 
* @author albert
* @date 2018年1月11日 上午9:23:49 
*  
*/
public class SettingDialog extends BaseDialog {
	public SettingDialog(Window window) {
		super(window);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 619, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 513, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
	}
	@Override
	public void customizedInit() {
		super.customizedInit();
		setTitle("设置");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1597310160254338256L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingDialog dialog = new SettingDialog(null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
