/**
 * 
 */
package de.ewus.kafbas;

import org.apache.log4j.Logger;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * @author Erik Wegner
 *
 */
public class KafbasGUI extends JFrame implements WindowListener, KeyListener {

	private static final Logger logger = Logger.getLogger(KafbasGUI.class.getName());
	private Kassenpostenliste liste = new Kassenpostenliste();
	private String jdbccon = "jdbc:derby:kafbas/kafbasdb";
	private Connection conn;
	public final int numTabellen = 1;
	/**  Nummer der Tabelle f&uuml;r die eigene Kasse */
	public final int TAB_Kassenposten = 0;
	
	private String tabellen[];

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanelNord = null;

	private JLabel jLTextVerkaeufer = null;

	private JLabel jLVerkaeufer = null;

	private JLabel jLTextArtikelpreis = null;

	private JLabel jLArtikelpreis = null;

	private JPanel jPanelSued = null;

	private JLabel jLabel4 = null;

	private JLabel jLGesamtsumme = null;

	private JLabel jLStatuszeile = null;
	
	private Font normalFont = new java.awt.Font("Dialog", java.awt.Font.PLAIN, 24);
	private Font highlightFont = new java.awt.Font("Dialog", java.awt.Font.BOLD, 36);

	/**
	 * This is the default constructor
	 */
	public KafbasGUI() {
		super();
		if (startDB()) initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.addWindowListener(this);
		//this.setSize(442, 319);
		this.setContentPane(getJContentPane());
		this.setTitle("Kafbas - EWUS");
		this.pack();
		addKeyListener(this); 
		setFocusable(true);
		requestFocus();
		aktualisiereFelder();
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
			jContentPane.add(getJPanelNord(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanelSued(), java.awt.BorderLayout.SOUTH);
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
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelNord() {
		if (jPanelNord == null) {
			jLArtikelpreis = new JLabel();
			jLArtikelpreis.setText("0,00");
			jLArtikelpreis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLArtikelpreis.setFont(new java.awt.Font("DialogInput", java.awt.Font.BOLD, 36));
			jLTextArtikelpreis = new JLabel();
			jLTextArtikelpreis.setText("Artikelpreis");
			jLTextArtikelpreis.setFont(normalFont);
			jLVerkaeufer = new JLabel();
			jLVerkaeufer.setText("000");
			jLVerkaeufer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLVerkaeufer.setFont(new java.awt.Font("DialogInput", java.awt.Font.BOLD, 36));
			jLTextVerkaeufer = new JLabel();
			jLTextVerkaeufer.setText("Verkäufer");
			jLTextVerkaeufer.setFont(highlightFont);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setColumns(2);
			gridLayout.setRows(2);
			jPanelNord = new JPanel();
			jPanelNord.setLayout(gridLayout);
			jPanelNord.add(jLTextVerkaeufer, null);
			jPanelNord.add(jLVerkaeufer, null);
			jPanelNord.add(jLTextArtikelpreis, null);
			jPanelNord.add(jLArtikelpreis, null);
		}
		return jPanelNord;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSued() {
		if (jPanelSued == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.insets = new java.awt.Insets(2,0,0,0);
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.ipadx = 129;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 131;
			gridBagConstraints.ipady = 16;
			gridBagConstraints.gridy = 0;
			jLStatuszeile = new JLabel();
			jLStatuszeile.setText("Status");
			jLGesamtsumme = new JLabel();
			jLGesamtsumme.setFont(new java.awt.Font("DialogInput", java.awt.Font.BOLD, 36));
			jLGesamtsumme.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLGesamtsumme.setText("0,00");
			jLabel4 = new JLabel();
			jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel4.setText("Summe");
			jPanelSued = new JPanel();
			jPanelSued.setLayout(new GridBagLayout());
			jPanelSued.add(jLabel4, gridBagConstraints);
			jPanelSued.add(jLGesamtsumme, gridBagConstraints1);
			jPanelSued.add(jLStatuszeile, gridBagConstraints2);
		}
		return jPanelSued;
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		beenden();
	}

	private void beenden() {
		logger.info("Anwendung wird beendet");
		setVisible(false);
		stopDB();
		dispose();
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		//logger.debug("keyPressed: " + arg0.toString());
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		//logger.debug("keyReleased: " + arg0.toString());
		statuszeile = "";
		
		if (arg0.getKeyChar() >= '0' && arg0.getKeyChar() <= '9' ) verarbeiteZahl(arg0.getKeyChar());
		else
			if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) verarbeiteRueckschritt();
			else
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) verarbeiteEscape();
				else
					if (arg0.getKeyChar() == '+') verarbeitePlus();
					else
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) verarbeiteEnter();
		if (arg0.getKeyCode() != KeyEvent.VK_ENTER) entercount = 0;
		aktualisiereFelder();
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		//logger.debug("keyTyped: " + arg0.toString());
	}

