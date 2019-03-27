package com.albert;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.SavePageType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class SaveWebPageSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        
        JFrame frame = new JFrame();
        frame.setBounds(0,0,screenSize.width,screenSize.height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                	event.getBrowser().executeJavaScript(
                            "window.scrollTo(document.body.scrollWidth, " +
                            "3000);");
                	String filePath = "C:\\Users\\Administrator\\Desktop\\fff\\index.html";
					String dirPath = "C:\\Users\\Administrator\\Desktop\\fff\\resources";
					event.getBrowser().saveWebPage(filePath, dirPath, SavePageType.COMPLETE_HTML);
                }
            }
        });
        
        browser.loadURL("https://detail.1688.com/offer/576277585710.html?spm=b26110380.sw1688.mof001.43.22a748f4eIfqmX&tracelog=p4p&clickid=664e0d7f8db741d9a8f356aae03b1394&sessionid=b283db537c30f88d74dde7a321440ddd");
    }
}