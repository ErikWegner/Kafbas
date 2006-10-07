/**
 * Die Datenquelle f&uuml;r die Auswertung.
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

//@SuppressWarnings("serial")
public class Auswertung extends AbstractTableModel {

    private static final Logger logger = Logger.getLogger(Auswertung.class.getName());
    private int anzahlKassen = 4;
    private Connection conn;
    private int[] ksums;
    private int[][] vsums;
    private int ergebniszeilen = 0;
    private HashMap vmap = new HashMap();
    private int anteilK = -1;
    private String[] spaltennamen;
    /** Anzahl permanenter Spalten */
    private int permSpalten = 0;
    /** Summe aller Verkäufe */
    private int summe;
    /** Summe des Kirchenanteils */
    private int summeK;
    /** Summe der Nettogewinne für Verkäufer */
    private int summeV;
    /**
     * 
     */
    public Auswertung(int anzahlKassen, Connection conn, int anteilProzent) {
		super();
		this.anzahlKassen = anzahlKassen;
		this.conn = conn;
		this.anteilK = anteilProzent;
		ksums = new int[anzahlKassen];
		spaltennamen = new String[4];
		spaltennamen[permSpalten++] = "Verkäufer";
		spaltennamen[permSpalten++] = "Summe";
		spaltennamen[permSpalten++] = anteilK + "%";
		spaltennamen[permSpalten++] = (100-anteilK) + "%";
		for (int i = 0; i < anzahlKassen; i++) ksums[i] = 0;
		auswerten();
    }

    private void auswerten() {
		String ksum = "SELECT \"KASSENID\", SUM( \"ARTIKELPREIS\" ) AS \"KSUM\" FROM \"KASSENPOSTEN\" GROUP BY \"KASSENID\"";
		try {
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(ksum);
		    int tkassenid;
		    while (rs.next()) {
			tkassenid = rs.getInt("KASSENID");
			logger.debug("Query KASSENID=" + tkassenid);
			if (tkassenid <= anzahlKassen && tkassenid > 0)
			    ksums[tkassenid - 1] = rs.getInt("KSUM");
			else logger.error("In der Datenbank befinden sich Datensaetze mit einer KassenID groesser als AnzahlKassen. (KASSENID=" + tkassenid + ", AnzahlKassen="+anzahlKassen+")");
		    }
		}
		catch (java.sql.SQLException e) {
		    logger.fatal("SQLException", e);
	}

		String vsum = "SELECT \"VERKAEUFER\", SUM( \"ARTIKELPREIS\" ) AS \"VSUM\", \"KASSENID\" FROM \"KASSENPOSTEN\" GROUP BY \"VERKAEUFER\", \"KASSENID\"";
		String vcountquery = "SELECT \"VERKAEUFER\" FROM \"KASSENPOSTEN\" GROUP BY \"VERKAEUFER\"";
		try {
		    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    ResultSet rs = stmt.executeQuery(vcountquery);
		    rs.last();
		    int vcount = rs.getRow();
		    if (vcount == 0) logger.error("Anzahl der Verkaeufer ist null.");
		    else {
			ergebniszeilen = vcount;
			vsums = new int[ergebniszeilen][anzahlKassen+2];
			logger.debug("Anzahl der Verkaeufer=" + vcount);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(vsum);
			int tkassenid, vid;
			int arraycounter=0;
			vmap.clear();
			while (rs.next()) {
			    tkassenid = rs.getInt("KASSENID");
			    vid = rs.getInt("VERKAEUFER");
			    logger.debug("Query KASSENID=" + tkassenid);
			    logger.debug("Query VERKAEUFER=" + vid);
			    logger.debug("Query VSUM=" + rs.getInt("VSUM"));
			    if (!vmap.containsKey(new Integer(vid))) {
				logger.debug("Neuer Hashmapeintrag: " + vid + " => " + arraycounter);
				vmap.put(new Integer(vid), new Integer(arraycounter));
				vsums[arraycounter][0] = vid;
				arraycounter++;
			    }
			    logger.debug("Zugriff auf Zeile " + vmap.get(new Integer(vid)));
			    if (tkassenid <= anzahlKassen && tkassenid > 0) vsums[((Integer)vmap.get(new Integer(vid))).intValue()][tkassenid] = rs.getInt("VSUM");
			    else logger.error("In der Datenbank befinden sich Datensaetze mit einer KassenID groesser als AnzahlKassen. (KASSENID=" + tkassenid + ", AnzahlKassen="+anzahlKassen+")");
			}
		    }
		}
		catch (java.sql.SQLException e) {
		    logger.fatal("SQLException", e);
		}
		summe = summeK = summeV = 0;
		for (int i1 = 0; i1 < ergebniszeilen; i1++) {
		    vsums[i1][anzahlKassen+1] = 0;
		    for (int i2 = 0; i2 < anzahlKassen; i2++) vsums[i1][anzahlKassen+1] += vsums[i1][i2+1];
		    summe += vsums[i1][anzahlKassen+1];
		    summeV += (100-anteilK)*vsums[i1][anzahlKassen+1]/100;
		}
		summeK = summe-summeV;
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
		// TODO Automatisch erstellter Methoden-Stub
		return ergebniszeilen + 1;
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
    	return permSpalten + anzahlKassen;
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col) {
		String r = "";
		double d = 0;
		if (row == ergebniszeilen) {
		    switch (col) {
			case 0 : r = "Summe"; break;
			case 1 : d = summe/100.0; break; //TODO Summe aller Verkäufe
			case 2 : d = summeK/100.0; break; //TODO Summe aller 20%
			case 3 : d = summeV/100.0; break; //TODO Summe aller 80%
			default :
			    d = (ksums[col - permSpalten])/100.0; //TODO Summe aller Verkäufe der Kasse
		    }
		} else 
		    switch (col) {
			case 0 : r = "000" + vsums[row][col]; 
			    r = r.substring(r.length() - 3 );
			    break;
			case 1 : 
			    d = ((double)vsums[row][anzahlKassen+1])/100;
			    break;
			case 2 :
			    d = ((double)(
				     vsums[row][anzahlKassen+1]-
				     (100-anteilK)*vsums[row][anzahlKassen+1]/100)
				)/100;
			    break;
			case 3 :
			    d = ((double)(
				     (100-anteilK)*vsums[row][anzahlKassen+1]/100)
				)/100; 
			    break;
			default :
			    d = ((double)vsums[row][col-permSpalten+1])/100;
		    }
		Double objd = new Double(d);
		if (col > 0) return objd;
		else return r;
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int arg0) {
    	return (arg0 < permSpalten ? spaltennamen[arg0] : "Kasse" + (1 + arg0 - permSpalten));
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int arg0, int arg1) {
    	return false;
    }

    /* (Kein Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int col) {
	return (col > 0 ? Double.class : String.class);
    }

	/* (Kein Javadoc)
	 * @see javax.swing.table.AbstractTableModel#findColumn(java.lang.String)
	 */
	public int findColumn(String arg0) {
		// return super.findColumn(arg0);
		int i = -1;
		for (int c = 0; c < spaltennamen.length; c++) if (spaltennamen[c].equals(arg0)) i=c;
    	return (i < 0 ? Integer.parseInt(arg0.substring(5)) - 1 + permSpalten : i);
	}

}
