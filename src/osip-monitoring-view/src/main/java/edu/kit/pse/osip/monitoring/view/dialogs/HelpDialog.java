package edu.kit.pse.osip.monitoring.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
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
    private final double WINDOW_HEIGHT = 400;
    private final double WINDOW_WIDTH = 600;

    public HelpDialog() {
        setTitle(Translator.getInstance().getString("monitoring.helpdialog.title"));
        getIcons().add(new Image("/icon.png"));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createMainTab(), createSettingsTab());
        setScene(new Scene(tabPane));
        setHeight(WINDOW_HEIGHT);
        setWidth(WINDOW_WIDTH);
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("monitoring.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.introduction", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutText", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmText", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeText", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.progressSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.progressText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.prefHeight(-1));
        scrollPane.setPrefViewportWidth(textFlow.prefWidth(-1));
        scrollPane.setPadding(new Insets(5));

        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }

    private Text getText(String id, Font font) {
        Text text = new Text();
        text.setText(Translator.getInstance().getString(id) + "\n");
        if (font != null) {
            text.setFont(font);
        }
        return text;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("monitoring.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.introduction", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.generalSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.generalText", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.progressSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.progressText", null));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.alarmSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.settingsTab.alarmText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.prefHeight(-1));
        scrollPane.setPrefViewportWidth(textFlow.prefWidth(-1));
        scrollPane.setPadding(new Insets(5));

        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }
}
