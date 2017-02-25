package edu.kit.pse.osip.monitoring.view.dialogs;

import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStreamReader;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
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
    private static final int FONT_SIZE = MonitoringViewConstants.FONT_SIZE;
    private static final int MIN_WINDOW_WIDTH = 800;
    private static final int MIN_WINDOW_HEIGHT = 500;
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
        scrollPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
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
    
    private Text getLicenseText(String path) {
        InputStreamReader reader = null;
        reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path));        
        String fileContents = "";      
        int i;       
        try {
            while ((i = reader.read()) != -1) {
                char ch = (char) i;      
                fileContents = fileContents + ch; 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Text output = new Text(fileContents);
        output.setFont(Font.font("Monospaced", FONT_SIZE));
        return output;
    }
    
    private void configure() {
        this.setTitle(Translator.getInstance().getString("monitoring.aboutdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        hbox.setSpacing(10);
        GridPane.setConstraints(hbox, 0, 0);
        
        ImageView aboutIcon = new ImageView();
        configureIcon(imageIcon, aboutIcon);
        
        Text iconText = new Text(PROG_NAME);
        Font iconFont = new Font(FONT_SIZE * 7);
        iconText.setFont(iconFont);
        
        hbox.getChildren().addAll(aboutIcon, iconText);
           
        Font stdFont = new Font(FONT_SIZE);
        
        TextFlow introductionFlow = new TextFlow();
        introductionFlow.getChildren().add(getText("monitoring.aboutdialog.aboutIntroduction", stdFont));
        GridPane.setConstraints(introductionFlow, 0, 1); 
        
        TextFlow osipVersionFlow = new TextFlow();
        osipVersionFlow.getChildren().add(getText("monitoring.aboutdialog.version", stdFont));
        GridPane.setConstraints(osipVersionFlow, 0, 2);
               
        TextFlow licenseFlow = new TextFlow();
        licenseFlow.getChildren().add(getLicenseText("Licenses.txt"));
        
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseFlow);
        GridPane.setConstraints(scrollPane, 0, 3);
        GridPane.setMargin(scrollPane, new Insets(2 * MonitoringViewConstants.ELEMENTS_GAP, 0, 0, 0));
                        
        grid.getChildren().addAll(hbox, introductionFlow, osipVersionFlow, scrollPane);
        Scene scene = new Scene(grid, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        grid.setHgrow(introductionFlow, Priority.ALWAYS);
        grid.setHgrow(scrollPane, Priority.ALWAYS);
        grid.setVgrow(scrollPane, Priority.ALWAYS);
               
        this.setScene(scene);        
        this.setDialogSize();
    }
}
