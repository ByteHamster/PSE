package edu.kit.pse.osip.core.utils.language;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is the interface to load menu texts in different languages.
 * 
 * @author Maximilian Schwarzmann 
 * @version 1.0
 */
public final class Translator {
    /**
     * ResourceBundle saving the translations.
     */
    private ResourceBundle bundle;
    /**
     * Saves the sole accessible instance.
     */
    private static Translator singleton;
    /**
     * true when the keys shall always be returned and false otherwise.
     */
    private boolean localizationDisabled = false;
    
    /**
     * Creates a new translator. Private because it is a singleton.
     */
    private Translator() {
        setLocale(new Locale("en", "US"));
    }
    /**
     * Gets the single instance of the Translator. It is newly created at the first call of the method.
     * 
     * @return The single instance of Translator.
     */
    public static Translator getInstance() {
        if (singleton == null) {
            singleton = new Translator();
        }
        return singleton;
    }
    /**
     * Gets the word that is associated with key in the current locale.
     * 
     * @param key The key to use for translation lookup.
     * @return The translation for key or key if there is no such entry.
     * Also returns key if bundle is null.
     */
    public String getString(String key) {
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        if (localizationDisabled) {
            return key;
        }
        if (bundle == null) {
            return key;
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
    /**
     * Sets the locale to be used when translating.
     * 
     * @param locale The locale to be set.
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new NullPointerException("Locale is null");
        } 
        try {
            bundle = ResourceBundle.getBundle("Bundle", locale);
        } catch (MissingResourceException e) {
            System.err.println();
            bundle = null;
        }
    }

    /**
     * Disables localization: Always shows IDs.
     * 
     * @param localizationDisabled true if localization should be disabled.
     */
    public void setLocalizationDisabled(boolean localizationDisabled) {
        this.localizationDisabled = localizationDisabled;
    }
}
