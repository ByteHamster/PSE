package org.iMage.edge.detection;

/**
 * A class that allows Matrix calculations
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SquareMatrix {
    double[][] values;
    
    /**
     * Generates a new Matrix
     * @param values The values of the matrix
     */
    public SquareMatrix(double[][] values) {
        this.values = values;
        
        if (values.length < 1) {
            throw new IllegalArgumentException("Matrix must have a size");
        }
        
        for (int i = 0; i < values.length; i++) {
            if (values[i].length != values.length) {
                throw new IllegalArgumentException("Matrix must be square");
            }
        }
    }
    
    /**
     * Generates a new Matrix
     * @param size The size of the matrix
     */
    public SquareMatrix(int size) {
        this.values = new double[size][size];
    }
    
    /**
     * @return The size of this square matrix
     */
    public int getSize() {
        return values.length;
    }
    
    /**
     * Returns the value in the given field
     * @param x The column
     * @param y The row
     * @return The value in this field
     */
    public double get(int x, int y) {
        if (x < 0 || x >= getSize()) {
            throw new IllegalArgumentException("Trying to access a field outside the matrix");
        } else if (y < 0 || y >= getSize()) {
            throw new IllegalArgumentException("Trying to access a field outside the matrix");
        }
        return values[x][y];
    }
    
    /**
     * Sets the value in the given field
     * @param x The column
     * @param y The row
     * @param value The value to set
     */
    public void set(int x, int y, float value) {
        if (x < 0 || x >= getSize()) {
            throw new IllegalArgumentException("Trying to access a field outside the matrix");
        } else if (y < 0 || y >= getSize()) {
            throw new IllegalArgumentException("Trying to access a field outside the matrix");
        }
        values[x][y] = value;
    }
    
    /**
     * Multiply the matrix element by element
     * @param m The SquareMatrix to multiply
     * @return A new SquareMatrix containing the result
     */
    public SquareMatrix multiplyElementWise(SquareMatrix m) {
        if (getSize() != m.getSize()) {
            throw new IllegalArgumentException("Size does not match");
        }
        double[][] newValues = new double[getSize()][getSize()];
        
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                newValues[i][j] = get(i, j) * m.get(i, j);
            }
        }
        
        return new SquareMatrix(newValues);
    }
    
    /**
     * @return The sum of all fields within the matrix
     */
    public double valueSum() {
        double sum = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                sum += get(i, j);
            }
        }
        return sum;
    }
}
