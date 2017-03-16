package edu.kit.pse.osip.simulation.view.dialogs;

import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This window shows information about the creators of OSIP.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class AboutDialog extends Stage {
    private static final int FONT_SIZE = ViewConstants.FONT_SIZE;
    private static final int MIN_WINDOW_WIDTH = 800;
    private static final int MIN_WINDOW_HEIGHT = 500;
    private static final int HBOX_SPACING = 10;
    
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
    
    private void configure() {
        this.setTitle(Translator.getInstance().getString("simulation.aboutdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
        hbox.setSpacing(HBOX_SPACING);
        GridPane.setConstraints(hbox, 0, 0);
        
        ImageView aboutIcon = new ImageView();
        configureIcon(imageIcon, aboutIcon);
        
        Text iconText = new Text(Translator.getInstance().getString("simulation.aboutdialog.heading"));
        Font iconFont = new Font(FONT_SIZE * 7);
        iconText.setFont(iconFont);
        
        hbox.getChildren().addAll(aboutIcon, iconText);
           
        Font stdFont = new Font(FONT_SIZE);
        
        TextFlow introductionFlow = new TextFlow();
        introductionFlow.getChildren().add(getText("simulation.aboutdialog.aboutIntroduction", stdFont));
        GridPane.setConstraints(introductionFlow, 0, 1);
        GridPane.setMargin(introductionFlow, new Insets(ViewConstants.ELEMENTS_GAP * 2, 0,
                ViewConstants.ELEMENTS_GAP, 0));
        
        TextFlow osipVersionFlow = new TextFlow();
        osipVersionFlow.getChildren().add(getText("simulation.aboutdialog.version", stdFont));
        GridPane.setConstraints(osipVersionFlow, 0, 2);
               
        TextFlow licenseFlow = new TextFlow();
        licenseFlow.getChildren().add(getLicenseText("Licenses.txt"));
        
        ScrollPane scrollPane = new ScrollPane();
        configureScrollPane(scrollPane, licenseFlow);
        GridPane.setConstraints(scrollPane, 0, 3);
        GridPane.setMargin(scrollPane, new Insets(2 * ViewConstants.ELEMENTS_GAP, 0, 0, 0));
                        
        grid.getChildren().addAll(hbox, introductionFlow, osipVersionFlow, scrollPane);
        Scene scene = new Scene(grid, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        GridPane.setHgrow(introductionFlow, Priority.ALWAYS);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
               
        this.setScene(scene);
        this.setDialogSize();
    }
}
