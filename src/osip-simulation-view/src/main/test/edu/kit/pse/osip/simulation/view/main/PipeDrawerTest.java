package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Pipe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A class testing PipeDrawer.
 */
public class PipeDrawerTest {

    @Test(expected = InvalidWaypointsException.class)
    public void PipeDrawerShortList() {
        Point2D[] waypoints = {};
        new PipeDrawer(waypoints, null, 1);
    }

    @Test(expected = InvalidWaypointsException.class)
    public void PipeDrawerMisPlacedPoints() {
        Point2D[] waypoints = {new Point2D(1, 1),
            new Point2D(2, 2)};
        new PipeDrawer(waypoints, null, 1);
    }

    @Test
    public void PipeDrawerValidPoints() {
        Point2D[] waypoints = {new Point2D(1, 1),
            new Point2D(1, 2),
            new Point2D(2, 2)};
        Pipe pipe = new Pipe(1f, 1, (byte)1);

        new PipeDrawer(waypoints, pipe, 1);
    }

}