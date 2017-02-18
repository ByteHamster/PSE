package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * This class has controls specific to the tanks in which several inputs are mixed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class MixTankTab extends AbstractTankTab {

    private int row;

    private Slider motorSlider;

    /**
     * Creates a new TankTab containg the standard controls of the AbstractTankTab as well as a
     * slider to control motor speed.
     * @param name The name of the AbstractTanktab
     * @param mixTank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public MixTankTab(String name, MixTank mixTank) {
        super(name, mixTank);

        row = getUsedRows();
        GridPane pane = getGridPane();

        createMotorSlider(pane, mixTank);

        this.setContent(this.getGridPane());
    }

    private void createMotorSlider(GridPane pane, MixTank tank) {
        Translator t = Translator.getInstance();

        motorSlider = new Slider(0,
                SimulationConstants.MAX_MOTOR_SPEED, tank.getMotor().getRPM());
        motorSlider.setShowTickLabels(true);
        motorSlider.setShowTickMarks(true);
        motorSlider.setMajorTickUnit(SimulationConstants.MAX_MOTOR_SPEED / 10);
        motorSlider.setMinorTickCount((SimulationConstants.MAX_MOTOR_SPEED / 10) - 1);
        motorSlider.setSnapToTicks(true);
        pane.add(motorSlider, 0, row);
        GridPane.setMargin(motorSlider, new Insets(10, 5, 10, 5));

        //Labels to show the current value and unit
        Label motorValue = new Label("" + tank.getMotor().getRPM());
        pane.add(motorValue, 1, row);
        GridPane.setMargin(motorValue, new Insets(10, 5, 10, 5));
        Label motorSign = new Label(t.getString("simulation.view.motorUnit"));
        pane.add(motorSign, 2, row);
        GridPane.setMargin(motorSign, new Insets(10, 5, 10, 5));

        //Listener to update the Label
        motorSlider.valueProperty().addListener((ov, oldVal, newVal)->
                motorValue.setText(String.format("%03d", newVal.intValue()))
        );
        row++;
    }

    /**
     * Gets the value of the motor rpm slider
     * 
     * @return The rpm of the motor in the mixtank
     */
    public final int getMotorRPM() {
        return (int) motorSlider.getValue();
    }
}
