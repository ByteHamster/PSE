package org.iMage.edge.detection.gui;

/**
 * Displays the GUI
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class App {

    private App() {
        // Must not be initialized
    }

    /**
     * Main method
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Use "new GuiFrame(false)" to disable auto update
        new GuiFrame(true).setVisible(true);
    }
}
