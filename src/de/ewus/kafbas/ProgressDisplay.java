package de.ewus.kafbas;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class ProgressDisplay {
	protected JDialog dlgWindow;
	protected JProgressBar pbar;
	protected JLabel noteLabel;
	
	public ProgressDisplay(JFrame parent, int min, int max) {
		dlgWindow = new JDialog(parent, false);
		dlgWindow.setTitle("Fortschritt...");
		pbar = new JProgressBar();
		pbar.setMinimum(min);
		pbar.setMaximum(max);
		pbar.setStringPainted(true);
		noteLabel = new JLabel();
		noteLabel.setHorizontalAlignment(JLabel.CENTER);
		
		BorderLayout b = new BorderLayout(10, 10);
		dlgWindow.setLayout(b);
		dlgWindow.add(pbar, BorderLayout.CENTER);
		dlgWindow.add(noteLabel,BorderLayout.PAGE_END);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension(screen.width/3,100);
		dlgWindow.setSize(frame);
		dlgWindow.validate();
		dlgWindow.setLocation((screen.width - frame.width) / 2,(screen.height - frame.height) / 2);
		dlgWindow.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
	public void setProgress(int progress) {
		pbar.setValue(progress);
	}
	
	public void setNote(String note) {
		noteLabel.setText(note);
	}
	
	public void close() {
		dlgWindow.dispose();
	}
	
	public void show() {
		dlgWindow.setVisible(true);
	}
}
