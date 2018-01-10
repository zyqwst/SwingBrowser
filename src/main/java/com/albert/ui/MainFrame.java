package com.albert.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.albert.bridge.PrintScriptContext;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MainFrame extends JFrame implements ConsoleListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -487732622394297438L;
	private final static String homeUrl = "http://192.168.1.40:9000/ZYYK";
	public void init() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(view, BorderLayout.CENTER);
        setBounds(0,0,screenSize.width,screenSize.height-30);
        setLocationRelativeTo(null);
        browser.loadURL(homeUrl);  
        browser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
            	System.out.println("初始化context");
                Browser browser = event.getBrowser();
                JSValue window = browser.executeJavaScriptAndReturnValue("window");
                window.asObject().setProperty("printContext", new PrintScriptContext());
            }
        });
       
        browser.setContextMenuHandler(new MyContextMenuHandler(view));
        browser.addConsoleListener(this);
    }
   
	@Override
	public void onMessage(ConsoleEvent event) {
		System.out.println(event.getMessage());
	}  
} 