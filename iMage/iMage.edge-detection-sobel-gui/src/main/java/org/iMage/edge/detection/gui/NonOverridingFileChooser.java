package org.iMage.edge.detection.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * A JFileChooser that warns the user when trying to override a file
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class NonOverridingFileChooser extends JFileChooser {
	private static final long serialVersionUID = -8606754023803726505L;

	@Override
	public void approveSelection() {
		File f = getSelectedFile();
		if (f.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				super.approveSelection();
				return;
			case JOptionPane.NO_OPTION:
				return;
			case JOptionPane.CLOSED_OPTION:
				return;
			case JOptionPane.CANCEL_OPTION:
				cancelSelection();
				return;
			default: // Do nothing
				return;
			}
		}
		super.approveSelection();
	}
}
