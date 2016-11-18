package org.iMage.edge.detection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests square matrix calculations
 */
public class SquareMatrixTest {
    
    /**
     * Tests generating a matrix that is not square
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNonSquareMatrix() {
        new SquareMatrix(new double[][] {
            {0, 0, 0},
            {0, 0, 0},
        });
    }
    
    /**
     * Tests multiplying a SquareMatrix element wise
     */
    @Test
    public void testElementWiseMultiplication() {
        SquareMatrix m = new SquareMatrix(new double[][] {
            {10, 0,  0},
            { 0, 0,  0},
            { 1, 0, -1},
        });
        SquareMatrix newMatrix = m.multiplyElementWise(m);
        assertTrue(newMatrix.get(0, 0) == 100);
        assertTrue(newMatrix.get(2, 2) == 1);
    }
    
    /**
     * Tests summing up the calues
     */
    @Test
    public void testValueSum() {
        SquareMatrix m = new SquareMatrix(new double[][] {
            {10, 0,  0},
            { 0, 0,  0},
            { 1, 0, -1},
        });
        assertTrue(m.valueSum() == 10);
    }
    
    /**
     * Tests generating and modifying a valid matrix
     */
    @Test
    public void testValidMatrix() {
        SquareMatrix m = new SquareMatrix(new double[][] {
            {42, 0,  0},
            { 0, 0,  0},
            { 0, 0, -1},
        });
        assertTrue(m.get(0, 0) == 42);
        m.set(0, 0, -23);
        assertTrue(m.get(0, 0) == -23);
    }
    
    /**
     * Tests generating a matrix that is completely invalid
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMatrix() {
        new SquareMatrix(new double[][] {
            {0, 0, 0, 0},
            {0, 0, 0},
            {0},
            {0, 0, 0},
            {0, 0, 0},
        });
    }

}
