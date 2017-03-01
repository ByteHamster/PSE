package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Observable;
import java.util.Observer;

/**
 * This class contains the controls for a single tank in the simulation.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public abstract class AbstractTankTab extends Tab implements Observer {

    protected int row;

    private GridPane pane;

    private Slider outFlowSlider;
    private TextField outFlowValue;

    /**
     * Creates a new AbstractTankTab with Sliders to control outFlow and Temperature
     * @param name The name of the AbstractTankTab
     * @param tank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public AbstractTankTab(String name, AbstractTank tank) {
        super(name);
        this.setClosable(false);

        pane = new GridPane();
        pane.setPrefWidth(ViewConstants.CONTROL_WIDTH);
        row = 0;

        tank.addObserver(this);
        createOutFlowSlider(pane, tank);
    }

    private void createOutFlowSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();
        int col = 0;

        Label outFlowLabel = new Label(t.getString("simulation.view.outFlowLabel"));
        pane.add(outFlowLabel, col++, row);
        GridPane.setMargin(outFlowLabel, ViewConstants.CONTROL_PADDING);

        outFlowSlider = new Slider(0, 100, tank.getOutPipe().getValveThreshold());
        outFlowSlider.setShowTickLabels(true);
        outFlowSlider.setShowTickMarks(true);
        int majorTick = 100 / ViewConstants.SLIDER_LABEL_COUNT;
        outFlowSlider.setMajorTickUnit(majorTick);
        outFlowSlider.setMinorTickCount(majorTick - 1);
        outFlowSlider.setSnapToTicks(true);
        outFlowSlider.setPrefWidth(ViewConstants.CONTROL_SLIDER_WIDTH);
        GridPane.setHgrow(outFlowSlider, Priority.ALWAYS);

        outFlowSlider.valueProperty().addListener((ov, oldVal, newVal) ->
            outFlowSlider.setValue(newVal.intValue()));

        pane.add(outFlowSlider, col++, row);
        GridPane.setMargin(outFlowSlider, ViewConstants.CONTROL_PADDING);

        //TextField and Label to show the current value and unit
        outFlowValue = new TextField("" + tank.getOutPipe().getValveThreshold());
        outFlowValue.setMaxWidth(ViewConstants.CONTROL_INPUT_WIDTH);
        pane.add(outFlowValue, col++, row);
        GridPane.setMargin(outFlowValue, ViewConstants.CONTROL_PADDING);
        Label outFlowSign = new Label(t.getString("simulation.view.flowUnit"));
        pane.add(outFlowSign, col++, row);
        GridPane.setMargin(outFlowSign, ViewConstants.CONTROL_PADDING);

        StringProperty sp = outFlowValue.textProperty();
        DoubleProperty dp = outFlowSlider.valueProperty();
        Bindings.bindBidirectional(sp, dp, new ConfinedStringConverter(0d, 100d, sp));

        row++;
    }

    /**
     * Disables or enables all control elements in the AbstractTankTab to block or allow inputs.
     * @param isDisable true if inputs shall be blocked, false if they shall be enabled
     */
    protected void setControlsDisabled(boolean isDisable) {
        if (outFlowSlider != null) {
            outFlowSlider.setDisable(isDisable);
        }
        if (outFlowValue != null) {
            outFlowValue.setDisable(isDisable);
        }
    }

    /**
     * Gets a reference to the outFlowSlider
     * @return A reference to the outFlowSlider
     */
    protected Slider getOutFlowSlider() {
        return outFlowSlider;
    }

    /**
     * Gets the GridPane on which the sliders are ordered
     * @return The GridPane in the tab
     */
    protected GridPane getGridPane() {
        return pane;
    }

    public void update(Observable observable, Object o) {
        AbstractTank tank = (AbstractTank) observable;
        outFlowSlider.setValue(tank.getOutPipe().getValveThreshold());
        outFlowValue.setText(String.valueOf(tank.getOutPipe().getValveThreshold()));
    }
}
