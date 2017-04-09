package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.base.MixTank;

/**
 * This is a ProductionSite which returns the simulated tanks of the package "simulation"
 * instead of the non-simulated tanks of package "base".
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ProductionSiteSimulation extends ProductionSite {
    /**
     * Creates a production site that allows simulating the contained tanks.
     */
    public ProductionSiteSimulation() {
        super();
    }

    /**
     * Template method to allow subclasses to create objects of subclasses of Tank. The parameters are the same
     * parameters as in the Tank constructor.
     * 
     * @param capacity @see Tank
     * @param tankSelector @see Tank
     * @param liquid @see Tank
     * @param outPipe @see Tank
     * @param inPipe @see Tank
     * @return The created Tank
     */
    @Override
    protected Tank instantiateTank(float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe,
                                   Pipe inPipe) {
        return new TankSimulation(capacity, tankSelector, liquid, outPipe, inPipe);
    }

    /**
     * Template method to allow subclasses to create objects of subclasses of MixTank. The parameters are the same
     * parameters as in the MixTank constructor.
     * 
     * @param capacity @see MixTank
     * @param liquid @see MixTank
     * @param outPipe @see MixTank
     * @return The creates MixTank
     */
    @Override
    protected MixTank instantiateMixTank(float capacity, Liquid liquid, Pipe outPipe) {
        return new MixTankSimulation(capacity, liquid, outPipe);
    }

    /**
     * Gets a tank which is extended to be simulatable.
     * 
     * @return the requested tank that is simulatable.
     * @param tank Specifies the tank.
     */
    @Override
    public TankSimulation getUpperTank(TankSelector tank) {
        return (TankSimulation) super.getUpperTank(tank);
    }

    /**
     * Get the mixtank which is extended to be simulatable.
     * 
     * @return the requested mixtank that is simulatable.
     */
    @Override
    public MixTankSimulation getMixTank() {
        return (MixTankSimulation) super.getMixTank();
    }
}
