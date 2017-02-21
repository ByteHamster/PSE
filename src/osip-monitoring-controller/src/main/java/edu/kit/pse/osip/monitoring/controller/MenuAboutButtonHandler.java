package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.monitoring.view.dialogs.AboutDialog;
import javafx.event.ActionEvent;

/**
 * Handles a click on the about menu button in the monitoring view.
 * 
 * @author Martin Armbruster, Maximilian Schwarzmann
 * @version 1.0
 */
public class MenuAboutButtonHandler extends AbstractMenuAboutButtonHandler {
    @Override
    public void handle(ActionEvent event) {
        AboutDialog d = new AboutDialog();
        d.show();
    }
}
