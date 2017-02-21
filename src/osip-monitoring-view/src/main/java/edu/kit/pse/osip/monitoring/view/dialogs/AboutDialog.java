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
    
    /**
     * Constructor of AboutDialog
     */
    public AboutDialog() {
        configure();
    }

    private void setDialogSize() {
        this.setMinWidth(600);
        //this.setMaxWidth(800);
        this.setMinHeight(400);
        //this.setMaxHeight(700);
    }

    private void configureIcon(Image image, ImageView icon) {
        icon.setImage(image);
        icon.setFitWidth(110);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
    }
    
    private void configureScrollPane(ScrollPane scrollPane, Text text) {
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(text);
        //scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
    }

    private void configure() {
        this.setTitle(Translator.getInstance().getString("monitoring.aboutdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setPadding(new Insets(0, 0,  MonitoringViewConstants.ELEMENTS_GAP, 0));
        
        ImageView aboutIcon = new ImageView();
        configureIcon(imageIcon, aboutIcon);
        //aboutIcon.setCache(true);  // use if slow image loading        
        GridPane.setConstraints(aboutIcon, 0, 0);       
        
        String aboutString = Translator.getInstance().getString("monitoring.aboutdialog.aboutIntroduction");
        Text aboutText = new Text(aboutString);
        aboutText.setFont(new Font(FONT_SIZE));
        aboutText.setWrappingWidth(800);  
        GridPane.setConstraints(aboutText, 0, 1);        
        
        String licenseString = Translator.getInstance().getString("monitoring.aboutdialog.license");
        Text licenseText = new Text(licenseString);
        aboutText.setFont(new Font(FONT_SIZE));
        aboutText.setWrappingWidth(300);
        
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseText);
        GridPane.setConstraints(scrollPane, 0, 2);
                        
        grid.getChildren().addAll(aboutIcon, aboutText, scrollPane);        
        Scene scene = new Scene(grid, 600, 400);
        
        grid.setHgrow(scrollPane, Priority.ALWAYS);
        grid.setHgrow(aboutText, Priority.ALWAYS);
        
        
        this.setScene(scene);        
        this.setDialogSize();
    }
}