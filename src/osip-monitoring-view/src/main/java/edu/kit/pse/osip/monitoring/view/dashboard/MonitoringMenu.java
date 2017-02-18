package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.controller.AbstractMenuAboutButtonHandler;
import edu.kit.pse.osip.monitoring.controller.AbstractMenuHelpButtonHandler;
import edu.kit.pse.osip.monitoring.controller.AbstractMenuSettingsButtonHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Contains all controls for the menu bar.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class MonitoringMenu extends MenuBar {
    /**
     * Menu containing all menu buttons for file interaction.
     */
    private Menu menuFile;
    
    /**
     * Menu button in the files menu for displaying the settings.
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
     * Button in the help menu for showing the about dialog.
     */
    private MenuItem menuButtonAbout;
    
    /**
     * Creates and initializes the menu for the monitoring view.
     */
    protected MonitoringMenu() {
        menuFile = new Menu(Translator.getInstance().getString("monitoring.menu.file"));
        menuButtonSettings = new MenuItem(Translator.getInstance().getString("monitoring.menu.file.settings"));
        menuFile.getItems().add(menuButtonSettings);
        
        menuHelp = new Menu(Translator.getInstance().getString("monitoring.menu.help"));
        menuButtonHelp = new MenuItem(Translator.getInstance().getString("monitoring.menu.help.help"));
        menuHelp.getItems().add(menuButtonHelp);
        menuButtonAbout = new MenuItem(Translator.getInstance().getString("monitoring.menu.help.about"));
        menuHelp.getItems().add(menuButtonAbout);
        
        this.getMenus().addAll(menuFile, menuHelp);
    }
    
    /**
     * Sets the handler for the settings menu button.
     * 
     * @param handler The handler for the settings menu button.
     */
    protected void setMenuSettingsButtonHandler(AbstractMenuSettingsButtonHandler handler) {
        menuButtonSettings.setOnAction(handler);
    }
    
    /**
     * Sets the handler for the about menu button.
     * 
     * @param handler The handler for the about menu button.
     */
    protected void setMenuAboutButtonHandler(AbstractMenuAboutButtonHandler handler) {
        menuButtonAbout.setOnAction(handler);
    }
    
    /**
     * Sets the handler for the help menu button.
     * 
     * @param handler The handler for the help menu button.
     */
    protected void setMenuHelpButtonHandler(AbstractMenuHelpButtonHandler handler) {
        menuButtonHelp.setOnAction(handler);
    }
}
