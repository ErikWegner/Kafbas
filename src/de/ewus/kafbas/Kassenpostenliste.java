/**
 * Eine Sammlung von Kassenposten.
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

import java.util.Vector;


public class Kassenpostenliste extends Vector {
	
	public Vector getListData() {
		int size = size();
		Vector v = new Vector(size);
		for (int i = 0; i < size; i++) v.addElement(elementAt(i).toString());
		return v;
	}
	
	public void addKassenposten(String verkaeufer, String artikelpreis) {
		Kassenposten kp = new Kassenposten(verkaeufer, artikelpreis);
		add(kp);
	}
	
	public Vector dbbefehle(String tabelle, int kassenid) {
		int size = size();
		Vector v = new Vector(size);
		for (int i = 0; i < size; i++) v.addElement(((Kassenposten)elementAt(i)).toDBString(tabelle, kassenid));
		return v;
	}
}
