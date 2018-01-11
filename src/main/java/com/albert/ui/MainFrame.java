package com.albert.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.lang.StringUtils;

import com.albert.AppContext;
import com.albert.bridge.PrintScriptContext;
import com.albert.utils.MessageUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MainFrame extends JFrame implements ConsoleListener,MenuListener{
	private static final long serialVersionUID = -487732622394297438L;
	private JMenu settingMenu ;
	public MainFrame(){
		super();
		initMainFrame();
	}
	private void initMainFrame(){
		initAppearance();
		initMenu();
	}
	
	private void initAppearance() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0,0,screenSize.width,screenSize.height);
        setLocationRelativeTo(null);
    }
   private void initMenu(){
	   	JMenuBar jb = new JMenuBar();
		settingMenu = new JMenu(MessageUtil.getMessage(MessageUtil.settingMenu));
		settingMenu.addMenuListener(this);
		jb.add(settingMenu);
		setJMenuBar(jb);
   }
   public void load(){
	   BrowserPreferences.setUserAgent(MessageUtil.userAgent);
       Browser browser = new Browser();
       final BrowserView view = new BrowserView(browser);
       String url = AppContext.getInstance().getConfig().getUrl();
       if(StringUtils.isBlank(url)){
    	   JOptionPane.showMessageDialog(this, "请在<设置>菜单中填写服务器地址","页面加载失败",JOptionPane.ERROR_MESSAGE);
       }else{
    	   browser.loadURL(url);  
       }
       browser.addScriptContextListener(new ScriptContextAdapter() {
           @Override
           public void onScriptContextCreated(ScriptContextEvent event) {
               Browser browser = event.getBrowser();
               JSValue window = browser.executeJavaScriptAndReturnValue("window");
               window.asObject().setProperty("printContext", new PrintScriptContext());
           }
       });
       add(view, BorderLayout.CENTER);
       browser.setContextMenuHandler(new MyContextMenuHandler(view));
       browser.addConsoleListener(this);
   }
	@Override
	public void onMessage(ConsoleEvent event) {
		System.out.println(event.getMessage());
	}
	@Override
	public void menuSelected(MenuEvent e) {
		if(e.getSource()==settingMenu){
			System.out.println("点击设置");
		}
	}
	@Override
	public void menuDeselected(MenuEvent e) {
		
	}
	@Override
	public void menuCanceled(MenuEvent e) {
		
	}  
} 