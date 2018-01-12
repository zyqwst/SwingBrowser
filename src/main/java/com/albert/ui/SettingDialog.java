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
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

/** 
* @ClassName: SettingDialog 
* @Description: 
* @author albert
* @date 2018年1月11日 上午9:23:49 
*  
*/
public class SettingDialog extends BaseDialog implements ConsoleListener{
	public SettingDialog(Window window) {
		super(window);
		Browser browser = new Browser();
	    BrowserView view = new BrowserView(browser);
	    String url = getClass().getResource("/pages/print.html").toString();
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
		browser.addConsoleListener(this);
		browser.addScriptContextListener(new ScriptContextAdapter() {
	           @Override
	           public void onScriptContextCreated(ScriptContextEvent event) {
	               Browser browser = event.getBrowser();
	               JSValue window = browser.executeJavaScriptAndReturnValue("window");
	               window.asObject().setProperty("settingContext", new SettingBridge());
	           }
	     });
		browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onDocumentLoadedInMainFrame(LoadEvent event) {
                Browser browser = event.getBrowser();
                WebStorage webStorage = browser.getSessionWebStorage();
                webStorage.setItem("myKey", "马勒戈壁");
            }
        });
	}
	@Override
	public void customizedInit() {
		super.customizedInit();
		setTitle(AppContext.getInstance().getI18nMap().get(MessageUtil.settingMenu));
	}
	private static final long serialVersionUID = -1597310160254338256L;

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
	public class SettingBridge{
		public void selectRow(String json){
			System.out.println(json);
		}
	}
	@Override
	public void onMessage(ConsoleEvent event) {
		System.out.println(event.getMessage());
	}
}
