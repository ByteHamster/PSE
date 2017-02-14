package edu.kit.pse.osip.simulation.controller;

/**
 * This Listener listens for changes in the settings
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@FunctionalInterface
public interface SettingsChangedListener {
    /**
     * Alerts the Controller that the settings have been changed.
     */
    void onSettingsChanged();
}
