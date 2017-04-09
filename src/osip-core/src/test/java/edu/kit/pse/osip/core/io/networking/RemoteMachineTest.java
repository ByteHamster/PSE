package edu.kit.pse.osip.core.io.networking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for RemoteMachine.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class RemoteMachineTest {
    /**
     * Tests getter of host name.
     */
    @Test
    public void testHostnameGetter() {
        RemoteMachine machine = new RemoteMachine("mac", 10);
        assertEquals("mac", machine.getHostname());
    }

    /**
     * Tests getter of port.
     */
    @Test
    public void testPortGetter() {
        RemoteMachine machine = new RemoteMachine("mac", 10);
        assertEquals(10, machine.getPort());
    }    
}
