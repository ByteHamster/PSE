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
        //throw new RuntimeException("Not implemented!");
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
     * @throws InvalidHostException Thrown if the given host is not valid
     * @param host 
     */
    public final static void checkHost (String host) {
        //throw new RuntimeException("Not implemented!");
        boolean validIp;
        boolean validHostname;
        if (host == null) {
        	throw new InvalidHostException(host, "is null");
        }
    	String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
    	String hostnamePattern = "[a-z0-9]+[\\.]{1}[a-z0-9]+[\\.]{1}[a-z0-9]+[\\.]{1}[a-z0-9]+";
    	validIp = host.matches(ipPattern);
        validHostname = host.matches(hostnamePattern);
    	if (validIp != true && validHostname != true) {
        	throw new InvalidHostException(host, "is neither an ip nor a hostname");
        }
    }
    /**
     * Parses a percentage value and checks whether is valid.
     * @throws InvalidPercentageException Thrown, if percentage does not represent an int or if percentage is not in the range from 0 to 100.
     * @return percentage parsed into an int.
     * @param percentage The percentage to parse
     */
    public final static int checkPercentage (String percentage) {
        //throw new RuntimeException("Not implemented!");
        int result;
    	if (percentage == null) {
        	throw new InvalidPortException(percentage, "is null");
        }
        try {
    	result = Integer.parseInt(percentage);
        } catch (NumberFormatException e) {
        	throw new InvalidPortException(percentage, "contains non-digits");
        }
        if (result < 0 || result > 100) {	
        	throw new InvalidPortException(percentage, "is not between 0 and 100");
        }
    	return result;
    }
}