	private void aktualisiereFelder() {
		jLVerkaeufer.setText(verkaeuferText.length() > 0 ? verkaeuferText.toString() : "000");
		jLArtikelpreis.setText(Kassenposten.formatiereZahl(false, artikelpreis.toString()));
		jLGesamtsumme.setText(Kassenposten.formatiereZahl(false, "" + summe));
		
		switch (eingabefeld) {
		case FELD_ARTIKELPREIS : 
			if (statuszeile.equals("")) statuszeile = "Geben Sie den Artikelpreis ein.";
			jLTextArtikelpreis.setFont(highlightFont);
			jLTextVerkaeufer.setFont(normalFont);
			break;
		case FELD_VERKAEUFER :
			if (statuszeile.equals("")) statuszeile = "Geben Sie die Verkäufernummer ein";
			jLTextVerkaeufer.setFont(highlightFont);
			jLTextArtikelpreis.setFont(normalFont);
			break;
		}
		jLStatuszeile.setText(statuszeile);
	}
	
	private String statuszeile = new String("");
	private int eingabefeld = 0;
	private int entercount = 0;
	public final int FELD_VERKAEUFER = 0;
	public final int FELD_ARTIKELPREIS = 1;
	public static final int LAENGEPREIS = 6;
	
	private long summe = 0;
	
	private StringBuffer verkaeuferText = new StringBuffer("");
	private StringBuffer artikelpreis = new StringBuffer("");

	private JList jList = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenuItem jMenuItemBeenden = null;
	private JMenuItem jMenuItem1 = null;
	private JMenu jMenuAustausch = null;
	private JMenuItem jMenuItem2 = null;
	private JMenuItem jMenuItem3 = null;
	
	private void verarbeiteEnter() {
		entercount++;
		logger.debug("Entercount = " + entercount);
		if (verkaeuferText.length() == 0 && artikelpreis.length() == 0 && liste.size() > 0) {
			if (entercount == 1) statuszeile = "Drücken Sie die <ENTER>-Taste erneut, um den Vorgang abzuschließen.";
			if (entercount == 2) {
				logger.debug("Eintrag in DB ablegen");
				//FIXME: Kassen-ID einlesen lassen
				Vector<String> v = liste.dbbefehle(tabellen[TAB_Kassenposten], "1");
				try {
					Statement stmt = conn.createStatement();
					for (int i = 0; i < v.size(); i++) {
						logger.debug("Statement " + v.elementAt(i));
						stmt.executeUpdate(v.elementAt(i));
					}
					liste.removeAllElements();
					aktualisiereListe();
					summe = 0;
				}
				catch (java.sql.SQLException e) {
					logger.fatal("Datensatz nicht in Datenbank schreiben");
					logger.fatal("SQLException", e);
				}
			}
		} else {
			logger.debug("Nicht alle Voraussetzungen okay:\n" + 
				(entercount == 2) + " " + 
				(verkaeuferText.length() == 0) + " " +
				(artikelpreis.length() == 0) + " " +
				(liste.size() > 0));
			entercount = 0;
		}
	}
	
