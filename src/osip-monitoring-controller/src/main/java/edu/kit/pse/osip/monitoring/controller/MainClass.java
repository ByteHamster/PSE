package edu.kit.pse.osip.monitoring.controller;

/**
 * The main class as entry point for the whole monitoring.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public final class MainClass {
    /**
     * Private constructor to avoid instantiation of the main class.
     */
    private MainClass() {
    }
    
    /**
     * Main entry point into the jar.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        MonitoringController.launch(MonitoringController.class, args);
    }
}
