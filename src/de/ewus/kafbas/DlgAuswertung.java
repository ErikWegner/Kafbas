/*
 * Ein Dialog zur Auswertung.
 * 
 * This file is part of Kafbas.
 * 
 * Kafbas is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package de.ewus.kafbas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jfree.report.ElementAlignment;
import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.ReportProcessingException;
import org.jfree.report.elementfactory.TextFieldElementFactory;
import org.jfree.report.modules.gui.base.PreviewDialog;
import org.jfree.ui.FloatDimension;



@SuppressWarnings("serial")
public class DlgAuswertung extends JDialog {

    private static final Logger logger = Logger.getLogger(DlgAuswertung.class.getName());
    private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel = null;
	private JButton jButtonDruck = null;
	private JButton jButtonSchliessen = null;
	private Auswertung datenquelle = null;
    private String austauschpfad = null;
	/**
	 * This is the default constructor
	 */
	public DlgAuswertung(JFrame owner, Auswertung datenquelle, String austauschpfad) {
		super(owner);
		this.datenquelle = datenquelle;
		this.austauschpfad = austauschpfad;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setSize(800, 500);
		this.setTitle("Auswertung");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = this.getSize();
		this.setLocation((screen.width - frame.width) / 2,(screen.height - frame.height) / 2);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable(datenquelle);
		}
		jTable.setDefaultRenderer(Double.class, new WaehrungsSpalteRenderer());
		return jTable;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getJButtonDruck(), null);
			jPanel.add(getJButtonSchliessen(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButtonDruck	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDruck() {
		if (jButtonDruck == null) {
			jButtonDruck = new JButton();
			jButtonDruck.setText("Export HTML");
			jButtonDruck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    //drucken();
				    exportHTML();
				}
			});
		}
		return jButtonDruck;
	}

	private void drucken() {
		JFreeReportBoot.getInstance().start();
		JFreeReport report = createReportDefinition();
		report.setData(datenquelle);
		try {
			PreviewDialog preview = new PreviewDialog(report);
			preview.pack();
			preview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			preview.setModal(true);
			preview.setVisible(true);
		}
		catch (ReportProcessingException e)
		{ logger.warn(e); }
	}
	
    private void exportHTML() {
	logger.debug("exportHTML");
	String datei = austauschpfad + File.separator + "auswertung.html";
	try {
	    String export;
	    logger.info("Austauschdatei=" + datei);
	    BufferedWriter bw = new BufferedWriter(
		new FileWriter(
		    new File(datei)));
	    //schreide Daten
	    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	    bw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n");
	    bw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" >\n");
	    bw.write(" <head>\n");
	    bw.write("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
	    bw.write("  <meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n");
	    bw.write("  <style type=\"text/css\">\n");
	    bw.write("   .tdr {text-align:right;}\n");
	    bw.write("   .tdsr {text-align:right;font-weight:bold;}\n");
	    bw.write("   .tds  {font-weight:bold;}\n");
	    bw.write("  </style>\n");
	    bw.write("  <title>Auswertung Kafbas</title>\n");
	    bw.write(" </head>\n");
	    bw.write(" <body>\n");
	    bw.write("  <h1>Abrechnung " + heute() + "</h1>");
	    bw.write("  <table border=\"1\">\n");
	    int spalten = datenquelle.getColumnCount();
	    int zeilen = datenquelle.getRowCount();
	    String zellinhalt = null;
	    WaehrungsSpalteRenderer renderer = new WaehrungsSpalteRenderer();
	    //Überschrift
	    bw.write("   <tr>\n");
	    for (int c1=0; c1<spalten; c1++)
		bw.write("    <th>" + datenquelle.getColumnName(c1) + "</th>\n");
	    bw.write("   </tr>\n");
	    //Datenzeilen
	    for (int c_row = 0; c_row < zeilen-1; c_row++) {
		bw.write("   <tr>\n");
		for (int c_col=0; c_col < spalten; c_col++) {
		    zellinhalt = datenquelle.getValueAt(c_row, c_col).toString();
		    if (c_col > 0) renderer.setValue(Double.parseDouble(zellinhalt));
		    bw.write("    <td" + 
			     ( c_col > 0 ? " class=\"tdr\"" : "") + ">" + 
			     (c_col == 0 ? zellinhalt : renderer.getText()) + 
			     "</td>\n");
		}
		bw.write("   </tr>\n");
	    }
	    //Summenzeile
	    bw.write("   <tr>\n");
	    for (int c_col=0; c_col < spalten; c_col++) {
		zellinhalt = datenquelle.getValueAt(zeilen-1, c_col).toString();
		if (c_col > 0) renderer.setValue(Double.parseDouble(zellinhalt));
		bw.write("    <td class=\"" + 
			 ( c_col > 0 ? "tdsr" : "tds") + "\">" +
			 (c_col == 0 ? zellinhalt : renderer.getText()) + 
			 "</td>\n");
	    }
	    bw.write("   </tr>\n");
	    bw.write("   </tr>\n");
	    bw.write("  </table>\n");
	    bw.write(" </body>\n");
	    bw.write("</html>");
	    bw.close();
	    JOptionPane.showMessageDialog(this, "Ausgabe in Datei " + datei + " erfolgreich.");
	} catch (FileNotFoundException e) {
	    logger.error("FileNotFoundException", e);
	} catch (IOException e) {
	    logger.error("IOException", e);
	}
    }

	/**
	 * This method initializes jButtonSchliessen	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSchliessen() {
		if (jButtonSchliessen == null) {
			jButtonSchliessen = new JButton();
			jButtonSchliessen.setText("Schließen");
			jButtonSchliessen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return jButtonSchliessen;
	}

	private JFreeReport createReportDefinition() {
		 final JFreeReport report = new JFreeReport();
	    report.setName("Kafbasreport");

	    TextFieldElementFactory factory;
	    factory = new TextFieldElementFactory();
	    factory.setName("T1");
	    factory.setAbsolutePosition(new Point2D.Float(0, 0));
	    factory.setMinimumSize(new FloatDimension(45, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.LEFT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("Verkäufer");
	    report.getItemBand().addElement(factory.createElement());

	    factory = new TextFieldElementFactory();
	    factory.setName("T2");
	    factory.setAbsolutePosition(new Point2D.Float(50, 0));
	    factory.setMinimumSize(new FloatDimension(60, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("Summe");
	    report.getItemBand().addElement(factory.createElement());

	    factory = new TextFieldElementFactory();
	    factory.setName("T3");
	    factory.setAbsolutePosition(new Point2D.Float(120, 0));
	    factory.setMinimumSize(new FloatDimension(60, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("20%");
	    report.getItemBand().addElement(factory.createElement());

	    factory = new TextFieldElementFactory();
	    factory.setName("T4");
	    factory.setAbsolutePosition(new Point2D.Float(190, 0));
	    factory.setMinimumSize(new FloatDimension(60, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("80%");
	    report.getItemBand().addElement(factory.createElement());

	    factory = new TextFieldElementFactory();
	    factory.setName("T5");
	    factory.setAbsolutePosition(new Point2D.Float(260, 0));
	    factory.setMinimumSize(new FloatDimension(60, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("Kasse1");
	    report.getItemBand().addElement(factory.createElement());

	    
	    return report;
	}
	
    private String heute() {
	DateFormat dateFormatter;

	dateFormatter = DateFormat.getInstance();
	return dateFormatter.format(new Date());
    }

}
