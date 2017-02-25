package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.simulation.view.dialogs.AboutDialog;
import javafx.event.ActionEvent;

/**
 * Handles a click on the about menu button in the simulation view.
 *
 * @version 0.1
 * @author Niko Wilhelm
 */
public class MenuAboutButtonHandler extends AbstractMenuAboutButtonHandler {

    /**
     * Handles a click on the about menu button in the simulation view.
     * @param event The occured event.
     */
    public final void handle(ActionEvent event) {
        //throw new RuntimeException("Not implemented!");
        AboutDialog dia = new AboutDialog();
        dia.show();
    }
}
