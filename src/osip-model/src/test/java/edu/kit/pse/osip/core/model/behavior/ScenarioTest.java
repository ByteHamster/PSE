package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A class to test the Scenario class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class ScenarioTest {
    private Scenario scenario;
    private AtomicInteger counter;
    private ProductionSite fakeSite;

    /**
     * Initialize scenario and counter.
     */
    @Before
    public void init() {
        scenario = new Scenario();
        counter = new AtomicInteger(0);
        fakeSite = Mockito.mock(ProductionSite.class);
    }

    /**
     * Check that starting the scenario without setting the production site first throws an exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testState1() {
        assertFalse(scenario.isRunning());
        scenario.startScenario();
    }

    /**
     * Check that canceling the scenario without starting it before throws an exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testState2() {
        assertFalse(scenario.isRunning());
        scenario.cancelScenario();
    }

    /**
     * Check that appendRunnable works fine
     */
    @Test
    public void testAddCommand() {
        CompletableFuture<Boolean> future1 = new CompletableFuture<>();
        CompletableFuture<Boolean> future2 = new CompletableFuture<>();
        CompletableFuture<Boolean> future3 = new CompletableFuture<>();

        scenario.appendRunnable(productionSite -> future1.complete(Boolean.valueOf(true)));
        scenario.appendRunnable(productionSite -> future2.complete(Boolean.valueOf(true)));
        scenario.appendRunnable(productionSite -> future3.complete(Boolean.valueOf(true)));
        scenario.setProductionSite(fakeSite);
        scenario.startScenario();

        try {
            assertEquals(true, future1.get(1, TimeUnit.SECONDS));
            assertEquals(true, future2.get(1, TimeUnit.SECONDS));
            assertEquals(true, future3.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            fail("CompletableFuture.get() failed: " + ex.getMessage());
        }
    }

    /**
     * Check that addPause works.
     */
    @Test
    public void testAddPause() {
        CompletableFuture<Boolean> future1 = new CompletableFuture<>();
        CompletableFuture<Boolean> future2 = new CompletableFuture<>();

        scenario.appendRunnable(productionSite -> future1.complete(Boolean.valueOf(true)));
        scenario.addPause(500);
        scenario.appendRunnable(productionSite -> future2.complete(Boolean.valueOf(true)));
        scenario.setProductionSite(fakeSite);
        scenario.startScenario();

        try {
            assertEquals(true, future1.get(100, TimeUnit.MILLISECONDS));
            assertEquals(false, future2.isDone());
            assertTrue(scenario.isRunning());
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            fail("CompletableFuture.get() failed: " + ex.getMessage());
        }

        try {
            sleep(300);
        } catch (InterruptedException ex) {
            System.err.println("Sleep failed in ScenarioTest: " + ex.getMessage());
        }
        assertEquals(false, future2.isDone());
        assertTrue(scenario.isRunning());

        try {
            assertEquals(true, future2.get(300, TimeUnit.MILLISECONDS));
            sleep(50);
            assertFalse(scenario.isRunning());
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            fail("CompletableFuture.get() failed: " + ex.getMessage());
        }
    }

    /**
     * Check whether canceling the scenario during a pause works fine.
     */
    @Test
    public void testStop() {
        CompletableFuture<Boolean> finished = new CompletableFuture<>();
        scenario.appendRunnable(productionSite -> counter.incrementAndGet());
        scenario.addPause(300);
        scenario.appendRunnable(productionSite -> counter.incrementAndGet());
        scenario.setProductionSite(fakeSite);
        scenario.setScenarioFinishedListener(() -> finished.complete(true));
        scenario.startScenario();
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            System.err.println("Sleep failed in ScenarioTest: " + ex.getMessage());
        }
        assertEquals(1, counter.get());
        assertTrue(scenario.isRunning());

        scenario.cancelScenario();
        try {
            sleep(400);
        } catch (InterruptedException ex) {
            System.err.println("Sleep failed in ScenarioTest: " + ex.getMessage());
        }
        try {
            assertEquals(1, counter.get());
            assertFalse(scenario.isRunning());
            assertTrue(finished.get());
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("CompletableFuture.get() failed in ScenarioTest: " + ex.getMessage());
        }
    }

    /**
     * Check setRepeat works fine
     */
    @Test
    public void testSetRepeat() {
        AtomicInteger counter = new AtomicInteger();

        scenario.appendRunnable(productionSite -> counter.incrementAndGet());
        scenario.setProductionSite(fakeSite);
        scenario.setRepeat(true);
        scenario.startScenario();

        try {
            sleep(1000);
            int state1 = counter.get();
            sleep(1000);
            assertTrue(counter.get() > state1);
        } catch (InterruptedException ex) {
            fail("Thread.sleep failed: " + ex.getMessage());
        }

        /* Scenario should still be running */
        assertTrue(scenario.isRunning());
        scenario.cancelScenario();
    }
}
