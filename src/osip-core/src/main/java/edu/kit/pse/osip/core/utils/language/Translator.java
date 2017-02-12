package edu.kit.pse.osip.core.utils.language;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is the interface to load menu texts in different languages.
 * @author Maximilian Schwarzmann 
 * @version 1.0
 */
public final class Translator {
    
    private ResourceBundle bundle;
    private static Translator singleton;
    
    /**
     * Creates a new translator. Private because it is a singleton
     */
    private Translator() {
        setLocale(new Locale("en", "US"));
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
     * @return The translation for key or key if there is no such entry or key is null
     * @param key The key to use for translation lookup
     */
    public String getString(String key) {
        String value;
        try {
            value = bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return key;
        } 
        return value;
    }
    /**
     * Sets the locale to be used when translating
     * @param locale The locale to set
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        } 
        try {
            bundle = ResourceBundle.getBundle("Bundle", locale);
        } catch (MissingResourceException e) {
            e.printStackTrace();
            bundle = null;
        }
    }    
}
