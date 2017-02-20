package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.AbstractMenuSettingsButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuAboutButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuControlButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuHelpButton;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

/**
 * This class is the MenuBar at the top of the simulation view. It provides options to control the
 * simulation, change the settings and get help.
 *
 * @author Niko Wilhelm
 * @version 1.0
 */
public class SimulationMenu extends GridPane {

    /**
     * Menu containing all buttons leading to ways to conrol the simulation.
     */
    private Button buttonControl;

    /**
     * Menu containing all buttons leading to ways to change the settings of the simulation.
     */
    private Button buttonSettings;

    /**
     * Menu containing all buttons for help.
     */
    private Menu menuOther;

    /**
     * Button in the help menu for showing the help dialog.
     */
    private MenuItem menuButtonHelp;

    /**
     * Button in the about menu for showing the about dialog.
     */
    private MenuItem menuButtonAbout;

    /**
     * Creates and initializes the menu for the simulation view.
     */
    protected SimulationMenu() {
        Translator trans = Translator.getInstance();
        buttonControl = new Button(trans.getString("simulation.view.menu.control"));

        buttonSettings = new Button(trans.getString("simulation.view.menu.settings"));

        menuOther = new Menu(trans.getString("simulation.view.menu.help"));
        menuButtonHelp = new Menu(trans.getString("simulation.view.menu.help.help"));
        menuButtonAbout = new Menu(trans.getString("simulation.view.menu.about.about"));
        menuOther.getItems().addAll(menuButtonHelp, menuButtonAbout);
        MenuBar bar = new MenuBar();
        bar.getMenus().addAll(menuOther);

        this.add(buttonControl, 0, 0);
        this.add(buttonSettings, 1, 0);

        this.add(bar, 2, 0);
    }

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public final void setControlButtonHandler(AbstractMenuControlButton controlButtonHandler) {
        buttonControl.setOnAction(controlButtonHandler);
    }

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler(AbstractMenuSettingsButton settingsButtonHandler) {
        buttonSettings.setOnAction(settingsButtonHandler);
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
