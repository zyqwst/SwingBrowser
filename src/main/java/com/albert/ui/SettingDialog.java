/**
 * 
 */
package com.albert.ui;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;

import com.albert.AppContext;
import com.albert.utils.MessageUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

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
		Browser browser = new Browser();
	     BrowserView view = new BrowserView(browser);
	     String url = getClass().getResource("/pages/print.html").toString();
	     System.out.println(url);
	     browser.loadURL(url);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(view, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(view, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
		 
	}
	@Override
	public void customizedInit() {
		super.customizedInit();
		setTitle(AppContext.getInstance().getI18nMap().get(MessageUtil.settingMenu));
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
