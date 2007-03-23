/**
 * 
 */
package de.ewus.kafbas;

import java.awt.Cursor;
import java.io.*;
import java.sql.*;

import javax.swing.JFrame;
import org.apache.log4j.Logger;

/**
 * @author e-man
 *
 */
public class DTAReader extends SwingWorker implements FilenameFilter {
	private static final Logger logger = Logger.getLogger(DTAReader.class
			.getName());

	protected ProgressDisplay pm;
	protected String path; //Austauschpfad
	protected String dateiPrefix, dateiSuffix;
	protected int kassenID;
	protected Connection conn;
	protected String tabelle; //Name der Tabelle
	protected DTAmessage dtam;
	protected JFrame parent;

	/** Konstruktor.
	 * @param pm			ProgressMonitor
	 * @param path			Austauschpfad
	 * @param dateiPrefix	Prefix der Austauschdateien
	 * @param dateiSuffix	Suffix der Austauschdateien
	 * @param kassenID		ID dieser Kasse
	 * @param tabelle		Name der Tabelle mit den Kassenposten
	 * @param dtam			DTAmessage-Objekt
	 */
	public DTAReader(
			String path, 
			String dateiPrefix, 
			String dateiSuffix,
			int kassenID,
			Connection conn,
			String tabelle,
			DTAmessage dtam,
			JFrame parent) {
		this.path = path;
		this.dateiPrefix = dateiPrefix;
		this.dateiSuffix = dateiSuffix;
		this.kassenID = kassenID;
		this.conn = conn;
		this.tabelle = tabelle;
		this.dtam = dtam;
		this.parent = parent;
	}

	/* (Kein Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File arg0, String arg1) {
		return arg1.toLowerCase().matches(dateiPrefix + "\\d*\\." + dateiSuffix);
	}

	protected int zahlAusDateinamen(String dateiname) {
		return Integer.parseInt(dateiname.substring(dateiPrefix.length(), dateiname.length() - dateiSuffix.length()-1));
	}

	protected void parentActive(boolean active) {
		parent.setEnabled(active);
		parent.setCursor(Cursor.getPredefinedCursor(active ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR));

	}
	
	public Object construct() {
		parentActive(false);
		logger.debug("DTAReader lesen");
		dtam.scriptDTAstart();
		//Finde alle kasse???.k
		File verzeichnis = new File(path);
		//für alle dateien <> eigene_kassen_id
		String[] kdaliste = verzeichnis.list(this);
		/*ProgressMonitor pm = new ProgressMonitor(
				parent,
				"Austauschdatei einlesen", 
				null, 
				0, 
				kdaliste.length * KafbasGUI.DatensaetzeJeDatei);
*/
		pm = new ProgressDisplay(parent, 0, kdaliste.length * KafbasGUI.DatensaetzeJeDatei);
		pm.show();
		//	delete where kassenid=kassenid_der_datei
		//	insert (datensätze aus datei)
		int kasse, dateilaenge;
		for (int c=0; c<kdaliste.length; c++) {
			kasse = zahlAusDateinamen(kdaliste[c]);
			pm.setNote("Kasse " + kasse + " wird verarbeitet...");
			if (kasse != kassenID) {
				int zeilenzaehler = 0;
				try {
					Statement stmt = conn.createStatement();
					String anweisung;
					conn.setAutoCommit(false);
					anweisung = "DELETE FROM " + tabelle + " WHERE kassenid = " + kasse;
					logger.debug("Anweisung: " + anweisung);
					//stmt.addBatch(anweisung);
					stmt.executeUpdate(anweisung);
					String zeile;
					String[] teile = null;
					//FileReader fr = new FileReader(new File(verzeichnis, kdaliste[c]));
					//BufferedReader in = new BufferedReader( fis );
					RandomAccessFile in = new RandomAccessFile(new File(verzeichnis, kdaliste[c]), "r");
					dateilaenge = 0;
					try {
						dateilaenge = (int)in.length();
					} catch (IOException e) {
						logger.error(e);
					}
					if (dateilaenge == 0) {dateilaenge = 1;}
					while ((zeile = in.readLine()) != null) {
						zeilenzaehler++;
						if (zeile.matches("\\d{3},\\d{1," + KafbasGUI.LAENGEPREIS +"}")) {
							teile = zeile.split(",");
							anweisung = "INSERT INTO " + tabelle +
							" (kassenid, verkaeufer, artikelpreis) VALUES (" +
							kasse + ",'" + teile[0] + "'," + teile[1] + ")";
							logger.debug("Anweisung: " + anweisung);
							stmt.executeUpdate(anweisung);
							//stmt.addBatch(anweisung);
						} else logger.error("In der Datei " + kdaliste[c] + " ist die Zeile" + zeilenzaehler + "fehlerhaft");
						pm.setProgress((int) (c*KafbasGUI.DatensaetzeJeDatei + (long)KafbasGUI.DatensaetzeJeDatei * in.getFilePointer()/dateilaenge));
						logger.debug("Progress:"+(int) (c*KafbasGUI.DatensaetzeJeDatei + (long)KafbasGUI.DatensaetzeJeDatei * in.getFilePointer()/dateilaenge));
					}
					in.close();
					//int updateCounts[] = stmt.executeBatch();
					conn.commit();
					conn.setAutoCommit(true);
					//for (int c1=0; c1 < updateCounts.length; c1++) logger.debug("update = " + updateCounts[c1]);
				} catch (FileNotFoundException e) {
					logger.error("FileNotFoundException", e);
				} catch (IOException e) {
					logger.error("IOException", e);
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.error("In der Datei " + kdaliste[c] + " ist die Zeile" + zeilenzaehler + "fehlerhaft");
				} catch (BatchUpdateException e) {
					try { conn.rollback();conn.setAutoCommit(true);} catch (SQLException e2) {logger.error("SQLException",e2);}
				} catch (SQLException e) {
					logger.error("SQLException", e);
				} finally {
					try { conn.setAutoCommit(true);} catch (SQLException e) {logger.error("SQLException",e);}
				}
			}
			pm.setProgress((c+1)*KafbasGUI.DatensaetzeJeDatei);
		}
		pm.close();
		dtam.scriptDTAende();
		dtam.meldungDTAende(parent);
		return null;
	}

	public void finished() {
		pm.close();
		parentActive(true);
	}
}
