package edu.kit.pse.osip.simulation.view.settings;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.formatting.FormatChecker;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.SimulationSettingsInterface;
import java.util.EnumMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This class is the main window of the OSIP simulation settings.
 *
 * @author Hans-Peter
 * @version 1.0
 */
public class SimulationSettingsWindow implements SimulationSettingsInterface {
    /**
     * The current settings.
     */
    private ServerSettingsWrapper settings;
    /**
     * The actual settings window.
     */
    private Stage settingsStage;
    /**
     * Contains all PortTextFields for the tanks.
     */
    private EnumMap<TankSelector, PortTextField> portTextFields;
    /**
     * Handler for the save button.
     */
    private EventHandler<ActionEvent> listener;
    /**
     * Stores the save button.
     */
    private Button btnSave;
    /**
     * Spacing between the components.
     */
    private static final int SPACING = 10;
    /**
     * The minimum size of the window.
     */
    private static final double MIN_WINDOW_SIZE = 250;
    /**
     * The maximum height of the window.
     */
    private static final int MAX_HEIGHT = 400;

    /**
     * Generates a new window that shows the simulation settings.
     * 
     * @param settings Wrapper for saved settings.
     */
    public SimulationSettingsWindow(ServerSettingsWrapper settings) {
        this.settings = settings;
        this.portTextFields = new EnumMap<>(TankSelector.class);

        settingsStage = new Stage();
        Scene scene = new Scene(createRootLayout());
        settingsStage.setScene(scene);
        settingsStage.setTitle(Translator.getInstance().getString("simulation.settings.title"));
        settingsStage.getIcons().add(new Image("icon.png"));
        settingsStage.setMinWidth(MIN_WINDOW_SIZE);
        settingsStage.setMinHeight(MIN_WINDOW_SIZE);

        resetValues();
    }

    /**
     * Creates the settings main layout.
     * 
     * @return The layout.
     */
    private BorderPane createRootLayout() {
        Translator t = Translator.getInstance();

        GridPane portBoxes = new GridPane();
        portBoxes.setHgap(SPACING);
        portBoxes.setVgap(SPACING);
        portBoxes.setAlignment(Pos.CENTER);

        int row = 0;
        for (TankSelector tank : TankSelector.values()) {
            Label portLabel = new Label(t.getString(TankSelector.TRANSLATOR_LABEL_PREFIX + tank.name()));
            PortTextField portText = new PortTextField();
            portTextFields.put(tank, portText);
            portBoxes.add(portLabel, 0, row);
            portBoxes.add(portText, 1, row);
            portText.textProperty().addListener((observable, oldValue, newValue) -> updateSaveButton());
            row++;
        }

        Label portHeading = new Label(t.getString("simulation.settings.port"));
        portHeading.setPadding(new Insets(0, 0, SPACING, 0));

        HBox buttons = new HBox(SPACING);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setPadding(new Insets(SPACING, 0, 0, 0));

        btnSave = new Button(t.getString("simulation.settings.save"));
        btnSave.setDefaultButton(true);
        btnSave.setOnAction(e -> {
            if (listener != null) {
                listener.handle(e);
            } else {
                System.err.println("No SettingsChangedListener set");
            }
        });

        Button btnCancel = new Button(t.getString("simulation.settings.cancel"));
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(e ->
            settingsStage.close()
        );

        buttons.getChildren().add(btnCancel);
        buttons.getChildren().add(btnSave);

        ScrollPane scrollPortBoxes = new ScrollPane();
        scrollPortBoxes.setPadding(new Insets(SPACING));
        scrollPortBoxes.setContent(portBoxes);
        scrollPortBoxes.setFitToWidth(true);
        scrollPortBoxes.setMaxHeight(MAX_HEIGHT);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(SPACING));
        rootLayout.setTop(portHeading);
        rootLayout.setCenter(scrollPortBoxes);
        rootLayout.setBottom(buttons);
        return rootLayout;
    }

    /**
     * Updates enabled state of the save button.
     */
    private void updateSaveButton() {
        boolean allValid = true;
        for (Map.Entry<TankSelector, PortTextField> entry : portTextFields.entrySet()) {
            if (!FormatChecker.isValidPort(entry.getValue().getText())) {
                allValid = false;
            }
        }
        btnSave.setDisable(!allValid);
    }

    /**
     * Sets PortTextFields to the value saved in settings.
     */
    private void resetValues() {
        int defaultPort = OSIPConstants.DEFAULT_PORT_MIX;
        for (Map.Entry<TankSelector, PortTextField> entry : portTextFields.entrySet()) {
            entry.getValue().setText("" + settings.getServerPort(entry.getKey(), defaultPort++));
        }
    }

    @Override
    public final int getPort(TankSelector tank) {
        return portTextFields.get(tank).getPort();
    }

    @Override
    public final void setSettingsChangedListener(EventHandler<ActionEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void show() {
        resetValues();
        settingsStage.show();
    }

    @Override
    public void close() {
        settingsStage.close();
    }
}
