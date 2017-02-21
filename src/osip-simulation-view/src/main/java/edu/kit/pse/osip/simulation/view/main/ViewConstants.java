package edu.kit.pse.osip.simulation.view.main;

import javafx.stage.Screen;

/**
 * This class provides multiple constants to configure the drawing of
 * the simulation view.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public final class ViewConstants {

    /**
     * Private constructor to avoid instantiation.
     */
    private ViewConstants() {

    }

    /**
     * The gap between ui elements.
     */
    public static final int ELEMENTS_GAP = 5;

    /**
     * The font size in pixels, relative to the absolute screen height.
     */
    public static final int FONT_SIZE = (int) Math.round(0.01389 * Screen.getPrimary().getBounds().getHeight());

}
