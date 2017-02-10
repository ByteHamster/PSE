package edu.kit.pse.osip.core.utils.language;

/**
 * This exception signifies that a port was invalid.
 */
public class TranslatorNullException extends java.lang.IllegalArgumentException {

    private static final long serialVersionUID = -691973949419701068L;
    
    /**
     * Creates a new TranslatorNullException
     *          
     * @param reason The reason why it went wrong
     *            
     */
    public TranslatorNullException(String reason) {
        super(reason);
    }
}
