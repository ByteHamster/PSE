package edu.kit.pse.osip.simulation.view.main;

import java.util.Observable;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.simulation.controller.MenuAboutButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuHelpButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuSettingsButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuControlButtonHandler;

/**
 * The main window for visualizing the OSIP simulation. It regularly updates itself with current information from the model and posesses an update() method for alarms. If an overflow occurs in the model it is be displayed by an overlay.
 */
public class SimulationMainWindow implements edu.kit.pse.osip.simulation.controller.SimulationViewInterface, java.util.Observer {
    public ProductionSite productionSite;
    public Drawer element;
    /**
     * Creates a new SimulationMainWindow
     * @param productionSite The ProductionSite object, so that the view can access the model
     */
    public SimulationMainWindow (ProductionSite productionSite) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * The stage that is provided by JavaFx
     * @param primaryStage The stage to draw the window on
     */
    public final void start (javafx.stage.Stage primaryStage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * The simulation is replaced by the OverflowOverlay.
     */
    public final void showOverflow () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * The observed object received an update
     * @param observable The observed object
     * @param object The new value
     */
    public final void update (Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public final void setControlButtonHandler (MenuControlButtonHandler controlButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler (MenuSettingsButtonHandler settingsButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for pressing the about entry in the menu
     * @param aboutButtonHandler The handler to be called when the about button is pressed
     */
    public final void setAboutButtonHandler (MenuAboutButtonHandler aboutButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for pressing the help entry in the menu
     * @param helpButtonHandler The handler to be called when the help button is pressed
     */
    public final void setHelpButtonHandler (MenuHelpButtonHandler helpButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }
}
