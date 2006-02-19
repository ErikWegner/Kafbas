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
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFrame;

public class DlgAuswertung extends JDialog {

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
		this.setSize(800, 200);
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
		}
		return jButtonDruck;
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

}
