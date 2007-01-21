/**
 * 
 */
package de.ewus.kafbas;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * @author e-man
 *
 */
public class AnteilBerechner {

	private static final Logger logger = Logger.getLogger(AnteilBerechner.class
			.getName());

	private static final AnteilBerechner instance = new AnteilBerechner();

	/** Ablage der Regeln als Regelobjekte */
	protected Vector regeln = new Vector();

	/** Ablage des Anteils unter der Verkäufernummer */
	protected HashMap ergebnisCache = new HashMap();

	/**
	 * Konstruktor
	 */
	protected AnteilBerechner() {}

	private int standardAnteil = -1;
	private int regelsuchmodus = 1;

	/**
	 * Liefert den Anteil für einen Verkäufer
	 * @param verkaeuferNr die Nummer des Verkäufers
	 * @return Anteil in Prozent
	 */
	public int anteil(int verkaeuferNr) {
		int r = standardAnteil;
		if (ergebnisCache.containsKey(new Integer(verkaeuferNr))) {
			r = ((Integer)ergebnisCache.get(new Integer(verkaeuferNr))).intValue();
			logger.debug("Für Verkäufer " + verkaeuferNr + " wurde aus dem Cache der Wert " + r + " geholt.");
		} else {
			logger.debug("Für Verkäufer " + verkaeuferNr + " müssen die Regeln geprüft werden.");
			Regelobjekt regel;
			int c1 = 0;
			boolean weitersuchen = true, treffer = false;
			while (c1 < regeln.size() && weitersuchen) {
				regel = (Regelobjekt)regeln.elementAt(c1);
				treffer = regel.trifftZu(verkaeuferNr);
				if (treffer) {
					logger.debug("Regel " + regel + " trifft zu.");
					r = regel.getAnteil();
					switch (regelsuchmodus) {
					case 0:
						weitersuchen = true;
						break;
					case 1:
						weitersuchen = false;
						break;
					default:
						logger.warn("Regelsuchmodus hat ungültigen Wert:" + regelsuchmodus);
					break;
					}
				}
				c1++;
			}
			ergebnisCache.put(new Integer(verkaeuferNr), new Integer(r));
			logger.debug("Für Verkäufer " + verkaeuferNr + " wurde der Wert " + r + " ermittelt und im Cache abgelegt.");
		}
		return r;
	}

	/** Richtet die Berechnungsregeln anhand der Properties ein.
	 * @param properties ein konfiguriertes Properties-Element
	 * @return Erfolgreiches Einlesen
	 */
	public boolean init(Properties properties) {
		boolean r; //Rückgabe
		logger.info("AnteilBerechner wird initialisiert");


		//Standardanteil lesen
		String sAnteilProzent = properties.getProperty("AnteilProzent");
		logger.debug("AnteilProzent=" + sAnteilProzent);
		if (sAnteilProzent != null) {
			standardAnteil = Integer.parseInt(sAnteilProzent);
			logger.debug("Standardanteil = " + standardAnteil);
		}
		r = standardAnteil > 0;


		//welche Schlüssel gibt es, die mit »AnteilSpezial« beginnen?
		//alle Schlüssel-Werte-Paare sortiert merken
		String keyName;
		SortedMap keyValues = new TreeMap();
		Regelobjekt regel;
		for (Enumeration e = properties.keys() ; e.hasMoreElements() ;) {
			keyName = (String)e.nextElement();
			if (keyName.startsWith("AnteilSpezial")) {
				if (!keyName.equals("AnteilSpezialModus")) {
					logger.debug("gelesen Regel " + keyName + "=" + properties.getProperty(keyName));
					keyValues.put(keyName, properties.getProperty(keyName));
				}
			}
		}
		
		//und nun die sortierte Liste auswerten
		for (Iterator i=keyValues.keySet().iterator(); i.hasNext();  ) {
			keyName = (String)i.next();
			regel = new Regelobjekt((String)keyValues.get(keyName));
			logger.debug("verarbeitet Regel " + keyName + "=" + regel);
			if (regel.isValid()) {
				regeln.add(regel);
			} else {
				r = false;
				logger.error("Regel " + keyName + " enthält folgende Fehler");
				regel.whyIsNotValid();
			}

		}

		//welcher Regelmodus wird benutzt?
		String rmodusS = properties.getProperty("AnteilSpezialModus");
		if (rmodusS != null) {
			regelsuchmodus = Integer.parseInt(rmodusS);
			logger.debug("AnteilSpezialModus=" + regelsuchmodus + " (=Regelsuchmodus)");
		}

		if (standardAnteil < 1) logger.fatal("Der Prozentanteil für die Berechnung der Auswertung ist nicht festgelegt.");
		return r;
	}

	public static AnteilBerechner getInstance() {
		return instance;
	}

}
