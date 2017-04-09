package edu.kit.pse.osip.core.opcua;

import edu.kit.pse.osip.core.opcua.client.BooleanReceivedListener;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;
import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;
import edu.kit.pse.osip.core.opcua.client.UAClientWrapper;

/**
 * For test purposes, makes protected methods public.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TestUaClientWrapper extends UAClientWrapper {
    /**
     * Allows public access to the connection timeout.
     */
    protected static final int CONNECTION_TIMEOUT_TEST = UAClientWrapper.CONNECTION_TIMEOUT;

    /**
     * Allows public access to UAClientWrapper.
     * 
     * @param url Same as for UAClientWrapper.
     * @param namespace Same as for UAClientWrapper.
     */
    public TestUaClientWrapper(String url, String namespace) {
        super(url, namespace);
    }

    /**
     * Allows public access to subscribeInt.
     * 
     * @param nodeName Same as for subscribeInt.
     * @param interval Same as for subscribeInt.
     * @param listener Same as for subscribeInt.
     * @throws UAClientException Same as for subscribeInt.
     */
    protected final void subscribeIntTest(String nodeName, int interval, IntReceivedListener listener)
            throws UAClientException {
        super.subscribeInt(nodeName, interval, listener);
    }

    /**
     * Allows public access to subscribeBoolean.
     * 
     * @param nodeName Same as for subscribeBoolean.
     * @param interval Same as for subscribeBoolean.
     * @param listener Same as for subscribeBoolean.
     * @throws UAClientException Same as for subscribeBoolean.
     */
    protected final void subscribeBooleanTest(String nodeName, int interval, BooleanReceivedListener listener)
            throws UAClientException {
        super.subscribeBoolean(nodeName, interval, listener);
    }

    /**
     * Allows public access to subscribeFloat.
     * 
     * @param nodeName Same as for subscribeFloat.
     * @param interval Same as for subscribeFloat.
     * @param listener Same as for subscribeFloat.
     * @throws UAClientException Same as for subscribeFloat.
     */
    protected final void subscribeFloatTest(String nodeName, int interval, FloatReceivedListener listener)
            throws UAClientException {
        super.subscribeFloat(nodeName, interval, listener);
    }
}
