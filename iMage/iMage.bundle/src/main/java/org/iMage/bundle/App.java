package org.iMage.bundle;

/**
 * Main class that just starts JMRJRST
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class App {
    private App() {
        // Main class must not be initialized
    }
    
    /**
     * Entry point for jar execution
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        org.jis.Main.main(args);
    }
}
