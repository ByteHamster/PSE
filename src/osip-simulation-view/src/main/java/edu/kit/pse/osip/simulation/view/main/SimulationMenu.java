package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.AbstractMenuSettingsButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuAboutButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuControlButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuHelpButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * This class is the MenuBar at the top of the simulation view. It provides options to control the
 * simulation, change the settings and get help.
 *
 * @author Niko Wilhelm
 * @version 1.0
 */
public class SimulationMenu extends MenuBar {

    /**
     * Menu containing all buttons leading to ways to conrol the simulation.
     */
    private Menu menuControl;

    /**
     * Menu button in the control menu for displaying the control window.
     */
    private MenuItem menuButtonControl;

    /**
     * Menu containing all buttons leading to ways to change the settings of the simulation.
     */
    private Menu menuSettings;

    /**
     * Menu button in the settings menu for displaying the settings window.
     */
    private MenuItem menuButtonSettings;

    /**
     * Menu containing all buttons for help.
     */
    private Menu menuHelp;

    /**
     * Button in the help menu for showing the help dialog.
     */
    private MenuItem menuButtonHelp;

    /**
     * Menu containg the button leading to the about dialog.
     */
    private Menu menuAbout;

    /**
     * Button in the about menu for showing the about dialog.
     */
    private MenuItem menuButtonAbout;

    /**
     * Creates and initializes the menu for the simulation view.
     */
    protected SimulationMenu() {
        Translator trans = Translator.getInstance();
        menuControl = new Menu(trans.getString("simulation.view.menu.control"));
        menuButtonControl = new MenuItem(trans.getString("simulation.view.menu.control.control"));
        menuControl.getItems().add(menuButtonControl);

        menuSettings = new Menu(trans.getString("simulation.view.menu.settings"));
        menuButtonSettings = new Menu(trans.getString("simulation.view.menu.settings.settings)"));
        menuSettings.getItems().add(menuButtonSettings);

        menuHelp = new Menu(trans.getString("simulation.view.menu.help"));
        menuButtonHelp = new Menu(trans.getString("simulation.view.menu.help.help"));
        menuHelp.getItems().add(menuButtonHelp);

        menuAbout = new Menu(trans.getString("simulation.view.menu.about"));
        menuButtonAbout = new Menu(trans.getString("simulation.view.menu.about.about"));
        menuAbout.getItems().add(menuButtonAbout);

        this.getMenus().addAll(menuControl, menuSettings, menuHelp, menuAbout);
    }

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public final void setControlButtonHandler(AbstractMenuControlButton controlButtonHandler) {
        menuButtonControl.setOnAction(controlButtonHandler);
    }

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler(AbstractMenuSettingsButton settingsButtonHandler) {
        menuButtonSettings.setOnAction(settingsButtonHandler);
    }

    /**
     * Sets the handler for pressing the about entry in the menu
     * @param aboutButtonHandler The handler to be called when the about button is pressed
     */
    public final void setAboutButtonHandler(AbstractMenuAboutButton aboutButtonHandler) {
        menuButtonAbout.setOnAction(aboutButtonHandler);
    }

    /**
     * Sets the handler for pressing the help entry in the menu
     * @param helpButtonHandler The handler to be called when the help button is pressed
     */
    public final void setHelpButtonHandler(AbstractMenuHelpButton helpButtonHandler) {
        menuButtonHelp.setOnAction(helpButtonHandler);
    }
}
