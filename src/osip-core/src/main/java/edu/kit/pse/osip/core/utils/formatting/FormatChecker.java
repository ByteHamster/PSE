package edu.kit.pse.osip.core.utils.formatting;

/**
 * This class provides static methods to check input formats.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public final class FormatChecker {
    private static final int PORT_MAX = 61000;
    private static final int PORT_MIN = 1024;
    
    private FormatChecker() {
    }
    
    /**
     * Checks if the given string is a valid port
     *
     * @param port The port to check
     * @return true if the port is valid
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
        return !(result < PORT_MIN || result > PORT_MAX);
    }

    /**
     * Checks whether string is a valid IP address or hostname.
     * 
     * @throws InvalidHostException Thrown if the given host is not valid
     *             
     * @param host The host to check
     */
    public static void checkHost(String host) {
        boolean validHostname;
        if (host == null) {
            throw new InvalidHostException(host, "is null");
        }
        String hostnamePattern = "[0-9a-zA-Z\\-_\\.]+";
        validHostname = host.matches(hostnamePattern);
        if (!validHostname) {
            throw new InvalidHostException(host, "is neither an ip nor a hostname");
        }
    }

    /**
     * Parses a percentage value and checks whether is valid.
     * 
     * @throws InvalidPercentageException Thrown, if percentage does not represent an int or if
     * percentage is not in the range from 0 to 100.
     * @return percentage parsed into an int.
     * @param percentage The percentage to parse
     *            
     */
    public static int checkPercentage(String percentage) {
        int result;
        if (percentage == null) {
            throw new InvalidPercentageException(percentage, "is null");
        }
        try {
            result = Integer.parseInt(percentage);
        } catch (NumberFormatException e) {
            throw new InvalidPercentageException(percentage, "contains non-digits");
        }
        if (result < 0 || result > 100) {
            throw new InvalidPercentageException(percentage, "is not between 0 and 100");
        }
        return result;
    }
}
