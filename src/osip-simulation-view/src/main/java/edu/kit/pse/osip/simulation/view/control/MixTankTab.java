package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.converter.IntegerStringConverter;

import java.util.Observable;
import java.util.function.Consumer;

/**
 * This class has controls specific to the tanks in which several inputs are mixed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class MixTankTab extends AbstractTankTab {

    private Slider motorSlider;
    private TextField motorValue;
    private MixTank mixTank;

    /**
     * Creates a new TankTab containg the standard controls of the AbstractTankTab as well as a
     * slider to control motor speed.
     * @param name The name of the AbstractTanktab
     * @param mixTank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public MixTankTab(String name, MixTank mixTank) {
        super(name, mixTank);
        this.mixTank = mixTank;

        GridPane pane = getGridPane();

        createOutFlowSlider(pane, mixTank);
        createMotorSlider(pane, mixTank);

        this.setContent(pane);

        mixTank.addObserver(this);
        mixTank.getMotor().addObserver(this);
        mixTank.getOutPipe().addObserver(this);
    }

    private void createMotorSlider(GridPane pane, MixTank tank) {
        Translator t = Translator.getInstance();
        int col = 0;

        Label motorLabel = new Label(t.getString("simulation.view.motorLabel"));
        pane.add(motorLabel, col++, row);
        GridPane.setMargin(motorLabel, ViewConstants.CONTROL_PADDING);

        motorSlider = new Slider(0,
                SimulationConstants.MAX_MOTOR_SPEED, tank.getMotor().getRPM());
        motorSlider.setShowTickLabels(true);
        motorSlider.setShowTickMarks(true);
        int majorTick = SimulationConstants.MAX_MOTOR_SPEED / ViewConstants.SLIDER_LABEL_COUNT;
        motorSlider.setMajorTickUnit(majorTick);
        motorSlider.setMinorTickCount(majorTick - 1);
        motorSlider.setSnapToTicks(true);
        motorSlider.setPrefWidth(ViewConstants.CONTROL_SLIDER_WIDTH);
        GridPane.setHgrow(motorSlider, Priority.ALWAYS);

        pane.add(motorSlider, col++, row);
        GridPane.setMargin(motorSlider, ViewConstants.CONTROL_PADDING);

        //TextField and Label to show the current value and unit
        motorValue = new TextField("" + tank.getMotor().getRPM());
        motorValue.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        motorValue.setMaxWidth(ViewConstants.CONTROL_INPUT_WIDTH);
        pane.add(motorValue, col++, row);
        GridPane.setMargin(motorValue, ViewConstants.CONTROL_PADDING);
        Label motorSign = new Label(t.getString("simulation.view.motorUnit"));
        pane.add(motorSign, col++, row);
        GridPane.setMargin(motorSign, ViewConstants.CONTROL_PADDING);


        StringProperty sp = motorValue.textProperty();
        DoubleProperty dp = motorSlider.valueProperty();
        Bindings.bindBidirectional(sp, dp,
            new ConfinedStringConverter(0d, (double) SimulationConstants.MAX_MOTOR_SPEED, sp));
        row++;
    }

    /**
     * Disables or enables all control elements in the MixTankTab to block or allow inputs.
     * @param isDisable true if inputs shall be blocked, false if they shall be enabled
     */
    public void setControlsDisabled(boolean isDisable) {
        super.setControlsDisabled(isDisable);
        if (motorSlider != null) {
            motorSlider.setDisable(isDisable);
        }
        if (motorValue != null) {
            motorValue.setDisable(isDisable);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!skipUpdates) {
            update(mixTank);
        }
    }

    /**
     * Updates the MixTankTab to show the values from the productionSite
     * @param tank The tank whose values are taken
     */
    private void update(MixTank tank) {
        super.update(tank);

        motorSlider.setValue(tank.getMotor().getRPM());
    }

    /**
     * Sets the listener that is notified of changes in motor speed.
     * @param listener The Consumer that gets all to the motorSpeed
     */
    protected void setMotorListener(Consumer<Integer> listener) {
        motorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!skipUpdates) {
                listener.accept(newValue.intValue());
            }
        });
    }
}
