package edu.kit.pse.osip.monitoring.controller;

import java.util.Locale;
import javafx.application.Application;

/**
 * The main class as entry point for the whole monitoring.
 * 
 * @author Martin Armbruster
 * @version 1.1
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
        Locale.setDefault(Locale.US);
        Application.launch(MonitoringController.class, args);
    }
}
