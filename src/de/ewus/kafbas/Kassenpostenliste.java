package de.ewus.kafbas;

import java.util.Vector;


public class Kassenpostenliste extends Vector<Kassenposten> {
	
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
}
