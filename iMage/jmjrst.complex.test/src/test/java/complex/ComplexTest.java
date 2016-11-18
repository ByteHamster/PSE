package complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests complex number calculations
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ComplexTest {

    /**
     * Checks if the copy constructor really copies a number
     */
    @Test
    public void testClone() {
        Complex c = new Complex(1, 1);
        Complex copy = new Complex(c);

        assertFalse(c == copy);
    }

    /**
     * Square i
     */
    @Test
    public void testSquareI() {
        Complex i = new Complex(0, 1);
        i.square();
        assertTrue(i.imag() == 0);
        assertTrue(i.real() == -1);
    }

    /**
     * Cube i
     */
    @Test
    public void testCubeI() {
        Complex i = new Complex(0, 1);
        i.cube();
        assertTrue(i.imag() == -1);
        assertTrue(i.real() == 0);
    }

    /**
     * Check square of length
     */
    @Test
    public void testModsq() {
        Complex c = new Complex(1, 1);
        // Double is not 100% exact
        assertTrue(c.modsq() - 2 < 0.00000000000001F);
    }

    /**
     * Test toString() "Erstellet eine textuelle Repräsentation der komplexen
     * Zahl this in der Form (<Realteil> + <Imaginärteil>i)."
     * 
     * Man beachte das Leerzeichen um das Plus...
     */
    @Test
    @Ignore
    public void testToString() {
        Complex c = new Complex(5, 3);
        assertEquals("5.0 + 3.0i", c.toString());
    }

    /**
     * Test adding complex numbers
     */
    @Test
    public void testAdd() {
        Complex c = new Complex(1, 1);
        c.add(c);
        assertTrue(c.imag() == 2);
        assertTrue(c.real() == 2);
    }

    /**
     * Test subtracting complex numbers
     */
    @Test
    public void testSubtract() {
        Complex c = new Complex(1, 1);
        c.sub(c);
        assertTrue(c.imag() == 0);
        assertTrue(c.real() == 0);
    }

    /**
     * Test multiplying complex numbers
     */
    @Test
    public void testMultiply() {
        Complex c = new Complex(1, 1);
        c.mul(c);
        assertTrue(c.imag() == 2);
        assertTrue(c.real() == 0);
    }

    /**
     * Test multiplying complex numbers with zero
     */
    @Test
    public void testMultiplyZero() {
        Complex c = new Complex(1, 1);
        c.mul(new Complex(0, 0));
        assertTrue(c.imag() == 0);
        assertTrue(c.real() == 0);
    }

    /**
     * Test dividing complex numbers
     */
    @Test
    public void testDivide() {
        Complex c = new Complex(1, 1);
        c.div(c);
        assertTrue(c.imag() == 0);
        assertTrue(c.real() == 1);
    }

    /**
     * Test if sub, add, mul, div return the original object
     * 
     * "Achtung! Die komplexe Zahl this wird bei dieser Operation verändert und
     * als Ergebnis zurückgeliefert. Benutzen Sie den Kopierkonstruktor, um die
     * komplexe Zahl vor dem Ausführen der Operation zu sichern."
     */
    @Test
    @Ignore
    public void testReturnOriginal() {
        Complex c = new Complex(1, 1);
        assertTrue(c == c.div(c));
        assertTrue(c == c.add(c));
        assertTrue(c == c.mul(c));
        assertTrue(c == c.sub(c));
    }
}