package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.TankSelector;
import java.util.LinkedList;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the ProductionSiteSimulation.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ProductionSiteSimulationTest {
    /**
     * It must be possible to call putIn() on all tanks.
     */
    @Test
    public void testObjectTypes() {
        ProductionSiteSimulation prodSite = new ProductionSiteSimulation();
        Liquid liquid = new Liquid(0, 300, new Color(0));
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(liquid);


        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertNotNull(prodSite.getUpperTank(selector));
            prodSite.getUpperTank(selector).putIn(liquid);
        }
        prodSite.getMixTank().putIn(liquids);

        prodSite.reset();

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertNotNull(prodSite.getUpperTank(selector));
            prodSite.getUpperTank(selector).putIn(liquid);
        }
        prodSite.getMixTank().putIn(liquids);
    }
}
