package edu.kit.pse.osip.simulation.view.dialogs;

import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;

/**
 * This window shows information about the creators of OSIP.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class AboutDialog extends javafx.stage.Stage {    
    private static final int FONT_SIZE = ViewConstants.FONT_SIZE;
    private static final int MIN_WINDOW_WIDTH = 600;
    private static final int MIN_WINDOW_HEIGHT = 400;
    private static final String PROG_NAME = "OSIP";
    
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
        icon.setFitWidth(8 * FONT_SIZE);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
    }
    
    private void configureScrollPane(ScrollPane scrollPane, TextFlow textFlow) {
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
    }

    private Text getText(String id, Font font) {
        Text text = new Text();
        text.setText(Translator.getInstance().getString(id) + "\n");
        if (font != null) {
            text.setFont(font);
        }
        return text;
    }
    
    private Text newLine() {
        return new Text("\n");
    }
    
    private void configure() {
        this.setTitle(Translator.getInstance().getString("monitoring.aboutdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
        hbox.setSpacing(10);
        GridPane.setConstraints(hbox, 0, 0);
        
        ImageView aboutIcon = new ImageView();
        configureIcon(imageIcon, aboutIcon);
        
        Text iconText = new Text(PROG_NAME);
        Font iconFont = new Font(FONT_SIZE * 7);
        iconText.setFont(iconFont);
        
        hbox.getChildren().addAll(aboutIcon, iconText);
                    
        TextFlow introductionFlow = new TextFlow();
        introductionFlow.getChildren().add(getText("monitoring.aboutdialog.aboutIntroduction", null));
        GridPane.setConstraints(introductionFlow, 0, 1); 
        
        TextFlow licenseFlow = new TextFlow();
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.head", null));
        licenseFlow.getChildren().add(newLine());
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.copyright", null));
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.author1", null));
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.author2", null));
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.author3", null));
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.author4", null));
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.author5", null));
        licenseFlow.getChildren().add(newLine());
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.mainParagraph1", null));
        licenseFlow.getChildren().add(newLine());
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.mainParagraph2", null));
        licenseFlow.getChildren().add(newLine());
        licenseFlow.getChildren().add(getText("monitoring.aboutdialog.license.mainParagraph3", null));
                      
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseFlow);
        GridPane.setConstraints(scrollPane, 0, 2);
        GridPane.setMargin(scrollPane, new Insets(2 * ViewConstants.ELEMENTS_GAP, 0, 0, 0));
                        
        grid.getChildren().addAll(hbox, introductionFlow, scrollPane);
        Scene scene = new Scene(grid, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        grid.setHgrow(scrollPane, Priority.ALWAYS);
        grid.setHgrow(introductionFlow, Priority.ALWAYS);
        grid.setVgrow(scrollPane, Priority.ALWAYS);        
        
        this.setScene(scene);        
        this.setDialogSize();
    }
}
