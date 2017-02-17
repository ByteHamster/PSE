package edu.kit.pse.osip.simulation.view.dialogs;

import edu.kit.pse.osip.core.utils.language.Translator;
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

    public HelpDialog() {
        setTitle(Translator.getInstance().getString("controller.helpdialog.title"));
        getIcons().add(new Image("/icon.png"));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createMainTab(), createControlTab(), createSettingsTab());
        setScene(new Scene(tabPane));
        sizeToScene();
    }

    private Tab createMainTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.introduction", null));
        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.layoutSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.mainTab.layoutText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.prefHeight(-1));
        scrollPane.setPrefViewportWidth(textFlow.prefWidth(-1));

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

    private Tab createControlTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.introduction", null));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.generalSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.generalText", null));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.mixTankSection", sectionFont));
        textFlow.getChildren().add(getText("controller.helpdialog.controlTab.mixTankText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.prefHeight(-1));
        scrollPane.setPrefViewportWidth(textFlow.prefWidth(-1));

        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }

    private Tab createSettingsTab() {
        Tab tab = new Tab();
        tab.setText(Translator.getInstance().getString("controller.helpdialog.mainTab.header"));

        TextFlow textFlow = new TextFlow();

        textFlow.getChildren().add(getText("controller.helpdialog.settingslTab.generalText", null));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textFlow);
        scrollPane.setPrefViewportHeight(textFlow.prefHeight(-1));
        scrollPane.setPrefViewportWidth(textFlow.prefWidth(-1));

        tab.setContent(scrollPane);
        tab.setClosable(false);

        return tab;
    }
}
