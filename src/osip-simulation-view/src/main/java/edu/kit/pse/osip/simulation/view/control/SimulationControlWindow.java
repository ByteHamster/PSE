package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
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
 * @version 0.1
 * @author Niko Wilhelm
 */
public class SimulationControlWindow extends Stage implements SimulationControlInterface {

    private EnumMap<TankSelector, AbstractTankTab> tankTabs;

    /**
     * Constructs a new SimulationControlWindow
     * @param productionSite The ProductionSite, to get all the tanks
     */
    public SimulationControlWindow(ProductionSite productionSite) {
        Translator t = Translator.getInstance();

        this.setTitle(t.getString("simulation.control.title"));
        this.setResizable(false);
        this.tankTabs = new EnumMap<>(TankSelector.class);

        Scene scene = new Scene(makeLayout(productionSite));
        this.setScene(scene);
    }

    private TabPane makeLayout(ProductionSite productionSite) {
        TabPane layout = new TabPane();

        for (TankSelector tankSelector : TankSelector.valuesWithoutMix()) {
            Tank tank = productionSite.getUpperTank(tankSelector);
            TankTab tab = new TankTab(tankSelector.name(), tank);
            tankTabs.put(tankSelector, tab);
            layout.getTabs().add(tab);
        }

        // TODO: is there another way to get TankSelector.MIX separately?
        TankSelector mixTankSelector = TankSelector.MIX;
        MixTank mixTank = productionSite.getMixTank();
        MixTankTab mtTab = new MixTankTab(mixTankSelector.name(), mixTank);
        tankTabs.put(mixTankSelector, mtTab);
        layout.getTabs().add(mtTab);

        return layout;
    }

    /**
     * Sets the listener that is notified of changes to valve thresholds.
     * @param listener The Consumer that gets all changes to valve thresholds.
     */
    public void setValveListener(BiConsumer<Pipe, Byte> listener) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the listener that is notified of changes in the temperature.
     * @param listener The Consumer that gets all changes to Tank temperatures
     */
    public void setTemperatureListener(BiConsumer<TankSelector, Float> listener) {
        throw new RuntimeException("Not implemented");
    }

    public void setMotorListener(Consumer<Integer> listener) {
        throw new RuntimeException("Not implemented");
    }
}