	private void verarbeitePlus() {
		if (verkaeuferText.length() == 3 && artikelpreis.length() > 0) {
			logger.info("Alte Summe " + summe);
			logger.info("Artikelpreis " +artikelpreis.toString());
			summe += Long.parseLong(artikelpreis.toString());
			logger.info("Neue Summe " + summe);
			liste.addKassenposten(verkaeuferText.toString(), artikelpreis.toString());
			aktualisiereListe();
			resetEingabefelder();
		}
	}

	private void aktualisiereListe() {
		jList.setListData(liste.getListData());
		jList.setSelectedIndex(liste.size()-1);
		jList.ensureIndexIsVisible(liste.size()-1);
	}
	
	private void resetEingabefelder() {
		artikelpreis.setLength(0);
		verkaeuferText.setLength(0);
		eingabefeld = FELD_VERKAEUFER;
		
	}
	
	private void verarbeiteEscape() {
		resetEingabefelder();
		summe = 0;
	}
	
	private void verarbeiteRueckschritt() {
		if (artikelpreis.length()==0 && verkaeuferText.length()==0 && liste.size()>0) {
			//Hole den letzten Datensatz aus der Liste, wenn es einen gibt
			Kassenposten kb = liste.remove(liste.size()-1);
			artikelpreis.append(kb.getArtikelpreis());
			verkaeuferText.append(kb.getVerkaeufer());
			eingabefeld = FELD_ARTIKELPREIS;
			aktualisiereListe();
			summe -= Long.parseLong(artikelpreis.toString());
		} else {
			if (eingabefeld == FELD_VERKAEUFER || artikelpreis.length() == 0) {
				if (verkaeuferText.length() > 0) verkaeuferText.setLength(verkaeuferText.length() - 1);
				eingabefeld = FELD_VERKAEUFER;
			}
			if (eingabefeld == FELD_ARTIKELPREIS && artikelpreis.length() > 0) {
				artikelpreis.setLength(artikelpreis.length() - 1);
			}
		}
	}
	
