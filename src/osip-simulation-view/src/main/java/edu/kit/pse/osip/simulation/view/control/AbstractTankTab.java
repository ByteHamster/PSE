package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

/**
 * This class contains the controls for a single tank in the simulation.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public abstract class AbstractTankTab extends Tab {

    private int row;

    private GridPane pane;

    private Slider outFlowSlider;

    private Slider temperatureSlider;

    /**
     * Creates a new AbstractTankTab with Sliders to control outFlow and Temperature
     * @param name The name of the AbstractTanktab
     * @param tank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public AbstractTankTab(String name, AbstractTank tank) {
        super(name);
        this.setClosable(false);

        pane = new GridPane();
        row = 0;

        createTemperatureSlider(pane, tank);
        createOutFlowSlider(pane, tank);
    }

    private void createTemperatureSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();

        temperatureSlider = new Slider(SimulationConstants.MIN_TEMPERATURE,
                SimulationConstants.MAX_TEMPERATURE, tank.getLiquid().getTemperature());
        temperatureSlider.setShowTickLabels(true);
        temperatureSlider.setShowTickMarks(true);
        temperatureSlider.setMajorTickUnit(10);
        temperatureSlider.setMinorTickCount(9);
        temperatureSlider.setSnapToTicks(true);
        pane.add(temperatureSlider, 0, row);
        GridPane.setMargin(temperatureSlider, new Insets(10, 5, 10, 5));

        //Labels to show the current value and unit
        Label temperatureValue = new Label("" + tank.getLiquid().getTemperature());
        pane.add(temperatureValue, 1, row);
        GridPane.setMargin(temperatureValue, new Insets(10, 5, 10, 5));
        Label temperatureSign = new Label(t.getString("simulation.view.temperatureUnit"));
        pane.add(temperatureSign, 2, row);
        GridPane.setMargin(temperatureSign, new Insets(10, 5, 10, 5));

        //Listener to update the Label
        temperatureSlider.valueProperty().addListener((ov, oldVal, newVal)->
                temperatureValue.setText(String.format("%03d", newVal.intValue()))
        );
        row++;
    }

    private void createOutFlowSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();

        outFlowSlider = new Slider(0, 100, tank.getOutPipe().getValveThreshold());
        outFlowSlider.setShowTickLabels(true);
        outFlowSlider.setShowTickMarks(true);
        outFlowSlider.setMajorTickUnit(10);
        outFlowSlider.setMinorTickCount(9);
        outFlowSlider.setSnapToTicks(true);
        pane.add(outFlowSlider, 0, row);
        GridPane.setMargin(outFlowSlider, new Insets(10, 5, 10, 5));

        //Labels to show the current value and unit
        Label outFlowValue = new Label("" + tank.getOutPipe().getValveThreshold());
        pane.add(outFlowValue, 1, row);
        GridPane.setMargin(outFlowValue, new Insets(10, 5, 10, 5));
        Label outFlowSign = new Label(t.getString("simulation.view.flowUnit"));
        pane.add(outFlowSign, 2, row);
        GridPane.setMargin(outFlowSign, new Insets(10, 5, 10, 5));

        //Listener to update the Label
        outFlowSlider.valueProperty().addListener((ov, oldVal, newVal) ->
                outFlowValue.setText(String.format("%02d", newVal.intValue()))
        );
        row++;
    }

    /**
     * Gets a reference to the outFlowSlider
     * @return A reference to the outFlowSlider
     */
    protected Slider getOutFlowSlider() {
        return outFlowSlider;
    }

    /**
     * Gets a reference to the temperatureSlider
     * @return A reference to the temperatureSlider
     */
    protected Slider getTemperatureSlider() {
        return temperatureSlider;
    }

    /**
     * Get the number of rows that have already been filled in the GridPane
     * @return The value of rows.
     */
    protected int getUsedRows() {
        return row;
    }

    /**
     * Gets the GridPane on which the sliders are ordered
     * @return The GridPane in the tab
     */
    protected GridPane getGridPane() {
        return pane;
    }
}
