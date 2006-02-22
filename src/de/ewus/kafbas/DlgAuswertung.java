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
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.jfree.report.ElementAlignment;
import org.jfree.report.JFreeReport;
import org.jfree.report.JFreeReportBoot;
import org.jfree.report.ReportProcessingException;
import org.jfree.report.elementfactory.TextFieldElementFactory;
import org.jfree.report.modules.gui.base.PreviewDialog;
import org.jfree.ui.FloatDimension;
import java.awt.geom.Point2D;



public class DlgAuswertung extends JDialog {

    private static final Logger logger = Logger.getLogger(KafbasGUI.class.getName());
    private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel = null;
	private JButton jButtonDruck = null;
	private JButton jButtonSchliessen = null;
	private Auswertung datenquelle = null;
	/**
	 * This is the default constructor
	 */
	public DlgAuswertung(JFrame owner, Auswertung datenquelle) {
		super(owner);
		this.datenquelle = datenquelle;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 500);
		this.setTitle("Auswertung");
		this.setContentPane(getJContentPane());
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
			jButtonDruck.setText("Drucken");
			jButtonDruck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					drucken();
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

	    TextFieldElementFactory factory = new TextFieldElementFactory();
	    factory.setName("T1");
	    factory.setAbsolutePosition(new Point2D.Float(0, 0));
	    factory.setMinimumSize(new FloatDimension(150, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("Column1");
	    report.getItemBand().addElement(factory.createElement());

	    factory = new TextFieldElementFactory();
	    factory.setName("T2");
	    factory.setAbsolutePosition(new Point2D.Float(200, 0));
	    factory.setMinimumSize(new FloatDimension(150, 12));
	    factory.setColor(Color.black);
	    factory.setHorizontalAlignment(ElementAlignment.LEFT);
	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
	    factory.setNullString("-");
	    factory.setFieldname("Column2");
	    report.getItemBand().addElement(factory.createElement());
	    return report;
	}
	
}
