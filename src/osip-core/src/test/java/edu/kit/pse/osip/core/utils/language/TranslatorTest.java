package edu.kit.pse.osip.core.utils.language;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Test class for Translator
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TranslatorTest {

    Translator trans;
    
    /**
     * Create a new translator instance
     */
    @Before
    public void setUp() {
        trans = Translator.getInstance(); 
    }

    /**
     * Get an English translation
     */
    @Test
    public void testStd() {
        String language = "en";
        String country = "US";
        Locale loc = new Locale(language, country);
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle", loc);
        trans.setLocaleBundle(bundle);
        String output = trans.getString("greet");
        assertEquals("hi", output);
    }

    /**
     * Get a German translation
     */
    @Test
    public void testGerman() {
        String language = "ge";
        String country = "GE";
        Locale loc = new Locale(language, country);
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle", loc);
        trans.setLocaleBundle(bundle);
        String output = trans.getString("greet");
        assertEquals("hallo", output);
    }
}
