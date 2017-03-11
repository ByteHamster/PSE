package edu.kit.pse.osip.simulation.view.dialogs;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * This window informs the user, that a tank has an overflow.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class OverflowDialog extends Stage {
    private TankSelector tank;
    private EventHandler<ActionEvent> resetButtonHandler;
    private static final double MIN_WINDOW_HEIGHT = 250;
    private static final double MIN_WINDOW_WIDTH = 500;

    /**
     * Constructor of OverflowDialog
     * @param tank Tank which has an overflow
     */
    public OverflowDialog(TankSelector tank) {
        this.setTank(tank);
    }

    /**
     * Set the tank which has an overflow
     * @param tank The tank which has an overflow
     */
    public final void setTank(TankSelector tank) {
        if (tank == null) {
            throw new NullPointerException("TankSelector parameter is null");
        }
        this.tank = tank;
        configure();
    }

    /**
     * Setter method for resetButtonHandler
     * @param eventHandler event handler for reset button
     */
    public void setResetButtonHandler(EventHandler<ActionEvent> eventHandler) {
        resetButtonHandler = eventHandler;
    }

    private Text getTextWithTankName(TankSelector tank, Font font) {
        Text text = new Text();
        if (tank != null) {
            String tankName = Translator.getInstance().getString(
                TankSelector.TRANSLATOR_LABEL_PREFIX + tank.name()).toLowerCase();
            text.setText(String.format(
                Translator.getInstance().getString("simulation.overflowdialog.overflowmessage"), tankName));
        } else {
            throw new IllegalStateException("No tank set. Please call setTank() before calling show()");
        }
        if (font != null) {
            text.setFont(font);
        }
        return text;
    }

    private void configure() {
        this.setTitle(Translator.getInstance().getString("simulation.overflowdialog.title"));
        Image imageIcon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        this.getIcons().add(imageIcon);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(ViewConstants.ELEMENTS_GAP));

        ImageView icon = new ImageView();
        icon.setImage(new Image("/overflow.png"));
        icon.setFitHeight(MIN_WINDOW_HEIGHT * 0.7);
        icon.setPreserveRatio(true);

        TextFlow overflowTextFlow = new TextFlow();
        Text overflowText = getTextWithTankName(tank,
                new Font(ViewConstants.FONT_SIZE * 2));
        overflowText.setFill(Color.valueOf("#c80000"));
        overflowTextFlow.getChildren().add(overflowText);
        overflowTextFlow.setPadding(new Insets(ViewConstants.FONT_SIZE * 2, 0, 0, 0));

        Button resetButton = new Button(Translator.getInstance().getString("simulation.overflowdialog.reset"));
        resetButton.setStyle("-fx-font-size: " + ViewConstants.FONT_SIZE + "px;");
        resetButton.setDefaultButton(true);
        resetButton.setOnAction(event -> {
            if (resetButtonHandler != null) {
                resetButtonHandler.handle(event);
            }
            this.close();
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        borderPane.setBottom(resetButton);
        borderPane.setPadding(new Insets(0, 0, ViewConstants.ELEMENTS_GAP, 0));
        BorderPane.setAlignment(resetButton, Pos.CENTER);

        grid.add(icon, 0, 0);
        grid.add(overflowTextFlow, 1, 0);
        Scene scene = new Scene(borderPane, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        this.setScene(scene);
        this.setMinWidth(MIN_WINDOW_WIDTH);
        this.setMinHeight(MIN_WINDOW_HEIGHT);
    }
}
