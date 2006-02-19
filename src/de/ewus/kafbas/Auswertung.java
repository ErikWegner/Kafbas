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

import javax.swing.table.AbstractTableModel;

/**
 * @author 364
 *
 */
public class Auswertung extends AbstractTableModel {

	private int anzahlKassen = 4;
	private final String[] spaltennamen = {"Verkäufer", "Summe", "20%", "80%"};
	/** Anzahl permanenter Spalten */
	private final int permSpalten = spaltennamen.length;
	
	private int ergebniszeilen = 0;
	/**
	 * 
	 */
	public Auswertung() {
		super();
		// TODO Automatisch erstellter Konstruktoren-Stub
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
			case 1 : d = 100.00; break; //TODO Summe aller Verkäufe
			case 2 : d = 20.00; break; //TODO Summe aller 20%
			case 3 : d = 80.00; break; //TODO Summe aller 80%
			default :
				d = 25.00; //TODO Summe aller Verkäufe der Kasse
			}
		}
		return (col > 0 ? d : r);
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int arg0) {
		// TODO Automatisch erstellter Methoden-Stub
		return (arg0 < permSpalten ? spaltennamen[arg0] : "Kasse" + (1 + arg0 - permSpalten));
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Automatisch erstellter Methoden-Stub
		return false;
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return (col > 0 ? Double.class : String.class);
	}

}
