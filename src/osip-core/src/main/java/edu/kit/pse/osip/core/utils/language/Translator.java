package edu.kit.pse.osip.core.utils.language;

/**
 * This class is the interface to load menu texts in different languages.
 */
public final class Translator {
    private java.util.ResourceBundle bundle;
    
    private static Translator singleton;
    
    /**
     * Creates a new translator. Private because it is a singleton
     */
    private Translator() {
        //throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the single Instance of the Translator. It is newly created at the first call of the method.
     * @return The single Instance of Translator.
     */
    public static Translator getInstance() {
        if (singleton == null) {
            singleton = new Translator();
        }
        return singleton;
    }
    /**
     * Gets the word that is associated with key in the current locale.
     * @return The translation for key.
     * @param key The key to use for translation lookup
     */
    public String getString(String key) {
        return singleton.getLocaleBundle().getString(key);
        
    }
    /**
     * Sets the locale to be used when translating
     * @param bundle The locale to set
     * @throws TranslatorNullException Exception for null bundle
     */
    public void setLocaleBundle(java.util.ResourceBundle bundle) throws TranslatorNullException {
        if (bundle == null) {
            throw new TranslatorNullException("The input bundle is null");
        }
        singleton.bundle = bundle;
    }

    /**
     * Getter method for bundle
     * @return bundle of instance
     */
    public java.util.ResourceBundle getLocaleBundle() {
        return bundle;
    }
    
}
