/**
 * 
 */
package de.ewus.kafbas;

import org.apache.log4j.Logger;

/**
 * @author e-man
 *
 */
public class Regelobjekt {
	private static final Logger logger = Logger.getLogger(Regelobjekt.class
			.getName());
	
	public static final int minVerkaeuferZahl = 1;
	
	private int 
		von = -1, 
		bis = -1, 
		anteil = -1;
	
	public Regelobjekt(String regel) {
		//Text hinter Komma suchen
		String[] teile = regel.split(",", 2);
		if (teile.length == 2) {
			String anteilS = teile[1];
			//Minuszeichen suchen
			teile = teile[0].split("-", 2);
			if (teile.length == 2) {
				String vonS = teile[0];
				String bisS = teile[1];
				von = Integer.parseInt(vonS);
				bis = Integer.parseInt(bisS);
				anteil = Integer.parseInt(anteilS);
			}
		}
	}
	
	public boolean isValid() {
		return (von >= minVerkaeuferZahl) && (bis >= minVerkaeuferZahl) && (bis >= von) && (anteil > 0) && (anteil < 100);
	}
	
	
	public void whyIsNotValid() {
		if (!isValid()) {
			if (!(von >= minVerkaeuferZahl)) logger.error("Regelfehler: von(=" + von + ") ist nicht größer oder gleich " + minVerkaeuferZahl);
			if (!(bis >= minVerkaeuferZahl)) logger.error("Regelfehler: bis(=" + bis + ") ist nicht größer oder gleich " + minVerkaeuferZahl);
			if (!(bis >= von)) logger.error("Regelfehler: bis(=" + bis + ") ist nicht größer oder gleich von(=" + von + ")");
			if (!(anteil > 0)) logger.error("Regelfehler: anteil(=" + anteil + ") ist nicht größer als 0");
			if (!(anteil < 100)) logger.error("Regelfehler: anteil(=" + anteil + ") ist nicht kleiner als 100");
		}
	}
	
	public boolean trifftZu(int verkaeuferID) {
		return (verkaeuferID >= von) && (verkaeuferID <= bis);
	}
	
	public int getAnteil() {
		return anteil;
	}
	
	public String toString() {
		return "(von=" + von + ", bis=" + bis + ", anteil=" + anteil + ")";
	}
}
