package com.albert;

import java.awt.EventQueue;

import com.albert.ui.MainFrame;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.load();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
