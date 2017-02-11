package edu.kit.pse.osip.simulation.view.main;

import java.util.*;

import edu.kit.pse.osip.core.model.base.*;
import edu.kit.pse.osip.simulation.controller.MenuAboutButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuHelpButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuSettingsButtonHandler;
import edu.kit.pse.osip.simulation.controller.MenuControlButtonHandler;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;

/**
 * The main window for visualizing the OSIP simulation. It regularly updates itself with current information from the model and posesses an update() method for alarms. If an overflow occurs in the model it is be displayed by an overlay.
 */
public class SimulationMainWindow implements edu.kit.pse.osip.simulation.controller.SimulationViewInterface, java.util.Observer {
    public ProductionSite productionSite;
    public Collection<Drawer> element;

    /**
     * Creates a new SimulationMainWindow
     * @param productionSite The ProductionSite object, so that the view can access the model
     */
    public SimulationMainWindow (ProductionSite productionSite) {
        element = new ArrayList<Drawer>();
        this.productionSite = productionSite;

        int tankCount = TankSelector.getUpperTankCount();
        TankSelector[] topTanks = TankSelector.valuesWithoutMix();
        for (int i = 0; i < tankCount; i++) {
            double xPos = i * (1.0 / tankCount);
            Point2D position = new Point2D(xPos, 0.0);
            Tank tank = productionSite.getUpperTank(topTanks[i]);
            element.add(new TankDrawer(position, tank, 2, tankCount));
        }

        // get all the tanks from the productionsite
        MixTank mix = productionSite.getMixTank();
        double xPos = ((double) (tankCount - 1)) / (tankCount * 2);
        //Assuming there are only ever two rows of tanks
        double yPos = 0.5;
        Point2D mixPos = new Point2D(xPos, yPos);
        element.add(new MixTankDrawer(mixPos, mix, 2, tankCount));
        throw new RuntimeException("Not implemented!");
    }

    /**
     * The stage that is provided by JavaFx
     * @param primaryStage The stage to draw the window on
     */
    public final void start (javafx.stage.Stage primaryStage) {
        primaryStage.setTitle("OSIP Simulation");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        // Get screen dimensions of the primary screen (minus taskbars etc)
        Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(screenDimensions.getMinX());
        primaryStage.setY(screenDimensions.getMinY());
        primaryStage.setWidth(screenDimensions.getWidth());
        primaryStage.setHeight(screenDimensions.getHeight());

        Canvas canvas = new Canvas(screenDimensions.getWidth(), screenDimensions.getHeight());

        root.getChildren().add(canvas);
        //TODO: make sure Stage/ Scene/ Canvas is maximized

        GraphicsContext context = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                // TODO: clear whole canvas

                for (Drawer d : element) {
                    d.draw(context, currentTime);
                }
            }
        }.start();

        primaryStage.show();
        throw new RuntimeException("Not implemented!");
    }

    /**
     * The simulation is replaced by the OverflowOverlay.
     */
    public final void showOverflow() {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * The observed object received an update
     * @param observable The observed object
     * @param object The new value
     */
    public final void update(Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public final void setControlButtonHandler(MenuControlButtonHandler controlButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler(MenuSettingsButtonHandler settingsButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Sets the handler for pressing the about entry in the menu
     * @param aboutButtonHandler The handler to be called when the about button is pressed
     */
    public final void setAboutButtonHandler(MenuAboutButtonHandler aboutButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Sets the handler for pressing the help entry in the menu
     * @param helpButtonHandler The handler to be called when the help button is pressed
     */
    public final void setHelpButtonHandler(MenuHelpButtonHandler helpButtonHandler) {
        throw new RuntimeException("Not implemented!");
    }
}
