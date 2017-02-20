package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.AbstractMenuSettingsButtonHandler;
import edu.kit.pse.osip.simulation.controller.AbstractMenuAboutButtonHandler;
import edu.kit.pse.osip.simulation.controller.AbstractMenuControlButtonHandler;
import edu.kit.pse.osip.simulation.controller.AbstractMenuHelpButtonHandler;
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
    private Menu menuButtonView;
    private MenuItem menuItemControl;

    /**
     * Menu containing all buttons leading to ways to change the settings of the simulation.
     */
    private Menu menuButtonFile;
    private MenuItem menuItemSettings;

    /**
     * Menu containing all buttons for help.
     */
    private Menu menuOther;

    /**
     * Button in the help menu for showing the help dialog.
     */
    private MenuItem menuItemHelp;

    /**
     * Button in the about menu for showing the about dialog.
     */
    private MenuItem menuItemAbout;

    /**
     * Creates and initializes the menu for the simulation view.
     */
    protected SimulationMenu() {
        this.setStyle("-fx-font-size:" + ViewConstants.FONT_SIZE + "px;");
        Translator trans = Translator.getInstance();

        menuButtonFile = new Menu(trans.getString("simulation.view.menu.file"));
        menuItemSettings = new MenuItem(trans.getString("simulation.view.menu.file.settings"));
        menuButtonFile.getItems().add(menuItemSettings);

        menuButtonView = new Menu(trans.getString("simulation.view.menu.view"));
        menuItemControl = new MenuItem(trans.getString("simulation.view.menu.view.control"));
        menuButtonView.getItems().add(menuItemControl);

        menuOther = new Menu(trans.getString("simulation.view.menu.other"));
        menuItemHelp = new MenuItem(trans.getString("simulation.view.menu.other.help"));
        menuItemAbout = new MenuItem(trans.getString("simulation.view.menu.other.about"));
        menuOther.getItems().add(menuItemHelp);
        menuOther.getItems().add(menuItemAbout);

        this.getMenus().addAll(menuButtonFile, menuButtonView, menuOther);
    }

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public final void setControlButtonHandler(AbstractMenuControlButtonHandler controlButtonHandler) {
        menuItemControl.setOnAction(controlButtonHandler);
    }

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler(AbstractMenuSettingsButtonHandler settingsButtonHandler) {
        menuItemSettings.setOnAction(settingsButtonHandler);
    }

    /**
     * Sets the handler for pressing the about entry in the menu
     * @param aboutButtonHandler The handler to be called when the about button is pressed
     */
    public final void setAboutButtonHandler(AbstractMenuAboutButtonHandler aboutButtonHandler) {
        menuItemAbout.setOnAction(aboutButtonHandler);
    }

    /**
     * Sets the handler for pressing the help entry in the menu
     * @param helpButtonHandler The handler to be called when the help button is pressed
     */
    public final void setHelpButtonHandler(AbstractMenuHelpButtonHandler helpButtonHandler) {
        menuItemHelp.setOnAction(helpButtonHandler);
    }
}
