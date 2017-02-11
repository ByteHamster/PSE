package edu.kit.pse.osip.core.utils.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is the interface to load menu texts in different languages.
 * @author Maximilian Schwarzmann 
 * @version 1.0
 */
public final class Translator {
    
    private ResourceBundle bundle;
    private Locale loc;
    private static Translator singleton;
    
    /**
     * Creates a new translator. Private because it is a singleton
     */
    private Translator() {
        loc = new Locale("en", "US");
        bundle = ResourceBundle.getBundle("Bundle", loc);
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
        return bundle.getString(key);       
    }
    /**
     * Sets the locale to be used when translating
     * @param locale The locale to set
     * @throws TranslatorNullException Exception for null bundle
     */
    public void setLocale(Locale locale) throws NullPointerException {
        if (locale == null) {
            throw new NullPointerException();
        }
        loc = locale;
        bundle = ResourceBundle.getBundle("Bundle", loc);
    }    
}
