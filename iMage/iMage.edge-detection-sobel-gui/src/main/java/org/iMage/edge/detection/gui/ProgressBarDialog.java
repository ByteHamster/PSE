package org.iMage.edge.detection.gui;

import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * A progress bar dialog
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ProgressBarDialog {
	JDialog progressHolder;
	JProgressBar progressBar;

	/**
	 * Creates (but not shows) new progress bar popup
	 */
	public ProgressBarDialog() {
		progressHolder = new JDialog();
		progressHolder.setTitle("Please wait...");
		progressHolder.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		progressHolder.setModalityType(ModalityType.APPLICATION_MODAL);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		progressHolder.getContentPane().add(progressBar);
		progressHolder.setResizable(false);
		progressHolder.pack();
		WindowTools.centerWindow(progressHolder);
		

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(progressHolder);
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException ignored) {
			// Using default theme
		}
	}

	/**
	 * Shows the popup
	 */
	public void show() {
		progressHolder.setVisible(true);
	}

	/**
	 * Closes the popup
	 */
	public void dismiss() {
		progressHolder.setVisible(false);
	}

	/**
	 * Sets the maximum value
	 * @param max The maximum value
	 */
	public void setMaximum(int max) {
		progressBar.setMaximum(max);
	}

	/**
	 * Update the dialogs value and text
	 * @param value The value to set
	 * @param text The text to set
	 */
	public void update(final int value, final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(value);
				progressBar.setString(text);
			}
		});
	}
}
