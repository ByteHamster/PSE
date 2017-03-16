package edu.kit.pse.osip.simulation.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
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
    private static final double MIN_WINDOW_SIZE = 300;

    /**
     * Creates a new HelpDialog.
     */
    public HelpDialog() {
        setTitle(Translator.getInstance().getString("simulation.helpdialog.title"));
        getIcons().add(new Image("/icon.png"));

        setupView();
    }

    private void setupView() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createMainTab(), createControlTab(), createSettingsTab(), createScenarioTab());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Button close = new Button(Translator.getInstance().getString("simulation.helpdialog.closeButton"));
        close.setDefaultButton(true);
        close.setCancelButton(true);
        close.setOnAction(actionEvent -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-font-size: " + ViewConstants.FONT_SIZE + "px;");
        pane.setCenter(tabPane);
        pane.setBottom(close);
        pane.setPadding(new Insets(0, 0, ViewConstants.ELEMENTS_GAP, 0));
        BorderPane.setAlignment(close, Pos.CENTER);
        setScene(new Scene(pane));
        setHeight(WINDOW_HEIGHT);
        setWidth(WINDOW_WIDTH);
        setMinWidth(MIN_WINDOW_SIZE);
        setMinHeight(MIN_WINDOW_SIZE);
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("simulation.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("simulation.helpdialog.mainTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("simulation.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("simulation.helpdialog.mainTab.layoutText", null));

        tab.setContent(setupScrollPane(textFlow));

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

    private ScrollPane setupScrollPane(TextFlow textFlow) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.getPrefHeight());
        scrollPane.setPrefViewportWidth(textFlow.getPrefWidth());
        scrollPane.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));
        return scrollPane;
    }

    private Tab createControlTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("simulation.helpdialog.controlTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("simulation.helpdialog.controlTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("simulation.helpdialog.controlTab.generalSection", sectionFont));
        textFlow.getChildren().add(getText("simulation.helpdialog.controlTab.generalText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("simulation.helpdialog.controlTab.mixTankSection", sectionFont));
        textFlow.getChildren().add(getText("simulation.helpdialog.controlTab.mixTankText", null));

        tab.setContent(setupScrollPane(textFlow));

        return tab;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("simulation.helpdialog.settingsTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("simulation.helpdialog.settingsTab.generalText", null));

        tab.setContent(setupScrollPane(textFlow));

        return tab;
    }

    private Tab createScenarioTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("simulation.helpdialog.scenarioTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("simulation.helpdialog.scenarioTab.introduction", null));

        tab.setContent(setupScrollPane(textFlow));

        return tab;
    }
}
