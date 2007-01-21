package de.ewus.kafbas;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

public class PMThread extends Thread {
	private static final Logger logger = Logger.getLogger(PMThread.class
			.getName());
	protected JFrame parent = null;
	protected int max = 1;
	protected ProgressMonitor pm;
	protected int progress = 0, altprogress = 0;
	protected boolean quit = false;
	
	public void prepare(JFrame parent, int max) {
		this.parent = parent;
		this.max = max;
	}

	public void run() {
		pm = new ProgressMonitor(parent,
				"Austauschdatei einlesen", null, 0, max);
		logger.debug("PMmax="+max);
		pm.setMillisToDecideToPopup(250);
		pm.setMillisToPopup(500);
		while (!quit) {
			if (progress != altprogress) {
				pm.setProgress(progress);
				altprogress = progress;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		pm.close();
	}

	public void close() {
		quit = true;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
	}
}
