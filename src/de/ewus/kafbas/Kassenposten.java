/**
 * Ein Kassenposten.
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

public class Kassenposten {
	private String verkaeufer;
	private String artikelpreis;
	
	public Kassenposten(String verkaeufer, String artikelpreis) {
		this.verkaeufer = verkaeufer;
		this.artikelpreis = artikelpreis;
	}
	
	public String toString() {
		return "[" + verkaeufer + "]     " + formatiereZahl(true, artikelpreis);
	}
	
		
	public String getArtikelpreis() {
		return artikelpreis;
	}

	public String getVerkaeufer() {
		return verkaeufer;
	}

	public static String formatiereZahl(boolean nullen, String s) {
		StringBuffer sb = new StringBuffer("");
		sb.append(s);
		while (sb.length()<3) sb.insert(0, "0");
		sb.insert(sb.length()-2, ",");
		if (nullen) while (sb.length() <= KafbasGUI.LAENGEPREIS) sb.insert(0, "0");
		return sb.toString();
	}
	
	public String toDBString(String tabelle, int kassenid) {
		return "INSERT INTO " + tabelle + " (kassenid, verkaeufer, artikelpreis) VALUES (" +
			kassenid + ",'" + verkaeufer + "'," + artikelpreis + ")";
	}
}
