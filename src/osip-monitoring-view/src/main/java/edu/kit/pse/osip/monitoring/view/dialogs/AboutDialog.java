package edu.kit.pse.osip.monitoring.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This window shows information about the creators of OSIP.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class AboutDialog extends Stage {
    /**
     * The default font size.
     */
    private static final int FONT_SIZE = MonitoringViewConstants.FONT_SIZE;
    /**
     * The minimum width of the window.
     */
    private static final int MIN_WINDOW_WIDTH = 800;
    /**
     * The minimum height of the window.
     */
    private static final int MIN_WINDOW_HEIGHT = 500;
    
    /**
     * Constructor of AboutDialog.
     */
    public AboutDialog() {
        configure();
    }

    /**
     * Sets the window size.
     */
    private void setDialogSize() {
        this.setMinWidth(MIN_WINDOW_WIDTH);
        this.setMinHeight(MIN_WINDOW_HEIGHT);
    }

    /**
     * Configures an ImageView for showing an Image.
     * 
     * @param image the image to be shown.
     * @param icon the ImageView for showing image.
     */
    private void configureIcon(Image image, ImageView icon) {
        icon.setImage(image);
        icon.setFitWidth(8 * FONT_SIZE);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
    }
    
    /**
     * Configures a ScrollPane to show a TextFlow.
     * 
     * @param scrollPane the ScrollPane for showing textFlow.
     * @param textFlow the TextFlow to be shown.
     */
    private void configureScrollPane(ScrollPane scrollPane, TextFlow textFlow) {
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
    }

    /**
     * Generates a Text instance with a specified font and a text from the Translator.
     * 
     * @param id the id for the text from the Translator.
     * @param font the font.
     * @return the generated Text instance.
     */
    private Text getText(String id, Font font) {
        Text text = new Text();
        text.setText(Translator.getInstance().getString(id) + "\n");
        if (font != null) {
            text.setFont(font);
        }
        return text;
    }
    
    /**
     * Reads the license.
     * 
     * @param path path to the license file.
     * @return the license text.
     */
    private Text getLicenseText(String path) {
        InputStream inStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder builder = new StringBuilder();
        String aux;
        try {
            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = builder.toString();
        Text output = new Text(fileContents);
        output.setFont(Font.font("Monospaced", FONT_SIZE));
        return output;
    }
    
    /**
     * Configures the dialog.
     */
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
        
        Text iconText = new Text(Translator.getInstance().getString("monitoring.aboutdialog.heading"));
        Font iconFont = new Font(FONT_SIZE * 7);
        iconText.setFont(iconFont);
        
        hbox.getChildren().addAll(aboutIcon, iconText);
           
        Font stdFont = new Font(FONT_SIZE);
        
        TextFlow introductionFlow = new TextFlow();
        introductionFlow.getChildren().add(getText("monitoring.aboutdialog.aboutIntroduction", stdFont));
        GridPane.setConstraints(introductionFlow, 0, 1);
        GridPane.setMargin(introductionFlow, new Insets(2 * MonitoringViewConstants.ELEMENTS_GAP, 0, 0, 0));
        
        TextFlow osipVersionFlow = new TextFlow();
        osipVersionFlow.getChildren().add(getText("monitoring.aboutdialog.version", stdFont));
        GridPane.setConstraints(osipVersionFlow, 0, 2);
        GridPane.setMargin(osipVersionFlow, new Insets(MonitoringViewConstants.ELEMENTS_GAP, 0, 0, 0));
               
        TextFlow licenseFlow = new TextFlow();
        licenseFlow.getChildren().add(getLicenseText("Licenses.txt"));
        
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseFlow);
        GridPane.setConstraints(scrollPane, 0, 3);
        GridPane.setMargin(scrollPane, new Insets(2 * MonitoringViewConstants.ELEMENTS_GAP, 0, 0, 0));
                        
        grid.getChildren().addAll(hbox, introductionFlow, osipVersionFlow, scrollPane);
        Scene scene = new Scene(grid, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        GridPane.setHgrow(introductionFlow, Priority.ALWAYS);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
               
        this.setScene(scene);        
        this.setDialogSize();
    }
}
