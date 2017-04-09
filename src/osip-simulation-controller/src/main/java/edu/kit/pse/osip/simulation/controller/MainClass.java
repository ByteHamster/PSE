package edu.kit.pse.osip.simulation.controller;

import java.util.Locale;
import javafx.application.Application;

/**
 * Main entry point for the whole simulation.
 * 
 * @author David Kahles
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
        Locale.setDefault(Locale.US);
        Application.launch(SimulationController.class, args);
    }
}
