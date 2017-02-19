package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * This class has controls specific to the input tanks.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class TankTab extends AbstractTankTab {

    private Slider inFlowSlider;

    private int row;

    /**
     * Creates a new TankTab containg the standard controls of the AbstractTankTab as well as a
     * slider to control inFlow.
     * @param name The name of the AbstractTanktab
     * @param tank The AbstractTank which is controlled through the AbstractTankTab.
     */
    public TankTab(String name, Tank tank) {
        super(name, tank);
        row = getUsedRows();

        GridPane pane = getGridPane();

        createInFlowSlider(pane, tank);

        this.setContent(pane);
    }

    private void createInFlowSlider(GridPane pane, AbstractTank tank) {
        Translator t = Translator.getInstance();

        inFlowSlider = new Slider(0, 100, tank.getOutPipe().getValveThreshold());
        inFlowSlider.setShowTickLabels(true);
        inFlowSlider.setShowTickMarks(true);
        inFlowSlider.setMajorTickUnit(10);
        inFlowSlider.setMinorTickCount(9);
        inFlowSlider.setSnapToTicks(true);
        pane.add(inFlowSlider, 0, row);
        GridPane.setMargin(inFlowSlider, new Insets(10, 5, 10, 5));

        //Labels to show the current value and unit
        Label inFlowValue = new Label("" + tank.getOutPipe().getValveThreshold());
        pane.add(inFlowValue, 1, row);
        GridPane.setMargin(inFlowValue, new Insets(10, 5, 10, 5));
        Label inFlowSign = new Label(t.getString("simulation.view.flowUnit"));
        pane.add(inFlowSign, 2, row);
        GridPane.setMargin(inFlowSign, new Insets(10, 5, 10, 5));

        //Listener to update the Label
        inFlowSlider.valueProperty().addListener((ov, oldVal, newVal) ->
                inFlowValue.setText(String.format("%02d", newVal.intValue()))
        );
        row++;
    }

    /**
     * Returns a reference to the InFlowSlider
     * @return A reference to the InFlowSlider
     */
    protected Slider getInFlowSlider() {
        return inFlowSlider;
    }
}
