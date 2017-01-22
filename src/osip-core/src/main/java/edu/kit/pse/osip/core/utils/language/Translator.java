package edu.kit.pse.osip.core.utils.language;

/**
 * This class is the interface to load menu texts in different languages.
 */
public class Translator {
    public java.util.ResourceBundle bundle;
    public java.util.Locale locale;
    /**
     * Creates a new translator. Private because it is a singleton
     */
    private Translator () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the single Instance of the Translator. It is newly created at the first call of the method.
     * @return The single Instance of Translator.
     */
    public final static Translator getInstance () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the word that is associated with key in the current locale.
     * @return The translation for key.
     * @param key The key to use for translation lookup
     */
    public final String getString (String key) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the locale to be used when translating
     * @param locale The locale to set
     */
    public final void setLocale (java.util.Locale locale) {
        throw new RuntimeException("Not implemented!");
    }
}
