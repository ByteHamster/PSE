package edu.kit.pse.osip.core.utils.formatting;

import edu.kit.pse.osip.core.OSIPConstants;

/**
 * This class provides static methods to check input formats.
 * 
 * @author Maximilian Schwarzmann, Martin Armbruster
 * @version 1.1
 */
public final class FormatChecker {
    /**
     * Private constructor to avoid instantiation.
     */
    private FormatChecker() {
    }
    
    /**
     * Checks if the given string is a valid port.
     *
     * @param port The port to check.
     * @return true if the port is valid.
     */
    public static boolean isValidPort(String port) {
        int result;
        if (port == null) {
            throw new NullPointerException("Port is null");
        }
        try {
            result = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return false;
        }
        return !(result < OSIPConstants.MIN_PORT || result > OSIPConstants.MAX_PORT);
    }

    /**
     * Checks whether string is a valid IP address or hostname.
     * 
     * @param host The host to check.
     * @return true when the string is valid. false otherwise.
     */
    public static boolean checkHost(String host) {
        if (host == null) {
            return false;
        }
        String hostnamePattern = "[0-9a-zA-Z\\-_\\.]+";
        return host.matches(hostnamePattern);
    }

    /**
     * Checks whether a percentage is valid.
     * 
     * @param percentage The percentage to parse.
     * @return true when the percentage is valid. false otherwise.
     */
    public static boolean checkPercentage(String percentage) {
        int result;
        if (percentage == null) {
            return false;
        }
        try {
            result = Integer.parseInt(percentage);
        } catch (NumberFormatException e) {
            return false;
        }
        return !(result < 0 || result > 100);
    }
}
