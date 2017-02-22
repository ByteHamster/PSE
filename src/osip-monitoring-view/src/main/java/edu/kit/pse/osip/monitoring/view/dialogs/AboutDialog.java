package edu.kit.pse.osip.monitoring.view.dialogs;

import javafx.scene.layout.GridPane;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;

/**
 * This window shows information about the creators of OSIP.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class AboutDialog extends javafx.stage.Stage {    
    private static final int FONT_SIZE = MonitoringViewConstants.FONT_SIZE;
    private static final int MIN_WINDOW_WIDTH = 600;
    private static final int MIN_WINDOW_HEIGHT = 400;
    
    /**
     * Constructor of AboutDialog
     */
    public AboutDialog() {
        configure();
    }

    private void setDialogSize() {
        this.setMinWidth(MIN_WINDOW_WIDTH);
        this.setMinHeight(MIN_WINDOW_HEIGHT);
    }

    private void configureIcon(Image image, ImageView icon) {
        icon.setImage(image);
        icon.setFitWidth(120);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
    }
    
    private void configureScrollPane(ScrollPane scrollPane, Text text) {
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(text);
        scrollPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
    }

    private void configure() {
        this.setTitle(Translator.getInstance().getString("monitoring.aboutdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        
        ImageView aboutIcon = new ImageView();
        configureIcon(imageIcon, aboutIcon);      
        GridPane.setConstraints(aboutIcon, 0, 0);       
        
        String aboutString = Translator.getInstance().getString("monitoring.aboutdialog.aboutIntroduction");
        Text aboutText = new Text(aboutString);
        aboutText.setFont(new Font(FONT_SIZE));
        aboutText.setWrappingWidth(500);  
        GridPane.setConstraints(aboutText, 0, 1);        
        
        String licenseString = Translator.getInstance().getString("monitoring.aboutdialog.license");
        Text licenseText = new Text(licenseString);
        licenseText.setFont(new Font(FONT_SIZE));
        
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseText);
        GridPane.setConstraints(scrollPane, 0, 2);
                        
        grid.getChildren().addAll(aboutIcon, aboutText, scrollPane);        
        Scene scene = new Scene(grid, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        grid.setHgrow(scrollPane, Priority.ALWAYS);
        grid.setHgrow(aboutText, Priority.ALWAYS);
        
        
        this.setScene(scene);        
        this.setDialogSize();
    }
}