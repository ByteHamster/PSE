package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import org.junit.Before;
import org.junit.Test;
import java.util.Observable;
import java.util.Observer;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the ProductionSite class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class ProductionSiteTest implements Observer {
    /**
     * ProductionSite used for testing.
     */
    private ProductionSite prodSite;
    /**
     * Indicates if the observer works.
     */
    private boolean updated;

    /**
     * Initializes prodSite.
     * */
    @Before
    public void init() {
        prodSite = new ProductionSite();
        updated = false;
    }

    /**
     * Checks that all tanks are not null.
     */
    @Test
    public void notNull() {
        assertNotNull(prodSite.getMixTank());
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertNotNull(prodSite.getUpperTank(selector));
        }

        modifyEverything();
        prodSite.reset();

        assertNotNull(prodSite.getMixTank());
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertNotNull(prodSite.getUpperTank(selector));
        }

    }

    /**
     * Checks that all tanks have the correct TankSelector assigned.
     */
    @Test
    public void correctSelector() {
        assertEquals(TankSelector.MIX, prodSite.getMixTank().getTankSelector());
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertEquals(selector, prodSite.getUpperTank(selector).getTankSelector());
        }

        modifyEverything();
        prodSite.reset();

        assertEquals(TankSelector.MIX, prodSite.getMixTank().getTankSelector());
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertEquals(selector, prodSite.getUpperTank(selector).getTankSelector());
        }
    }

    /**
     * Checks that all tanks have the correct initial color, also after reset().
     */
    @Test
    public void correctColor() {
        assertTrue(TankSelector.MIX.getInitialColor().equals(prodSite.getMixTank().getLiquid().getColor()));

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertTrue(selector.getInitialColor().equals(prodSite.getUpperTank(selector).getLiquid().getColor()));
        }

        modifyEverything();
        prodSite.reset();

        assertTrue(TankSelector.MIX.getInitialColor().equals(prodSite.getMixTank().getLiquid().getColor()));
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertTrue(selector.getInitialColor().equals(prodSite.getUpperTank(selector).getLiquid().getColor()));
        }
    }

    /**
     * Checks that all tanks have the correct initial color, also after reset().
     */
    @Test
    public void correctFillLevel() {
        assertTrue(TankSelector.MIX.getInitialColor().equals(prodSite.getMixTank().getLiquid().getColor()));
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertTrue(selector.getInitialColor().equals(prodSite.getUpperTank(selector).getLiquid().getColor()));
        }

        modifyEverything();
        prodSite.reset();

        assertTrue(TankSelector.MIX.getInitialColor().equals(prodSite.getMixTank().getLiquid().getColor()));
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            assertTrue(selector.getInitialColor().equals(prodSite.getUpperTank(selector).getLiquid().getColor()));
        }
    }

    /**
     * Modifies the ProductionSite to test reset().
     */
    private void modifyEverything() {
        Liquid l = prodSite.getMixTank().getLiquid();
        prodSite.getMixTank().setLiquid(modifyLiquid(l));
        prodSite.getMixTank().getOutPipe().setValveThreshold((byte) 0);

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            l = prodSite.getUpperTank(selector).getLiquid();
            prodSite.getUpperTank(selector).setLiquid(modifyLiquid(l));
            prodSite.getUpperTank(selector).getInPipe().setValveThreshold((byte) 0);
            prodSite.getUpperTank(selector).getOutPipe().setValveThreshold((byte) 0);
        }
    }
    
    /**
     * Modifies a liquid to test reset(). 
     * 
     * @param l the liquid for modifying.
     * @return the modified liquid.
     */
    private Liquid modifyLiquid(Liquid l) {
        Color c = l.getColor();
        return new Liquid(l.getAmount() + 1, l.getTemperature() + 2, new Color(c.getCyan(),
            c.getMagenta(), c.getYellow()));
    }

    /**
     * Checks that the production site is in a stable state, also after reset().
     */
    @Test
    public void correctState() {
        byte equality = prodSite.getMixTank().getOutPipe().getValveThreshold();
        assertEquals(0.5, prodSite.getMixTank().getFillLevel(), 0.0001);

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            equality -= prodSite.getUpperTank(selector).getOutPipe().getValveThreshold();
            assertEquals(prodSite.getUpperTank(selector).getInPipe().getValveThreshold(),
                prodSite.getUpperTank(selector).getOutPipe().getValveThreshold());
            assertEquals(0.5, prodSite.getUpperTank(selector).getFillLevel(), 0.0001);
        }
        assertEquals(0, equality);

        modifyEverything();
        prodSite.reset();

        equality = prodSite.getMixTank().getOutPipe().getValveThreshold();
        assertEquals(0.5, prodSite.getMixTank().getFillLevel(), 0.0001);

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            equality -= prodSite.getUpperTank(selector).getOutPipe().getValveThreshold();
            assertEquals(prodSite.getUpperTank(selector).getInPipe().getValveThreshold(),
                    prodSite.getUpperTank(selector).getOutPipe().getValveThreshold());
            assertEquals(0.5, prodSite.getUpperTank(selector).getFillLevel(), 0.0001);
        }
        assertEquals(0, equality);
    }

    /**
     * Tests that the ProductionSite remembers temperatures.
     */
    @Test
    public void testInputTemperature() {
        prodSite.addObserver(this);
        assertFalse(updated);
        prodSite.setInputTemperature(TankSelector.MIX, SimulationConstants.MIN_TEMPERATURE);
        assertEquals(SimulationConstants.MIN_TEMPERATURE, prodSite.getInputTemperature(TankSelector.MIX), 0.0001);
        assertTrue(updated);
        updated = false;
        prodSite.reset();
        assertTrue(updated);
    }

    /**
     * Tests that the ProductionSite rejects too low temperatures.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTooLowInputTemperature() {
        prodSite.setInputTemperature(TankSelector.MIX, SimulationConstants.MIN_TEMPERATURE - 1);
    }

    /**
     * Tests that the ProductionSite rejects too high temperatures.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTooHighInputTemperature() {
        prodSite.setInputTemperature(TankSelector.MIX, SimulationConstants.MAX_TEMPERATURE + 1);
    }

    @Override
    public void update(Observable o, Object arg) {
        updated = true;
    }
}
