package com.albert.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import com.albert.AppContext;
import com.albert.bridge.PrintScriptContext;
import com.albert.utils.MessageUtil;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.SavePageType;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MainFrame extends JFrame implements ConsoleListener,ActionListener{
	private static final long serialVersionUID = -487732622394297438L;
	private JMenu menuHome ;
	private JMenuItem item1;
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
    }
   private void initMenu(){
	   	Map<String,String> kv = AppContext.getInstance().getI18nMap();
	   	JMenuBar jb = new JMenuBar();
	   	menuHome = new JMenu(kv.get(MessageUtil.menuHome));
		item1 = new JMenuItem(kv.get(MessageUtil.settingMenu));
		item1.setAccelerator(KeyStroke.getKeyStroke('S',java.awt.Event.ALT_MASK));
		item1.addActionListener(this);
		menuHome.add(item1);
		jb.add(menuHome);
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
       browser.addLoadListener(new LoadAdapter() {
           @Override
           public void onFinishLoadingFrame(FinishLoadingEvent event) {
               if (event.isMainFrame()) {
               	event.getBrowser().executeJavaScript(
                           "window.scrollTo(document.body.scrollWidth, " +
                           "5000);");
               	try {
						Thread.sleep(1000);
						String filePath = "C:\\Users\\Administrator\\Desktop\\fff\\index.html";
						String dirPath = "C:\\Users\\Administrator\\Desktop\\fff\\resources";
						event.getBrowser().saveWebPage(filePath, dirPath, SavePageType.COMPLETE_HTML);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
               }
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==item1){
			AppContext.getInstance().getSettingDialog(this).setVisible(true);;
		}
	}
	
} 