/**
 * 
 */
package de.ewus.kafbas;

import java.text.NumberFormat;

import javax.swing.table.DefaultTableCellRenderer;


/**
 * @author 364
 *
 */
public class WaehrungsSpalteRenderer extends DefaultTableCellRenderer {
	
	NumberFormat formatter;
	
	/**
	 * 
	 */
	public WaehrungsSpalteRenderer() {
		super();
		// TODO Automatisch erstellter Konstruktoren-Stub
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#setValue(java.lang.Object)
	 */
	@Override
	protected void setValue(Object value) {
		// TODO Automatisch erstellter Methoden-Stub
	  if (formatter==null) {
            formatter = NumberFormat.getCurrencyInstance();
        }
        setText((value == null) ? "" : formatter.format(value) );
	}

}
