package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.monitoring.view.dialogs.HelpDialog;
import javafx.event.ActionEvent;

/**
 * Handles a click on the help menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class MenuHelpButtonHandler extends AbstractMenuHelpButtonHandler {
    @Override
    public void handle(ActionEvent event) {
        HelpDialog d = new HelpDialog();
        d.show();
    }
}
