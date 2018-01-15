package com.albert.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.ContextMenuHandler;
import com.teamdev.jxbrowser.chromium.ContextMenuParams;

public class MyContextMenuHandler implements ContextMenuHandler {  
    	  

        private final JComponent component;

        MyContextMenuHandler(JComponent parentComponent) {
            this.component = parentComponent;
        }

        public void showContextMenu(final ContextMenuParams params) {
            final JPopupMenu popupMenu = new JPopupMenu();
            if (!params.getLinkText().isEmpty()) {
                popupMenu.add(createMenuItem("在新窗口打开",true, new Runnable() {
                    public void run() {
                        String linkURL = params.getLinkURL();
                        System.out.println("linkURL = " + linkURL);
                    }
                }));
            }

            final Browser browser = params.getBrowser();
            popupMenu.add(createMenuItem("刷新",true, new Runnable() {
                public void run() {
                    browser.reloadIgnoringCache();
                    browser.getCurrentNavigationEntryIndex();
                }
            }));
        	popupMenu.add(createMenuItem("返回",browser.canGoBack() && browser.getCurrentNavigationEntryIndex()>1, new Runnable() {
                public void run() {
                    browser.goBack();
                }
            }));
        	popupMenu.add(createMenuItem("前进",browser.canGoForward(), new Runnable() {
                public void run() {
                    browser.goForward();
                }
            }));
            final Point location = params.getLocation();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    popupMenu.show(component, location.x, location.y);
                }
            });
        }

		private JMenuItem createMenuItem(String title,Boolean enable, final Runnable action) {
            JMenuItem reloadMenuItem = new JMenuItem(title);
            reloadMenuItem.setEnabled(enable);
            reloadMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    action.run();
                }
            });
            return reloadMenuItem;
        }
    }