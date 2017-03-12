package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.SimulationControlInterface;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import java.util.EnumMap;

/**
 * This is the main window for controlling the OSIP simulation. It provides scroll bars in
 * different tabs for all tanks to adjust their values, like outflow, temperature and motor speed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class SimulationControlWindow extends Stage implements SimulationControlInterface {

    private EnumMap<TankSelector, AbstractTankTab> tankTabs;
    private static final double MIN_WINDOW_HEIGHT = 250;
    private static final double MIN_WINDOW_WIDTH = 300;

    /**
     * Constructs a new SimulationControlWindow
     * @param productionSite The ProductionSite, to get all the tanks
     */
    public SimulationControlWindow(ProductionSite productionSite) {
        Translator t = Translator.getInstance();

        this.getIcons().add(new Image("/icon.png"));

        this.setTitle(t.getString("simulation.control.title"));
        this.tankTabs = new EnumMap<>(TankSelector.class);

        Scene scene = new Scene(makeLayout(productionSite));
        this.setScene(scene);
        setMinHeight(MIN_WINDOW_HEIGHT);
        setMinWidth(MIN_WINDOW_WIDTH);
    }

    private TabPane makeLayout(ProductionSite productionSite) {
        Translator t = Translator.getInstance();

        TabPane layout = new TabPane();
        layout.setStyle("-fx-font-size:" + ViewConstants.FONT_SIZE + "px;");

        for (TankSelector tankSelector : TankSelector.valuesWithoutMix()) {
            Tank tank = productionSite.getUpperTank(tankSelector);
            TankTab tab = new TankTab(t.getString(
                    TankSelector.TRANSLATOR_LABEL_PREFIX + tank.getTankSelector().name()), tank, productionSite);
            tankTabs.put(tankSelector, tab);
            layout.getTabs().add(tab);
        }

        TankSelector mixTankSelector = TankSelector.MIX;
        MixTank mixTank = productionSite.getMixTank();
        MixTankTab mtTab = new MixTankTab(t.getString(
                TankSelector.TRANSLATOR_LABEL_PREFIX + mixTank.getTankSelector().name()), mixTank);
        tankTabs.put(mixTankSelector, mtTab);
        layout.getTabs().add(mtTab);

        return layout;
    }

    @Override
    public void setControlsDisabled(boolean isDisable) {
        for (TankSelector t : TankSelector.values()) {
            AbstractTankTab tab = tankTabs.get(t);
            tab.setControlsDisabled(isDisable);
        }
    }

    /**
     * Sets the listener that is notified of changes to valve thresholds.
     * @param listener The Consumer that gets all changes to valve thresholds.
     */
    public void setValveListener(BiConsumer<Pipe, Byte> listener) {
        for (AbstractTankTab tab: tankTabs.values()) {
            tab.setValveListener(listener);
        }
    }

    /**
     * Sets the listener that is notified of changes in the temperature.
     * @param listener The Consumer that gets all changes to Tank temperatures
     */
    public void setTemperatureListener(BiConsumer<TankSelector, Float> listener) {
        for (TankSelector t : TankSelector.valuesWithoutMix()) {
            TankTab tab = (TankTab) tankTabs.get(t);
            tab.setTemperatureListener(listener);
        }
    }

    /**
     * Sets the listener that is notified of changes in motor speed.
     * @param listener The Consumer that gets all to the motorSpeed
     */
    public void setMotorListener(Consumer<Integer> listener) {
        MixTankTab tab = (MixTankTab) tankTabs.get(TankSelector.MIX);
        tab.setMotorListener(listener);
    }
}
