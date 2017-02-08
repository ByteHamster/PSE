package edu.kit.pse.osip.core.utils.language;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Test class for Translator
 * @author Maximilian Schwarzmann
 *
 */
public class TranslatorTest {

    String language;
    String country;
    String output;
    Locale loc;
    ResourceBundle bundle;
    Translator trans;
    

    /**
     * Create a new translator instance
     * @throws Exception
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
        language = "en";
        country = "US";
        loc = new Locale(language, country);
        bundle = ResourceBundle.getBundle("Bundle", loc);
        trans.setLocaleBundle(bundle);
        output = trans.getString("greet");
        assertEquals("hi", output);
    }

    /**
     * Get a German translation
     */
    @Test
    public void testGerman() {
        language = "ge";
        country = "GE";
        loc = new Locale(language, country);
        bundle = ResourceBundle.getBundle("Bundle", loc);
        trans.setLocaleBundle(bundle);
        output = trans.getString("greet");
        assertEquals("hallo", output);
    }
}
