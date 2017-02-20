package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.AbstractMenuAboutButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuHelpButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuControlButton;
import edu.kit.pse.osip.simulation.controller.AbstractMenuSettingsButton;
import edu.kit.pse.osip.simulation.controller.SimulationViewInterface;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Collection;
import java.util.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main window for visualizing the OSIP simulation.
 * It regularly updates itself with current information from the model and posesses an update() method for alarms.
 * If an overflow occurs in the model it is be displayed by an overlay.
 *
 * @version 0.5
 * @author Niko Wilhelm
 */
public class SimulationMainWindow implements SimulationViewInterface, Observer {

    /**
     * It is assumed, that the tanks and mixtank are always ordered in two rows.
     */
    private final int ROWS = 2;

    private SimulationMenu menu;
    private Collection<Drawer> element;
    private Canvas canvas;

    /**
     * Creates a new SimulationMainWindow. It is assumed that there will only ever be two rows of tanks.
     * @param productionSite The ProductionSite object, so that the view can access the model
     */
    public SimulationMainWindow(ProductionSite productionSite) {
        element = new ArrayList<>();

        // PipeDrawers are first stored in a separate list to make sure they are at the end
        // of the element list, as they need to draw over other Drawers
        List<Drawer> pipes = new ArrayList<>();

        int tankCount = TankSelector.getUpperTankCount();

        // get all the tanks from the productionSite
        MixTank mix = productionSite.getMixTank();
        double mixXPos = ((double) (tankCount - 1)) / (tankCount * 2);
        //Assuming there are only ever two rows of tanks
        double mixYPos = 0.5;
        Point2D mixPos = new Point2D(mixXPos, mixYPos);
        MixTankDrawer mtDrawer = new MixTankDrawer(mixPos, mix, ROWS, tankCount);
        element.add(mtDrawer);

        //Add a pipe leading out of the mixTank.
        Point2D mtStart = mtDrawer.getPipeStartPoint();
        Point2D mtEnd = mtDrawer.getPipeBotExit();
        Point2D[] mtWayPoints = {mtStart, mtEnd};
        PipeDrawer mtPipe = new PipeDrawer(mtWayPoints, mix.getOutPipe(), TankSelector.getUpperTankCount());
        pipes.add(mtPipe);

        TankSelector[] topTanks = TankSelector.valuesWithoutMix();

        for (int i = 0; i < tankCount; i++) {
            //Add a new TankDrawer
            double xPos = i * (1.0 / tankCount);
            Point2D position = new Point2D(xPos, 0.0);
            Tank tank = productionSite.getUpperTank(topTanks[i]);
            TankDrawer tankDrawer = new TankDrawer(position, tank, ROWS, tankCount);
            element.add(tankDrawer);

            //Create a pipe leading from the top to the tank
            Point2D topEnd = tankDrawer.getPipeEndPoint(1, 1);
            Point2D topStart = tankDrawer.getPipeTopEntry();
            Point2D[] topWayPoints = {topStart, topEnd};
            pipes.add(new PipeDrawer(topWayPoints, tank.getInPipe(), 3));

            //Create a pipe leading from the tank to the mixTank
            Point2D botStart = tankDrawer.getPipeStartPoint();
            Point2D botEnd = mtDrawer.getPipeEndPoint(i + 1, TankSelector.getUpperTankCount());
            Point2D botMid1 = new Point2D(botStart.getX(), 0.5);
            Point2D botMid2 = new Point2D(botEnd.getX(), 0.5);
            Point2D[] botWayPoints = {botStart, botMid1, botMid2, botEnd};
            pipes.add(new PipeDrawer(botWayPoints, tank.getOutPipe(), 3));

        }

        element.addAll(pipes);
    }

    /**
     * The stage that is provided by JavaFx
     * @param primaryStage The stage to draw the window on
     */
    public final void start(Stage primaryStage) {
        primaryStage.setTitle(Translator.getInstance().getString("simulation.title"));

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        // Get screen dimensions of the primary screen (minus taskbars etc)
        Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();

        // Set the dimensions of Stage and Canvas to the screen size
        primaryStage.setX(screenDimensions.getMinX());
        primaryStage.setY(screenDimensions.getMinY());
        primaryStage.setWidth(screenDimensions.getWidth());
        primaryStage.setHeight(screenDimensions.getHeight());

        setResizeListeners(primaryStage);

        makeLayOut(primaryStage);

        primaryStage.show();
    }

    private void makeLayOut(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();

        //TODO: figure out a constant for this
        mainPane.setStyle("-fx-font-size: 10px;");

        menu = new SimulationMenu();
        mainPane.setTop(menu);

        canvas = setCanvas(primaryStage);

        mainPane.setCenter(canvas);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
    }

    private Canvas setCanvas(Stage primaryStage) {
        Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();

        double totalWidth = screenDimensions.getWidth();
        double totalHeight = screenDimensions.getHeight();

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        canvas = new Canvas(totalWidth, totalHeight);
        root.getChildren().add(canvas);

        GraphicsContext context = canvas.getGraphicsContext2D();

        setResizeListeners(primaryStage);

        new AnimationTimer() {
            public void handle(long currentTime) {
                context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (Drawer d : element) {
                    d.draw(context, currentTime);
                }
            }
        }.start();

        return canvas;
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
    public final void setControlButtonHandler(AbstractMenuControlButton controlButtonHandler) {
        menu.setControlButtonHandler(controlButtonHandler);
    }

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public final void setSettingsButtonHandler(AbstractMenuSettingsButton settingsButtonHandler) {
        menu.setSettingsButtonHandler(settingsButtonHandler);
    }

    /**
     * Sets the handler for pressing the about entry in the menu
     * @param aboutButtonHandler The handler to be called when the about button is pressed
     */
    public final void setAboutButtonHandler(AbstractMenuAboutButton aboutButtonHandler) {
        menu.setAboutButtonHandler(aboutButtonHandler);
    }

    /**
     * Sets the handler for pressing the help entry in the menu
     * @param helpButtonHandler The handler to be called when the help button is pressed
     */
    public final void setHelpButtonHandler(AbstractMenuHelpButton helpButtonHandler) {
        menu.setHelpButtonHandler(helpButtonHandler);
    }

    /**
     * This method creates two ChangeListeners that keep track of the window width and height
     * and, if it changes, change the canvas size accordingly
     */
    private void setResizeListeners(Stage primaryStage) {

        final ChangeListener<Number> listener = new ChangeListener<Number>() {
            final Timer timer = new Timer();
            TimerTask task = null;
            final long delayTime = 50;

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                if (task != null) { // there was already a task scheduled from the previous operation
                    task.cancel(); // cancel it, we have a new size to consider
                }

                task = new TimerTask() {
                    @Override
                    public void run() {
                        synchronized (canvas) {

                            double newWidth = primaryStage.getWidth();
                            double newHeight = primaryStage.getHeight() - 50;

                            canvas.setWidth(newWidth);
                            canvas.setHeight(newHeight);
                            canvas.getGraphicsContext2D().clearRect(0, 0, newWidth, newHeight);
                        }
                    }
                };

                timer.schedule(task, delayTime);
            }
        };

        primaryStage.widthProperty().addListener(listener);
        primaryStage.heightProperty().addListener(listener);
    }
}
