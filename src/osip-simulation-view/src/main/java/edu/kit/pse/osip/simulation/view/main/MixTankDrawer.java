package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.MixTank;
import javafx.scene.canvas.GraphicsContext;

/**
 * The class visualizes a tank that is connected to several inputs. Due to this the fill level as well as the color of the contained liquid might change with time.
 */
public class MixTankDrawer extends edu.kit.pse.osip.simulation.view.main.AbstractTankDrawer {
    /**
     * Creates a new MixTankDrawer
     * @param pos The upper left corner of the tank
     * @param tank The tank to get the attributes from
     */
    public MixTankDrawer (Point2D pos, MixTank tank) {
        super(pos, tank);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add temperature- and fillsensor as well as a motor visualization to the tank.
     * @param context 
     */
    public final void drawSensors (GraphicsContext context) {
        throw new RuntimeException("Not implemented!");
    }
}
