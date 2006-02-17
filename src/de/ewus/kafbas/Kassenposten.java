package de.ewus.kafbas;

public class Kassenposten {
	private String verkaeufer;
	private String artikelpreis;
	
	public Kassenposten(String verkaeufer, String artikelpreis) {
		this.verkaeufer = verkaeufer;
		this.artikelpreis = artikelpreis;
	}
	
	public String toString() {
		return verkaeufer + "    " + formatiereZahl(true, artikelpreis);
	}
	
		
	public String getArtikelpreis() {
		return artikelpreis;
	}

	public String getVerkaeufer() {
		return verkaeufer;
	}

	public static String formatiereZahl(boolean nullen, String s) {
		int len = s.length();
		StringBuffer sb = new StringBuffer("");
		sb.append(s);
		while (sb.length()<3) sb.insert(0, "0");
		sb.insert(sb.length()-2, ",");
		if (nullen) while (sb.length() <= KafbasGUI.LAENGEPREIS) sb.insert(0, "0");
		return sb.toString();
	}
}
