package edu.kit.pse.osip.simulation.view.settings;

import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.formatting.InvalidPortException;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.SettingsChangedListener;
import edu.kit.pse.osip.simulation.controller.SimulationSettingsInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the main window of the OSIP simulation settings.
 */
public class SimulationSettingsWindow implements SimulationSettingsInterface {
    private ServerSettingsWrapper settings;
    private Stage settingsStage;
    private Map<TankSelector, PortTextField> portTextFields = new HashMap<>();
    private SettingsChangedListener listener;

    /**
     * Generates a new window that shows the simulation settings
     * @param settings Wrapper for saved settings
     */
    public SimulationSettingsWindow(ServerSettingsWrapper settings) {
        this.settings = settings;

        settingsStage = new Stage();
        Scene scene = new Scene(createRootLayout());
        settingsStage.setResizable(false);
        settingsStage.setScene(scene);
        settingsStage.setTitle(Translator.getInstance().getString("simulation.settings.title"));

        resetValues();
    }

    /**
     * Creates the settings main layout
     * @return The layout
     */
    private VBox createRootLayout() {
        Translator t = Translator.getInstance();

        HBox portsRow = new HBox(10);
        portsRow.setAlignment(Pos.CENTER);

        for (TankSelector tank : TankSelector.values()) {
            Label portLabel = new Label(t.getString("simulation.settings.port." + tank.name()));
            PortTextField portText = new PortTextField();
            portTextFields.put(tank, portText);
            portsRow.getChildren().add(portLabel);
            portsRow.getChildren().add(portText);
        }

        Label portHeading = new Label(t.getString("simulation.settings.port"));

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);

        Button btnSave = new Button(t.getString("simulation.settings.save"));
        btnSave.setDefaultButton(true);
        btnSave.setOnAction(e -> {
            if (listener != null) {
                listener.onSettingsChanged();
            } else {
                System.err.println("No SettingsChangedListener set");
            }
        });

        Button btnCancel = new Button(t.getString("simulation.settings.cancel"));
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(e -> {
            resetValues();
            settingsStage.close();
        });

        buttons.getChildren().add(btnSave);
        buttons.getChildren().add(btnCancel);

        VBox rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(10));
        rootLayout.getChildren().add(portHeading);
        rootLayout.getChildren().add(portsRow);
        rootLayout.getChildren().add(buttons);
        return rootLayout;
    }

    /**
     * Sets PortTextFields to the value saved in settings
     */
    private void resetValues() {
        int defaultPort = 12868;
        for (Map.Entry<TankSelector, PortTextField> entry : portTextFields.entrySet()) {
            entry.getValue().setText("" + settings.getServerPort(entry.getKey(), defaultPort++));
        }
    }

    /**
     * Gets the port number in PortTextField id.
     * @throws InvalidPortException Thrown if the current value in port is not valid
     * (see FormatChecker.parsePort(String port).
     * @return The port number in PortTextField id.
     * @param tank The tankto get the port for
     */
    public final int getPort(TankSelector tank) {
        return portTextFields.get(tank).getPort();
    }

    /**
     * Sets the listener that gets notified as soon as the settings change
     * @param listener The listener to be called when the settings are changed
     */
    public final void setSettingsChangedListener(SettingsChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void show() {
        settingsStage.show();
    }

    @Override
    public void close() {
        settingsStage.close();
    }
}