	private void verarbeiteZahl(char zahl) {
		if (eingabefeld == FELD_VERKAEUFER) {
			if (verkaeuferText.length() < 3) verkaeuferText.append(zahl);
		}
			
		if (eingabefeld == FELD_ARTIKELPREIS) {
			
			if (artikelpreis.length() >= LAENGEPREIS ) getToolkit().beep();
			else 
				if ((artikelpreis.length() == 0 && zahl != '0' )||
					artikelpreis.length() >0) artikelpreis.append(zahl);
		}

		if (verkaeuferText.length() == 3 && !verkaeuferText.equals("000")) eingabefeld = FELD_ARTIKELPREIS;
		if (verkaeuferText.toString().equals("000")) {
			verkaeuferText.setLength(2);
			eingabefeld = FELD_VERKAEUFER;
		}	
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList(liste);
			jList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			jList.setVisibleRowCount(12);
		}
		return jList;
	}
	
	/**
	 *  &Ouml;ffnet die Datenbankverbindung
	 *
	 * @return    Erfolg
	 */
	public boolean startDB() {
		logger.info("Datenbankverbindung wird initialisiert");
		boolean ergebnis = false;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			logger.debug("getConnection(\"" + jdbccon + ";create=true\")");
			conn = DriverManager.getConnection(jdbccon + ";create=true", "sa", "");
			ergebnis = true;
		}
		catch (InstantiationException e) {
			logger.fatal("InstantiationException", e);
		}
		catch (ClassNotFoundException e) {
			logger.fatal("ClassNotFoundException", e);
		}
		catch (IllegalAccessException e) {
			logger.fatal("IllegalAccessException", e);
		}
		catch (SQLException e) {
			logger.fatal("SQLException", e);
		}
		if (ergebnis)
			logger.info("Datenbankverbindung erfolgreich initialisiert");
		else
			logger.error("Datenbankverbindung konnte nicht initialisiert werden.");
		if (ergebnis)
			initDB();
		return ergebnis;
	}
	
	/**
	 *  Beendet die Verbindung zur Datenbank und f&auml;hrt die Datenbank herunter.
	 */
	public void stopDB() {
		logger.info("Datenbank wird heruntergefahren");
		try {
			conn.close();
			DriverManager.getConnection(jdbccon + ";shutdown=true");
			logger.debug("getConnection(\"" + jdbccon + ";shutdown=true\")");
		}
		catch (SQLException e) {
			// SQLException wird immer geworfen, um
			// Schließen der Verbindung zu bestätigen
			//logger.warn("SQLException", e);
		}
		logger.info("Herunterfahren der Datenbank beendet");
	}


	/**  Die Datenbank wird mit Tabellen und Inhalten gefüllt */
	private void initDB() {
		tabellen = new String[numTabellen];
		boolean[] tabellenVorhanden = new boolean[numTabellen];
		String tabSQLStatement[] = new String[numTabellen];
		for (int i = 0; i < numTabellen; i++)
			tabellenVorhanden[i] = false;

		//Erzeuge Tabelle lokale Kasse
		tabellen[TAB_Kassenposten] = "kassenposten";
		tabSQLStatement[TAB_Kassenposten] = "CREATE TABLE " + tabellen[TAB_Kassenposten] + " (" +
			"kassenid int, " +
			"verkaeufer varchar(3)," +
			"artikelpreis int" +
			")";

		try {
			String[] names = {"TABLE"};
			ResultSet rs = conn.getMetaData().getTables(null, "%", "%", names);
			String tabname = "";
			while (rs.next()) {
				tabname = rs.getString("TABLE_NAME");
				for (int i = 0; i < numTabellen; i++)
					if (tabname.equalsIgnoreCase(tabellen[i]))
						tabellenVorhanden[i] = true;

			}
		}
		catch (java.sql.SQLException e) {
			logger.error("Kann SYSTABLES nicht abfragen");
			logger.error("SQLException", e);
		}
		try {
			Statement stmt = conn.createStatement();
			for (int i = 0; i < numTabellen; i++) {
				logger.debug("Tabelle " + tabellen[i]);
				if (tabellenVorhanden[i])
					logger.debug(" -> Tabelle " + tabellen[i] + " ist vorhanden.");
				else {
					logger.info(tabSQLStatement[i]);
					stmt.executeUpdate(tabSQLStatement[i]);
				}
			}

		}
		catch (java.sql.SQLException e) {
			logger.fatal("Kann Basistabellen in der Datenbank nicht erzeugen");
			logger.fatal("SQLException", e);
		}
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("Programm");
			jMenu.add(getJMenuAustausch());
			jMenu.add(getJMenuItem3());
			jMenu.addSeparator();
			jMenu.add(getJMenuItemBeenden());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemBeenden() {
		if (jMenuItemBeenden == null) {
			jMenuItemBeenden = new JMenuItem();
			jMenuItemBeenden.setText("Beenden");
			jMenuItemBeenden.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					beenden();
				}
			});
		}
		return jMenuItemBeenden;
	}

	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("erzeugen");
			jMenuItem1.setActionCommand("erzeugen");
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jMenuAustausch	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuAustausch() {
		if (jMenuAustausch == null) {
			jMenuAustausch = new JMenu();
			jMenuAustausch.setText("Austauschdatei");
			jMenuAustausch.add(getJMenuItem1());
			jMenuAustausch.add(getJMenuItem2());
		}
		return jMenuAustausch;
	}

	/**
	 * This method initializes jMenuItem2	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("einlesen");
		}
		return jMenuItem2;
	}

	/**
	 * This method initializes jMenuItem3	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setText("Auswertung");
		}
		return jMenuItem3;
	}


	
}  //  @jve:decl-index=0:visual-constraint="86,8"
