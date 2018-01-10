package com.albert.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 * jasperSoft报表生成 调用工具
 * 
 * @author Albert
 * 
 */
public class JasperUtil {

	public static void preview(JasperPrint jasperPrint ) throws MyException {
		try {
			JasperViewer.viewReport(jasperPrint, false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		}
	}
	
	public static void print(JasperPrint jasperPrint ) throws MyException{
	    try {
			long start = System.currentTimeMillis();
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

			PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			
			JRPrintServiceExporter exporter = new JRPrintServiceExporter();
			
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
			configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
			configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
			configuration.setDisplayPageDialog(false);
			configuration.setDisplayPrintDialog(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			System.err.println("Printing time : " + (System.currentTimeMillis() - start));
		} catch (JRException e) {
			e.printStackTrace();
			throw new MyException(e);
		}
	  }
	public static void main(String[] args) throws JRException {
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		InputStream inReport = null;
		try {
			inReport = new FileInputStream("resources/outorder_blank.jrxml");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("param", "22");
			jasperReport = JasperCompileManager.compileReport(inReport);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params);
		} catch (JRException e) {
			e.printStackTrace();
		}
		// 预览
		JasperViewer.setDefaultLookAndFeelDecorated(false);
		JasperViewer.viewReport(jasperPrint, false);
	}
}
