package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import java.util.EnumMap;
import java.util.Observable;

/**
 * Groups all tanks in the production site together. This is the entrance point of the model because you can get every
 * tank and, through the tanks, every pipe in the production site.
 * 
 * @author David Kahles
 * @version 1.0
 */
public class ProductionSite extends Observable {
    /**
     * Stores the mixtank.
     */
    private MixTank mixTank;
    /**
     * Stores all tanks.
     */
    private final EnumMap<TankSelector, Tank> tanks = new EnumMap<>(TankSelector.class);
    /**
     * Saves the input temperatures of all tanks.
     */
    private final EnumMap<TankSelector, Float> inputTemperature = new EnumMap<>(TankSelector.class);

    /**
     * Constructs a new ProductionSite.
     */
    public ProductionSite() {
        initTanks();
    }
    
    /**
     * Initializes all tanks.
     */
    private void initTanks() {
        int halfFull = SimulationConstants.TANK_SIZE / 2;

        mixTank = instantiateMixTank(SimulationConstants.TANK_SIZE, new Liquid(halfFull,
                TankSelector.MIX.getInitialTemperature(), TankSelector.MIX.getInitialColor()),
                new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, (byte) 100));

        byte count = 0;
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            byte threshold;
            if (count == 0 || count == 1) {
                threshold = (byte) 50;
            } else {
                threshold = (byte) 0;
            }
            count++;
            Liquid l = new Liquid(halfFull, selector.getInitialTemperature(), selector.getInitialColor());
            Pipe inPipe = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, threshold);
            Pipe outPipe = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, threshold);

            tanks.put(selector, instantiateTank(SimulationConstants.TANK_SIZE, selector, l, outPipe, inPipe));
        }

        /* Make sure we're in the correct state */
        reset();
    }
    
    /**
     * Template method to allow subclasses to create objects of subclasses of Tank. The parameters are the same
     * parameters as in the Tank constructor.
     * 
     * @param capacity see Tank.
     * @param tankSelector see Tank.
     * @param liquid see Tank.
     * @param outPipe see Tank.
     * @param inPipe see Tank.
     * @return The created Tank.
     */
    protected Tank instantiateTank(float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe,
        Pipe inPipe) {
        return new Tank(capacity, tankSelector, liquid, outPipe, inPipe);
    }

    /**
     * Template method to allow subclasses to create objects of subclasses of MixTank. The parameters are the same
     * parameters as in the MixTank constructor.
     * 
     * @param capacity see MixTank.
     * @param liquid see MixTank.
     * @param outPipe see MixTank.
     * @return The created MixTank.
     */
    protected MixTank instantiateMixTank(float capacity, Liquid liquid, Pipe outPipe) {
        return new MixTank(capacity, liquid, outPipe);
    }

    /**
     * Gets the input temperature of an upper tank.
     * 
     * @param tank Specifies the tank.
     * @return The input temperature.
     */
    public float getInputTemperature(TankSelector tank) {
        return inputTemperature.get(tank);
    }

    /**
      * Sets the input temperature for an upper tank.
      * 
      * @param tank Specifies the tank.
      * @param temperature Temperature to set.
      */
    public void setInputTemperature(TankSelector tank, float temperature) {
        if (temperature > SimulationConstants.MAX_TEMPERATURE) {
            throw new IllegalArgumentException("Tank input temperature must not be grater than "
                + SimulationConstants.MAX_TEMPERATURE);
        }
        if (temperature < SimulationConstants.MIN_TEMPERATURE) {
            throw new IllegalArgumentException("Tank input temperature must not be smaller than "
                    + SimulationConstants.MIN_TEMPERATURE);
        }

        inputTemperature.put(tank, temperature);
        setChanged();
        notifyObservers();
    }

    /**
     * Gets one of the upper tanks.
     * 
     * @param tank Specifies the tank.
     * @return the requested tank.
     */
    public Tank getUpperTank(TankSelector tank) {
        return tanks.get(tank);
    }

    /**
     * Gets the mixtank.
     * 
     * @return the mixtank of the production site.
     */
    public MixTank getMixTank() {
        return mixTank;
    }

    /**
     * Resets the whole production site to its default values: Every tank with 50% infill, valves putting the site to
     * a stable state.
     */
    public synchronized void reset() {
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            tanks.get(selector).reset();
            inputTemperature.put(selector, selector.getInitialTemperature());
        }
        mixTank.reset();
        inputTemperature.put(TankSelector.MIX, TankSelector.MIX.getInitialTemperature());
        setChanged();
        notifyObservers();

    }
}
