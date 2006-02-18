/**
 * 
 */
package de.ewus.kafbas;

import org.apache.log4j.Logger;

/**
 * @author Erik Wegner
 *
 */
public class Kafbas {

	private static Logger logger;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		org.apache.log4j.xml.DOMConfigurator.configure("log4j.xml");
		Logger logger = Logger.getRootLogger();
		logger.info("Anwendung gestartet");
		// TODO Automatisch erstellter Methoden-Stub
		KafbasGUI gui = new KafbasGUI();
		gui.setVisible(true);
	}

}
