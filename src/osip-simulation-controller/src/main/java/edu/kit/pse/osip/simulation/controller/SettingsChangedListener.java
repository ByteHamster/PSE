package edu.kit.pse.osip.simulation.controller;

/**
 * This Listener listens for changes in the settings
 */
public interface SettingsChangedListener {
    /**
     * Alerts the Controller that the settings have been changed.
     */
    public void onSettingsChanged ();
}
