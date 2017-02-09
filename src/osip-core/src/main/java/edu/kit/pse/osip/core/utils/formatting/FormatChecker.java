package edu.kit.pse.osip.core.utils.formatting;

/**
 * This class provides static methods to check input formats.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public final class FormatChecker {
    
    private FormatChecker() {
    }
    
    /**
     * Parses port into an int.
     * 
     * @throws InvalidPortException Thrown, if port is not either an int between 1024 and 61000
     * or the string is empty, contains characters etc.
     * @return The port number
     * @param port The port to parse
     *            
     */
    public static int checkPort(String port) {
        int result;
        if (port == null) {
            throw new InvalidPortException(port, "is null");
        }
        try {
            result = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new InvalidPortException(port, "contains non-digits");
        }
        if (result < 1024 || result > 61000) {
            throw new InvalidPortException(port, "is not between 1024 and 61000");
        }
        return result;
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
        String hostnamePattern = "[0-9a-zA-Z\\-_\\.\\\\]+";
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
