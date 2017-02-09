package edu.kit.pse.osip.core.io.networking;

/**
 * Class for saving connection and networking details
 * @author  Maximilian Schwarzmann
 * @version 1.0
 */
public class RemoteMachine {
    
    private String hostname;
    private int port;
    
    /**
     * Constructor of RemoteMachine
     * @param hostname host name of RemoteMachine
     * @param port The port of the RemoteMachine
     */
    public RemoteMachine(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
    /**
     * Getter method of the hostname @return hostname of RemoteMachine
     */
    public final String getHostname() {
        return hostname;
    }
    /**
     * Getter method of port @return port of RemoteMachine
     */
    public final int getPort() {
        return port;
    }
}
