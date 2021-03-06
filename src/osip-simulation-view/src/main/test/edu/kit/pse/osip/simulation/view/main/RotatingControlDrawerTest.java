package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class testing RotatingControlDrawer.
 * In tests regarding RotatingControlDrawer.rotate Cw stands for clockwise (positive degrees) and Ccw for
 * counterclockwise (negative degrees) rotation.
 *
 * @author Niko Wilhelm
 * @version 1.0
 */
public class RotatingControlDrawerTest {

    private RotatingControlDrawer drawer;

    /**
     * Initializes a new RotatingControlDrawer and sets its speed to 1.
     */
    @Before
    public void setUp() {
        drawer = new RotatingControlDrawer(null, 0) {
            @Override
            public void draw(GraphicsContext context, double timeDiff) {

            }
        };
        drawer.setSpeed(1);
    }

    /**
     * Tests for no rotation at all.
     */
    @Test
    public void updateDegreesZero() {
        drawer.updateDegrees(0, 1);
        assertEquals(0, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests for one full revolution.
     */
    @Test
    public void updateDegreesNonZero() {
        // ask for degrees after timeDiff mins
        drawer.updateDegrees(1, 1);
        assertEquals(0, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests with higher speed factor in the formula.
     */
    @Test
    public void updateDegreesHighFactor() {
        drawer.updateDegrees(1, 1.5);
        assertEquals(180, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests with lower speed factor in the formula.
     */
    @Test
    public void updateDegreesLowFactor() {
        drawer.updateDegrees(1, 0.5);
        assertEquals(180, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests with higher timediff in the formula.
     */
    @Test
    public void updateDegreesHighTime() {
        drawer.updateDegrees(1.5, 1);
        assertEquals(180, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests with lower timediff in the formula.
     */
    @Test
    public void updateDegreesLowTime() {
        drawer.updateDegrees(0.5, 1);
        assertEquals(180, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests with higher speed set in the formula.
     */
    @Test
    public void updateDegreesHighSpeed() {
        drawer.setSpeed(2);
        drawer.updateDegrees(0.25, 1);
        assertEquals(180, drawer.getDegrees(), 0.001);
    }

    /**
     * Tests rotating the x coordinate 0 degrees.
     */
    @Test
    public void rotateXZero() {
        double newXPos = RotatingControlDrawer.rotateX(0, 1, 0, 0, 0);
        assertEquals(0, newXPos, 0.001);

        double newXNeg = RotatingControlDrawer.rotateX(0, 1, 0, 0, -0);
        assertEquals(0, newXNeg, 0.001);
    }

    /**
     * Tests a full clockwise rotation of the x coordinate.
     */
    @Test
    public void rotateXFullCw() {
        double newXCw = RotatingControlDrawer.rotateX(0, 1, 0, 0, 360);
        assertEquals(0, newXCw, 0.001);
    }

    /**
     * Tests a full counter clockwise rotation of the x coordinate.
     */
    @Test
    public void rotateXFullCcw() {
        double newXCcw = RotatingControlDrawer.rotateX(0, 1, 0, 0, -360);
        assertEquals(0, newXCcw, 0.001);
    }

    /**
     * Tests a half clockwise rotation of the x corrdinate.
     */
    @Test
    public void rotateXHalfCw() {
        double newXCw = RotatingControlDrawer.rotateX(0, 1, 0, 0, 180);
        assertEquals(2, newXCw, 0.001);
    }

    /**
     * Tests a half counter clockwise rotation of the x coordinate.
     */
    @Test
    public void rotateXHalfCcw() {
        double newXCcw = RotatingControlDrawer.rotateX(0, 1, 0, 0, -180);
        assertEquals(2, newXCcw, 0.001);
    }

    /**
     * Tests rotating the y coordinate 0 degrees.
     */
    @Test
    public void rotateYZero() {
        double newYPos = RotatingControlDrawer.rotateY(0, 1, 0, 0, 0);
        assertEquals(0, newYPos, 0.001);

        double newYNeg = RotatingControlDrawer.rotateY(0, 1, 0, 0, -0);
        assertEquals(0, newYNeg, 0.001);
    }

    /**
     * Tests a full clockwise rotation of the y coordinate.
     */
    @Test
    public void rotateYFullCw() {
        double newYCw = RotatingControlDrawer.rotateY(0, 0, 0, 1, 360);
        assertEquals(0, newYCw, 0.001);
    }

    /**
     * Tests a full counter clockwise rotation of the y coordinate.
     */
    @Test
    public void rotateYFullCcw() {
        double newYCcw = RotatingControlDrawer.rotateY(0, 0, 0, 1, -360);
        assertEquals(0, newYCcw, 0.001);
    }

    /**
     * Tests a half clockwise rotation of the y coordinate.
     */
    @Test
    public void rotateYHalfCw() {
        double newYCw = RotatingControlDrawer.rotateY(0, 0, 0, 1, 180);
        assertEquals(2, newYCw, 0.001);
    }

    /**
     * Tests a half counter clockwise rotation of the y coordinate.
     */
    @Test
    public void rotateYHalfCcw() {
        double newYCcw = RotatingControlDrawer.rotateY(0, 0, 0, 1, -180);
        assertEquals(2, newYCcw, 0.001);
    }
}
