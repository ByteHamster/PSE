package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.SimulationControlInterface;
import edu.kit.pse.osip.simulation.view.main.ViewConstants;
import java.util.EnumMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

/**
 * This is the main window for controlling the OSIP simulation. It provides sliders in
 * different tabs for all tanks to adjust their values, like outflow, temperature and motor speed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class SimulationControlWindow extends Stage implements SimulationControlInterface {
    /**
     * Saves the tabs for the tanks.
     */
    private EnumMap<TankSelector, AbstractTankTab> tankTabs;
    /**
     * The minimum width of the tank.
     */
    private static final double MIN_WINDOW_WIDTH = 300;   
    /**
     * The minimum height of the window.
     */
    private static final double MIN_WINDOW_HEIGHT = 250;

    /**
     * Constructs a new SimulationControlWindow.
     * 
     * @param productionSite The ProductionSite to get all the tanks.
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

    /**
     * Makes the layout for the window.
     * 
     * @param productionSite the current used ProductionSite.
     * @return the layout.
     */
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

    @Override
    public void setValveListener(BiConsumer<Pipe, Byte> listener) {
        for (AbstractTankTab tab: tankTabs.values()) {
            tab.setValveListener(listener);
        }
    }

    @Override
    public void setTemperatureListener(BiConsumer<TankSelector, Float> listener) {
        for (TankSelector t : TankSelector.valuesWithoutMix()) {
            TankTab tab = (TankTab) tankTabs.get(t);
            tab.setTemperatureListener(listener);
        }
    }

    @Override
    public void setMotorListener(Consumer<Integer> listener) {
        MixTankTab tab = (MixTankTab) tankTabs.get(TankSelector.MIX);
        tab.setMotorListener(listener);
    }
}
