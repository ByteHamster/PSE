package edu.kit.pse.osip.simulation.controller;

import javafx.application.Application;

/**
 * Main entry point for the whole simulation
 * @author David Kahles
 * @version 1.0
 */
public class MainClass {
    /**
     * Private constructor to avoid instantiation of the main class.
     */
    private MainClass () {

    }

    /**
     * Main entry point into the jar
     * @param args Command line arguments
     */
    public final static void main (String[] args) {
        Application.launch(SimulationController.class, args);
    }
}
