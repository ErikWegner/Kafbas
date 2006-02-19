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

	/**
	 * This is the default constructor
	 */
	public DlgAuswertung(JFrame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
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
			jTable = new JTable();
		}
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
