package org.iMage.edge.detection.gui;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.Box;

/**
 * Tiny utility classes for handling windows
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class WindowTools {

	private WindowTools() {
		// Must not be initialized
	}

	/**
	 * Center the given window on screen
	 * 
	 * @param frame
	 *            The window
	 */
	public static void centerWindow(Component frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		GraphicsDevice gd = gs[0];
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Rectangle bounds = gc.getBounds();
		frame.setLocation((bounds.width - frame.getWidth()) / 2, (bounds.height - frame.getHeight()) / 2);
	}
	
	/**
	 * Create a bounding box that aligns a panel to the left
	 * @param panel The panel to align
	 * @return The new bounding box
	 */
    public static Component leftJustify(Component panel) {
        Box b = Box.createHorizontalBox();
        b.add(panel);
        b.add(Box.createHorizontalGlue());
        return b;
    }
}
