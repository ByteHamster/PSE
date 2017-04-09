package edu.kit.pse.osip.core.utils.language;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Translator.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TranslatorTest {
    /**
     * The used Translator instance.
     */
    private Translator translator;
    
    /**
     * Create a new translator instance.
     */
    @Before
    public void setUp() {
        translator = Translator.getInstance(); 
    }

    /**
     * Get an English translation.
     */
    @Test
    public void testStd() {
        String output = translator.getString("greet");
        assertEquals("hi", output);
    }

    /**
     * Get a German translation.
     */
    @Test
    public void testGerman() {
        String language = "ge";
        String country = "GE";
        Locale loc = new Locale(language, country);
        translator.setLocale(loc);
        String output = translator.getString("greet");
        assertEquals("hallo", output);
    }

    /**
     * Tests null as input.
     */
    @Test
    public void testMissingKey() {
        String output = translator.getString("branch");
        assertEquals("branch", output);
    }
     
    /**
     * Tests null key.
     */
    @Test(expected = NullPointerException.class)
    public void testNullKey() {
        String output = translator.getString(null);
    }
        
    /**
     * Tests null as input.
     */
    @Test(expected = NullPointerException.class)
    public void testNullException() {
        Locale loc = null;
        translator.setLocale(loc);
    }
}
