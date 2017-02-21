package edu.kit.pse.osip.simulation.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import edu.kit.pse.osip.core.SimulationConstants;


/**
 * This window shows a short description of OSIP and how to use it.
 *
 * @author David Kahles
 * @version 1.0
 */
public class HelpDialog extends Stage {
    private Font sectionFont = new Font(20);
    private static final double WINDOW_HEIGHT = 400;
    private static final double WINDOW_WIDTH = 600;

    /**
     * Creates a new HelpDialog.
     */
    public HelpDialog() {
        setTitle(Translator.getInstance().getString("controller.helpdialog.title"));
        getIcons().add(new Image("/icon.png"));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createMainTab(), createControlTab(), createSettingsTab());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Button close = new Button(Translator.getInstance().getString("monitoring.helpdialog.closeButton"));
        close.setDefaultButton(true);
        close.setCancelButton(true);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();
            }
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(tabPane);
        pane.setBottom(close);
        BorderPane.setAlignment(close, Pos.CENTER);
        setScene(new Scene(pane));
        setHeight(WINDOW_HEIGHT);
        setWidth(WINDOW_WIDTH);
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.layoutText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.getPrefHeight());
        scrollPane.setPrefViewportWidth(textFlow.getPrefWidth());
        scrollPane.setPadding(new Insets(5));


        tab.setContent(scrollPane);

        return tab;
    }

    private Text newLine() {
        return new Text("\n");
    }

    private Text getText(String id, Font font) {
        Text text = new Text();
        text.setText(Translator.getInstance().getString(id) + "\n");
        if (font != null) {
            text.setFont(font);
        }
        return text;
    }

    private Tab createControlTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.controlTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.generalSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.generalText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.mixTankSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.mixTankText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.getPrefHeight());
        scrollPane.setPrefViewportWidth(textFlow.getPrefWidth());
        scrollPane.setPadding(new Insets(5));

        tab.setContent(scrollPane);

        return tab;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.settingsTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.settingslTab.generalText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.getPrefHeight());
        scrollPane.setPrefViewportWidth(textFlow.getPrefWidth());
        scrollPane.setPadding(new Insets(5));

        tab.setContent(scrollPane);

        return tab;
    }
}
