/**
 * Die Hauptklasse.
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
