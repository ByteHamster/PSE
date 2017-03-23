package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Pipe;
import org.junit.Test;

/**
 * A class testing PipeDrawer.
 */
public class PipeDrawerTest {

    /**
     * Checks for InvalidWayPointsException if there are too few points.
     */
    @Test(expected = InvalidWaypointsException.class)
    public void pipeDrawerShortList() {
        Point2D[] waypoints = {};
        new PipeDrawer(waypoints, null, 1);
    }

    /**
     * Checks for InvalidWayPointsException if the points are not properly aligned.
     */
    @Test(expected = InvalidWaypointsException.class)
    public void pipeDrawerMisPlacedPoints() {
        Point2D[] waypoints = {
            new Point2D(1, 1),
            new Point2D(2, 2)
        };
        new PipeDrawer(waypoints, null, 1);
    }

    /**
     * Tests whether there is no exception thrown if all parameters are properly
     * initialized.
     */
    @Test
    public void pipeDrawerValidPoints() {
        Point2D[] waypoints = {
            new Point2D(1, 1),
            new Point2D(1, 2),
            new Point2D(2, 2)
        };
        Pipe pipe = new Pipe(1f, 1, (byte) 1);

        new PipeDrawer(waypoints, pipe, 1);
    }
}
