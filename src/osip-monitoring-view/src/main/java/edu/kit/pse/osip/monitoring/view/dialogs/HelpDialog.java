package edu.kit.pse.osip.monitoring.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
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
     * Creates a new HelpDialog
     */
    public HelpDialog() {
        setTitle(Translator.getInstance().getString("monitoring.helpdialog.title"));
        getIcons().add(new Image("/icon.png"));

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(createMainTab(), createSettingsTab());

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
        pane.setStyle("-fx-font-size: " + MonitoringViewConstants.FONT_SIZE + "px;");
        pane.setCenter(tabPane);
        pane.setBottom(close);
        pane.setPadding(new Insets(0, 0, MonitoringViewConstants.ELEMENTS_GAP, 0));
        BorderPane.setAlignment(close, Pos.CENTER);
        setScene(new Scene(pane));
        setHeight(WINDOW_HEIGHT);
        setWidth(WINDOW_WIDTH);
        setMinWidth(MIN_WINDOW_SIZE);
        setMinHeight(MIN_WINDOW_SIZE);
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("monitoring.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.progressSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.progressText", null));

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
        scrollPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        return scrollPane;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("monitoring.helpdialog.settingsTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.introduction", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.generalSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.generalText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.progressSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.progressText", null));
        textFlow.getChildren().add(newLine());
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.alarmSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.alarmText", null));

        tab.setContent(setupScrollPane(textFlow));

        return tab;
    }
}
