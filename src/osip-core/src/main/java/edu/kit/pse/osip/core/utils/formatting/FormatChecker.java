package edu.kit.pse.osip.core.utils.formatting;

/**
 * This class provides static methods to check input formats.
 */
public class FormatChecker {
    /**
     * Parses port into an int.
     * @throws InvalidPortException Thrown, if port is not either an int between 1024 and 61000 or the string is empty, contains characters etc.
     * @return The port number
     * @param port The port to parse
     */
    public final static int checkPort (String port) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Checks whether string is a valid IP address or hostname.
     * @throws InvalidHostException Thrown if the given host is not valid
     * @param host 
     */
    public final static void checkHost (String host) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Parses a percentage value and checks whether is valid.
     * @throws InvalidPercentageException Thrown, if percentage does not represent an int or if percentage is not in the range from 0 to 100.
     * @return percentage parsed into an int.
     * @param percentage The percentage to parse
     */
    public final static int checkPercentage (String percentage) {
        throw new RuntimeException("Not implemented!");
    }
}
