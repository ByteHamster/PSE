package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.monitoring.controller.MenuAboutButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuHelpButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuSettingsButtonHandler;

/**
 * Contains all controls for the menu bar.
 */
public class MonitoringMenu extends javafx.scene.control.MenuBar {
    /**
     * Menu containing all menu buttons for file interaction.
     */
    private javafx.scene.control.Menu menuFile;
    /**
     * Menu button in the files menu for displaying the settings.
     */
    private javafx.scene.control.MenuButton menuButtonSettings;
    /**
     * Menu containing all buttons for help.
     */
    private javafx.scene.control.Menu menuHelp;
    /**
     * Button in the help menu for showing the help dialog.
     */
    private javafx.scene.control.MenuButton menuButtonHelp;
    /**
     * Button in the help menu for showing the about dialog.
     */
    private javafx.scene.control.MenuButton menuButtonAbout;
    /**
     * Creates and initializes the menu for the monitoring view.
     */
    protected MonitoringMenu () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the settings menu button.
     * @param handler The handler for the settings menu button.
     */
    protected final void setMenuSettingsButtonHandler (MenuSettingsButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the about menu button.
     * @param handler The handler for the about menu button.
     */
    protected final void setMenuAboutButtonHandler (MenuAboutButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the help menu button.
     * @param handler The handler for the help menu button.
     */
    protected final void setMenuHelpButtonHandler (MenuHelpButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
}
