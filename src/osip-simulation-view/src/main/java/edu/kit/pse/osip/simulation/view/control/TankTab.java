package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import java.util.Observable;
import java.util.function.BiConsumer;
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

/**
 * This class has controls specific to the input tanks.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class TankTab extends AbstractTankTab {
    /**
     * Slider for setting the threshold of the incoming pipe.
     */
    private Slider inFlowSlider;
    /**
     * Shows the threshold of the incoming pipe as text.
     */
    private TextField inFlowValue;

    /**
     * Slider for setting the temperature.
     */
    private Slider temperatureSlider;
    /**
     * Shows the temperature as text.
     */
    private TextField temperatureValue;

    /**
     * Saves the TankSelector this tab belongs to.
     */
    private TankSelector selector;
    /**
     * The current ProductionSite.
     */
    private ProductionSite site;

    /**
     * Creates a new TankTab containing the standard controls of the AbstractTankTab as well as a
     * slider to control inFlow.
     * 
     * @param name The name of the AbstractTankTab.
     * @param tank The AbstractTank which is controlled through the AbstractTankTab.
     * @param site The ProductionSite in which tank resides.
     */
    public TankTab(String name, Tank tank, ProductionSite site) {
        super(name, tank);

        this.selector = tank.getTankSelector();
        this.site = site;

        GridPane pane = getGridPane();

        createInFlowSlider(pane, tank);
        createOutFlowSlider(pane, tank);
        createTemperatureSlider(pane, tank);

        this.setContent(pane);

        site.addObserver(this);
        tank.addObserver(this);
        tank.getInPipe().addObserver(this);
        tank.getOutPipe().addObserver(this);
    }

    /**
     * Creates the controls for the incoming pipe.
     * 
     * @param pane the layout for the tab.
     * @param tank the tank with the incoming pipe.
     */
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

        pane.add(inFlowSlider, col++, row);
        GridPane.setMargin(inFlowSlider, ViewConstants.CONTROL_PADDING);

        //TextField and label to show the current value and unit
        inFlowValue = new TextField("" + tank.getOutPipe().getValveThreshold());
        inFlowValue.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
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

    /**
     * Creates all controls for the temperature.
     * 
     * @param pane the layout of the tab.
     * @param tank the tank with the temperature.
     */
    private void createTemperatureSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();
        int col = 0;

        Label temperatureLabel = new Label(t.getString("simulation.view.temperatureLabel"));
        pane.add(temperatureLabel, col++, row);
        GridPane.setMargin(temperatureLabel, ViewConstants.CONTROL_PADDING);

        // Otherwise the scale might end at -0.0
        float min = SimulationConstants.MIN_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET;
        if (min == -0.0) {
            min = 0.0f;
        }

        temperatureSlider = new Slider(min,
                SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET,
                tank.getLiquid().getTemperature() - SimulationConstants.CELSIUS_OFFSET);
        temperatureSlider.setShowTickLabels(true);
        temperatureSlider.setShowTickMarks(true);
        int majorTick = (int) (SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET)
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
            (double) SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET, sp));
        row++;
    }

    @Override
    public void setControlsDisabled(boolean isDisable) {
        super.setControlsDisabled(isDisable);
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
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!skipUpdates) {
            update(site.getUpperTank(selector));
        }
    }

    /**
     * Updates the TankTab to show the values from the productionSite.
     * 
     * @param tank The tank whose values are taken.
     */
    private void update(Tank tank) {
        super.update(tank);

        inFlowSlider.setValue(tank.getInPipe().getValveThreshold());
        temperatureSlider.setValue(site.getInputTemperature(selector) - SimulationConstants.CELSIUS_OFFSET);
    }

    @Override
    protected void setValveListener(BiConsumer<Pipe, Byte> listener) {
        super.setValveListener(listener);

        inFlowSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            skipUpdates = true;
            listener.accept(site.getUpperTank(selector).getInPipe(), newValue.byteValue());
            skipUpdates = false;
        });
    }

    /**
     * Sets the listener that is notified of changes in the temperature.
     * 
     * @param listener The Consumer that gets all changes to Tank temperatures.
     */
    protected void setTemperatureListener(BiConsumer<TankSelector, Float> listener) {
        temperatureSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            skipUpdates = true;
            listener.accept(selector, newValue.floatValue() + SimulationConstants.CELSIUS_OFFSET);
            skipUpdates = false;
        });
    }
}
