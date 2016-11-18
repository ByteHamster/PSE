package org.iMage.edge.detection.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.InputStream;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * Displays the "About" dialog
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 5344365059140043461L;

	/**
	 * Initializes a new AboutDialog
	 * @param owner Calling Frame
	 */
	public AboutDialog(Frame owner) {
		super(owner, "About", true);

		setPreferredSize(new Dimension(650, 600));
		JLabel label = new JLabel();
		label.setFont(new Font("Monospaced", Font.PLAIN, 8));
		InputStream is = getClass().getResourceAsStream("license_text.txt");
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
		String license = s.hasNext() ? s.next() : "";
		s.close();
		label.setText("<html>" + license.replace(" ", "&nbsp;").replace("\n", "<br />") + "</html>");
		label.setBorder(new EmptyBorder(20, 20, 20, 20));
		pack();
		getContentPane().add(label);
		WindowTools.centerWindow(this);
		setResizable(false);
	}
}
