package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Observable;

/**
 * This class has controls specific to the input tanks.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class TankTab extends AbstractTankTab {

    private Slider inFlowSlider;
    private TextField inFlowValue;

    private Slider temperatureSlider;
    private TextField temperatureValue;

    private TankSelector selector;
    private ProductionSite site;

    /**
     * Creates a new TankTab containing the standard controls of the AbstractTankTab as well as a
     * slider to control inFlow.
     * @param name The name of the AbstractTankTab
     * @param tank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public TankTab(String name, Tank tank, ProductionSite site) {
        super(name, tank);

        this.selector = tank.getTankSelector();
        this.site = site;

        GridPane pane = getGridPane();

        createInFlowSlider(pane, tank);
        createTemperatureSlider(pane, tank);

        this.setContent(pane);
    }

    private void createInFlowSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();
        int col = 0;

        Label inFlowLabel = new Label(t.getString("simulation.view.inFlowLabel"));
        pane.add(inFlowLabel, col++, row);
        GridPane.setMargin(inFlowLabel, ViewConstants.CONTROL_PADDING);

        inFlowSlider = new Slider(0, 100, tank.getOutPipe().getValveThreshold());
        inFlowSlider.setShowTickLabels(true);
        inFlowSlider.setShowTickMarks(true);
        int majorTick = 100 / ViewConstants.SLIDER_LABEL_COUNT;
        inFlowSlider.setMajorTickUnit(majorTick);
        inFlowSlider.setMinorTickCount(majorTick - 1);
        inFlowSlider.setSnapToTicks(true);
        inFlowSlider.setPrefWidth(ViewConstants.CONTROL_SLIDER_WIDTH);
        GridPane.setHgrow(inFlowSlider, Priority.ALWAYS);

        inFlowSlider.valueProperty().addListener((ov, oldVal, newVal) ->
                inFlowSlider.setValue(newVal.intValue()));

        pane.add(inFlowSlider, col++, row);
        GridPane.setMargin(inFlowSlider, ViewConstants.CONTROL_PADDING);

        //TextField and label to show the current value and unit
        inFlowValue = new TextField("" + tank.getOutPipe().getValveThreshold());
        inFlowValue.setMaxWidth(ViewConstants.CONTROL_INPUT_WIDTH);
        pane.add(inFlowValue, col++, row);
        GridPane.setMargin(inFlowValue, ViewConstants.CONTROL_PADDING);
        Label inFlowSign = new Label(t.getString("simulation.view.flowUnit"));
        pane.add(inFlowSign, col++, row);
        GridPane.setMargin(inFlowSign, ViewConstants.CONTROL_PADDING);

        StringProperty sp = inFlowValue.textProperty();
        DoubleProperty dp = inFlowSlider.valueProperty();
        Bindings.bindBidirectional(sp, dp, new ConfinedStringConverter(0d, 100d, sp));
        row++;
    }

    private void createTemperatureSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();
        int col = 0;

        Label temperatureLabel = new Label(t.getString("simulation.view.temperatureLabel"));
        pane.add(temperatureLabel, col++, row);
        GridPane.setMargin(temperatureLabel, ViewConstants.CONTROL_PADDING);

        // Otherwise the scale might end at -0.0
        float min = SimulationConstants.MIN_TEMPERATURE - SimulationConstants.CELCIUS_OFFSET;
        if (min == -0.0) {
            min = 0.0f;
        }

        temperatureSlider = new Slider(min,
                SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELCIUS_OFFSET,
                tank.getLiquid().getTemperature() - SimulationConstants.CELCIUS_OFFSET);
        temperatureSlider.setShowTickLabels(true);
        temperatureSlider.setShowTickMarks(true);
        int majorTick = (int) (SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELCIUS_OFFSET)
                / ViewConstants.SLIDER_LABEL_COUNT;
        temperatureSlider.setMajorTickUnit(majorTick);
        temperatureSlider.setMinorTickCount(majorTick - 1);
        temperatureSlider.setPrefWidth(ViewConstants.CONTROL_SLIDER_WIDTH);
        GridPane.setHgrow(temperatureSlider, Priority.ALWAYS);
        pane.add(temperatureSlider, col++, row);
        GridPane.setMargin(temperatureSlider, ViewConstants.CONTROL_PADDING);

        //Labels to show the current value and unit
        temperatureValue = new TextField("" + tank.getLiquid().getTemperature());
        temperatureValue.setMaxWidth(ViewConstants.CONTROL_INPUT_WIDTH);
        pane.add(temperatureValue, col++, row);
        GridPane.setMargin(temperatureValue, ViewConstants.CONTROL_PADDING);
        Label temperatureSign = new Label(t.getString("simulation.view.temperatureUnit"));
        pane.add(temperatureSign, col++, row);
        GridPane.setMargin(temperatureSign, ViewConstants.CONTROL_PADDING);

        StringProperty sp = temperatureValue.textProperty();
        DoubleProperty dp = temperatureSlider.valueProperty();
        Bindings.bindBidirectional(sp, dp, new ConfinedStringConverter((double) min,
            (double) SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELCIUS_OFFSET, sp));
        row++;
    }

    /**
     * Disables or enables all control elements in the TankTab to block or allow inputs.
     * @param isDisable true if inputs shall be blocked, false if they shall be enabled
     */
    public void setControlsDisabled(boolean isDisable) {
        if (inFlowSlider != null) {
            inFlowSlider.setDisable(isDisable);
        }
        if (inFlowValue != null) {
            inFlowValue.setDisable(isDisable);
        }
        if (temperatureSlider != null) {
            temperatureSlider.setDisable(isDisable);
        }
        if (temperatureValue != null) {
            temperatureValue.setDisable(isDisable);
        }
        super.setControlsDisabled(isDisable);
    }

    /**
     * Returns a reference to the InFlowSlider
     * @return A reference to the InFlowSlider
     */
    protected Slider getInFlowSlider() {
        return inFlowSlider;
    }

    /**
     * Gets a reference to the temperatureSlider
     * @return A reference to the temperatureSlider
     */
    protected Slider getTemperatureSlider() {
        return temperatureSlider;
    }

    @Override
    public void update(Observable observable, Object o) {
        super.update(observable, o);

        Tank tank = (Tank) observable;

        inFlowSlider.setValue(tank.getInPipe().getValveThreshold());
        inFlowValue.setText(String.valueOf(tank.getInPipe().getValveThreshold()));

        temperatureSlider.setValue(site.getInputTemperature(selector));
        temperatureValue.setText(String.valueOf(site.getInputTemperature(selector)));
    }

}
