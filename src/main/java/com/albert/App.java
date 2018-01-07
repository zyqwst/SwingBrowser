package com.albert;

import java.awt.EventQueue;
import java.awt.print.PrinterJob;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import com.albert.ui.MainFrame;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		 HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
         DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

		PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		PrintService[] pss = PrinterJob.lookupPrintServices(); 
		System.out.println(printService);
		if(1==1)return;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.init();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
