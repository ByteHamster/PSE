package edu.kit.pse.osip.monitoring.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * This window shows a short description of OSIP and how to use it.
 */
public class HelpDialog extends Stage {
    private Font sectionFont = new Font(20);
    private Font textFont = new Font(12);

    public HelpDialog() {
        setTitle(Translator.getInstance().getString("monitoring.helpdialog.title"));

        TabPane tabPane = new TabPane();

        tabPane.getTabs().add(createMainTab());
        tabPane.getTabs().add(createSettingsTab());

        setScene(new Scene(new Group(tabPane)));
        sizeToScene();
        show();
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("monitoring.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.introduction", textFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.layoutText", textFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.alarmText", textFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeSection", sectionFont));
        textFlow.getChildren().add(getText("monitoring.helpdialog.mainTab.gaugeText", textFont));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        tab.setContent(new BorderPane(scrollPane, null, null, null, null));

        tab.setClosable(false);

        return tab;
    }

    private Text getText(String id, Font font) {
        Text text = new Text();
        text.setText(id);
        text.setText(id + "\n");
        text.setFont(font);
        return text;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        return tab;
    }
}
