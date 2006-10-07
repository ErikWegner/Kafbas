/*
 * Ein Objekt zur Steuerung 
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

import java.io.IOException;

import java.awt.Component;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class DTAmessage {
    private static final Logger logger = Logger.getLogger(DTAmessage.class.getName());

	private String meldungAustauschFrageVorBeginn = "";
	private String meldungAustauschBeendet = "";
	private String scriptAustauschBeginn = "";
	private String scriptAustauschBeendet = "";
	public void setMeldungAustauschBeendet(String meldungAustauschBeendet) {
		this.meldungAustauschBeendet = meldungAustauschBeendet;
	}
	public void setMeldungAustauschFrageVorBeginn(
			String meldungAustauschFrageVorBeginn) {
		this.meldungAustauschFrageVorBeginn = meldungAustauschFrageVorBeginn;
	}
	public void setScriptAustauschBeendet(String scriptAustauschBeendet) {
		this.scriptAustauschBeendet = scriptAustauschBeendet;
	}
	public void setScriptAustauschBeginn(String scriptAustauschBeginn) {
		this.scriptAustauschBeginn = scriptAustauschBeginn;
	}
	
	public boolean frageDTAbeginnen(Component frame) {
		//Ist die Variable meldungAustauschFrageVorBeginn leer,
		//wird automatisch WAHR zurückgegeben,
		//andernfalls erscheint ein Dialog mit OK und Abbrechen,
		//bei der Auswahl von OK wird WAHR zurückgegeben
		return (meldungAustauschFrageVorBeginn.equals("") ? true : JOptionPane.showOptionDialog(frame, 
				meldungAustauschFrageVorBeginn, 
				"Datenaustausch beginnen?", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				null, null, null) == 0);
	}
	
	public void meldungDTAende(Component frame) {
		if (!meldungAustauschBeendet.equals("")) JOptionPane.showMessageDialog(frame,
				meldungAustauschBeendet,
				"Datenaustausch beendet",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void scriptDTAstart() {
		scriptAusfuehren(scriptAustauschBeginn);
	}
	
	public void scriptDTAende() {
		scriptAusfuehren(scriptAustauschBeendet);
	}
	
	private void scriptAusfuehren(String script) {
		try {
			if (!script.equals("")) {
				logger.debug("Runtime.exec " + script);
				Runtime.getRuntime().exec(script);
			}
		}
		catch (SecurityException e) {logger.error(e);} 
		catch (IOException e) {logger.error(e);}
		catch (NullPointerException e) {logger.error(e);}
		catch (IllegalArgumentException e) {logger.error(e);}
	}
	
}
