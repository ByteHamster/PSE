package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.controller.SimulationViewInterface;
import edu.kit.pse.osip.simulation.view.dialogs.OverflowDialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The main window for visualizing the OSIP simulation.
 * It regularly updates itself with current information from the model and processes an update() method for alarms.
 * If an overflow occurs in the model it is be displayed by an overlay.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class SimulationMainWindow implements SimulationViewInterface {
    /**
     * The minimum window size.
     */
    private static final double MIN_WINDOW_SIZE = 300;
    /**
     * The preferred width for the window.
     */
    private static final double WINDOW_WIDTH = 700;
    /**
     * The preferred height for the window.
     */
    private static final double WINDOW_HEIGHT = 800;
    /**
     * The size of the progress indicator.
     */
    private static final double PROGRESS_INDICATOR_SIZE = 100;

    /**
     * It is assumed, that the tanks and mixtank are always ordered in two rows.
     */
    private static final int ROWS = 2;

    /**
     * The menubar.
     */
    private SimulationMenu menu;
    /**
     * Collection of all drawers of the simulation.
     */
    private Collection<Drawer> element;
    /**
     * The canvas in which the simulation is drawn.
     */
    private Canvas canvas;
    /**
     * Handler for the reset button.
     */
    private EventHandler<ActionEvent> resetHandler;
    /**
     * Label for indicating if a scenario is running.
     */
    private Label scenarioLabel;
    /**
     * The stage on which everything is shown.
     */
    private Stage stage;
    /**
     * The main scene showing the simulation.
     */
    private Scene mainScene;
    /**
     * Scene that shows the progress indicator.
     */
    private Scene progressScene;

    /**
     * Creates a new SimulationMainWindow. It is assumed that there will only ever be two rows of tanks.
     * 
     * @param productionSite The ProductionSite object, so that the view can access the model.
     */
    public SimulationMainWindow(ProductionSite productionSite) {
        element = new ArrayList<>();

        // PipeDrawers are first stored in a separate list to make sure they are at the end
        // of the element list, as they need to draw over other Drawers
        List<Drawer> pipes = new ArrayList<>();

        // get all the tanks from the productionSite
        double mixXPos = ((double) (TankSelector.getUpperTankCount() - 1)) / (TankSelector.getUpperTankCount() * 2);
        //Assuming there are only ever two rows of tanks
        double mixYPos = 0.5;
        Point2D mixPos = new Point2D(mixXPos, mixYPos);
        MixTankDrawer mtDrawer = new MixTankDrawer(mixPos, productionSite.getMixTank(),
                ROWS, TankSelector.getUpperTankCount());
        element.add(mtDrawer);

        //Add a pipe leading out of the mixTank.
        pipes.add(createMixTankOutPipe(productionSite.getMixTank(), mtDrawer));

        TankSelector[] topTanks = TankSelector.valuesWithoutMix();

        for (int i = 0; i < TankSelector.getUpperTankCount(); i++) {
            //Add a new TankDrawer
            double xPos = i * (1.0 / TankSelector.getUpperTankCount());
            Point2D position = new Point2D(xPos, 0.0);
            Tank tank = productionSite.getUpperTank(topTanks[i]);
            TankDrawer tankDrawer = new TankDrawer(position, tank, ROWS, TankSelector.getUpperTankCount());
            element.add(tankDrawer);

            //Create a pipe leading from the top to the tank
            pipes.add(createTopPipe(tank, tankDrawer));

            //Create a pipe leading from the tank to the mixTank
            double middleI = (double) (TankSelector.getUpperTankCount() - 1) / 2;
            int offset = (int) Math.ceil(Math.abs((middleI - i)));
            pipes.add(createMixPipe(tank, tankDrawer, mtDrawer, i + 1, offset));
        }

        element.addAll(pipes);
    }

    /**
     * Creates a PipeDrawer entering the simulation and leading to the TanKDrawer.
     */
    private PipeDrawer createTopPipe(Tank tank, TankDrawer tankDrawer) {
        Point2D topEnd = tankDrawer.getPipeEndPoint(1, 1);
        Point2D topStart = tankDrawer.getPipeTopEntry();
        Point2D[] topWayPoints = {topStart, topEnd};
        return new PipeDrawer(topWayPoints, tank.getInPipe(), ROWS);
    }

    /**
     * Creates a PipeDrawer leading from the TankDrawer to the MixTankDrawer.
     */
    private PipeDrawer createMixPipe(Tank tank, TankDrawer tankDrawer, MixTankDrawer mixTankDrawer,
                                     int pipeNum, int offset) {
        Point2D botStart = tankDrawer.getPipeStartPoint();
        Point2D botEnd = mixTankDrawer.getPipeEndPoint(pipeNum, TankSelector.getUpperTankCount());
        double midY = botStart.getY()
                + (ViewConstants.OUTBOX_PERCENT - ViewConstants.INBOX_HEIGHT - ViewConstants.OVAL_PERCENT)
                / ROWS / 2 + 2.5 * offset * ViewConstants.PIPE_WIDTH;
        Point2D botMid1 = new Point2D(botStart.getX(), midY);
        Point2D botMid2 = new Point2D(botEnd.getX(), midY);
        Point2D[] botWayPoints = {botStart, botMid1, botMid2, botEnd};
        return new PipeDrawer(botWayPoints, tank.getOutPipe(),  ROWS);
    }

    /**
     * Creates a PipeDrawer leaving the MixTankDrawer and exiting the simulation.
     */
    private PipeDrawer createMixTankOutPipe(MixTank mixTank, MixTankDrawer mixTankDrawer) {
        Point2D mtStart = mixTankDrawer.getPipeStartPoint();
        Point2D mtEnd = mixTankDrawer.getPipeBotExit();
        Point2D[] mtWayPoints = {mtStart, mtEnd};
        return new PipeDrawer(mtWayPoints, mixTank.getOutPipe(), ROWS);
    }

    @Override
    public final void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle(Translator.getInstance().getString("simulation.title"));
        primaryStage.getIcons().add(new Image("/icon.png"));

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setMinWidth(MIN_WINDOW_SIZE);
        primaryStage.setMinHeight(MIN_WINDOW_SIZE);
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> Platform.exit());

        makeLayout(primaryStage);
        primaryStage.show();
    }

    /**
     * Makes the layout.
     * 
     * @param primaryStage the stage on which the layout is shown.
     */
    private void makeLayout(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();

        mainPane.setStyle("-fx-font-size:" + ViewConstants.FONT_SIZE + "px;");

        menu = new SimulationMenu(primaryStage);
        mainPane.setTop(menu);

        canvas = createCanvas();
        mainPane.setCenter(canvas);

        scenarioLabel = new Label(Translator.getInstance().getString("simulation.view.noScenario"));
        scenarioLabel.setFont(Font.font("Arial", ViewConstants.FONT_SIZE * 1.5));
        HBox labelBox = new HBox(10);
        labelBox.getChildren().add(scenarioLabel);
        labelBox.setStyle("-fx-background-color: #D3D3D3");
        mainPane.setBottom(labelBox);
        labelBox.setPadding(new Insets(2, 2, 2, 2));

        mainScene = new Scene(mainPane);

        ProgressIndicator progressIndicator = new ProgressIndicator(-1);
        progressIndicator.setMaxSize(PROGRESS_INDICATOR_SIZE, PROGRESS_INDICATOR_SIZE);
        VBox box = new VBox();
        box.setSpacing(PROGRESS_INDICATOR_SIZE / 2);
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(progressIndicator);
        box.getChildren().add(new Label(Translator.getInstance().getString("simulation.wait")));
        progressScene = new Scene(box);

        primaryStage.setScene(progressScene);
    }

    /**
     * Creates the canvas on which the simulation is drawn.
     * 
     * @return the generated canvas.
     */
    private Canvas createCanvas() {
        canvas = new ResizableCanvas();

        GraphicsContext context = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            private long oldTime = System.nanoTime();

            public void handle(long currentTimeNs) {
                long timeDiffNs = currentTimeNs - oldTime;
                
                // The canvas is only drawn after at least 25 ms. This is equals to approximately 40 FPS.
                if (timeDiffNs < 25000000) {
                    return;
                }
                
                oldTime = currentTimeNs;
                double timeDiff = ((double) timeDiffNs) / (1000000000.0 * 60);

                context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (Drawer d : element) {
                    d.draw(context, timeDiff);
                }
            }
        }.start();

        return canvas;
    }

    @Override
    public void showOverflow(AbstractTank tank) {
        OverflowDialog dialog = new OverflowDialog(tank);
        dialog.initModality(Modality.APPLICATION_MODAL);
        EventHandler<ActionEvent> handler = actionEvent -> {
            if (resetHandler != null) {
                resetHandler.handle(actionEvent);
            }
        };

        dialog.setResetButtonHandler(handler);
        dialog.show();
    }

    @Override
    public final void setControlButtonHandler(EventHandler<ActionEvent> controlButtonHandler) {
        menu.setControlButtonHandler(controlButtonHandler);
    }

    @Override
    public void setScenarioStartListener(Consumer<String> listener) {
        menu.setScenarioStartListener(listener);
    }

    @Override
    public void setScenarioStopListener(Runnable listener) {
        menu.setScenarioStopListener(listener);
    }

    @Override
    public void showScenarioError(String error) {
        Translator t = Translator.getInstance();

        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(t.getString("simulation.view.scenario.error.title"));
        errorDialog.setHeaderText(t.getString("simulation.view.scenario.error.header"));
        errorDialog.setContentText(error);
        errorDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorDialog.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) errorDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icon.png"));
        stage.setAlwaysOnTop(true);
        errorDialog.show();
    }

    @Override
    public void scenarioFinished() {
        menu.setScenarioFinished();
        Platform.runLater(()->
                scenarioLabel.setText(Translator.getInstance().getString("simulation.view.noScenario")));
    }

    @Override
    public void scenarioStarted() {
        scenarioLabel.setText(Translator.getInstance().getString("simulation.view.showScenario"));
    }

    @Override
    public void showOPCUAServerError(String message) {
        Platform.runLater(() -> {
            Translator t = Translator.getInstance();

            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle(t.getString("simulation.view.opcua.error.title"));
            errorDialog.setHeaderText(t.getString("simulation.view.opcua.error.header"));
            errorDialog.setContentText(message);
            errorDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorDialog.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) errorDialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/icon.png"));
            stage.setAlwaysOnTop(true);
            errorDialog.show();
        });
    }

    @Override
    public void setProgressIndicatorVisible(boolean visible) {
        Platform.runLater(() -> stage.setScene(visible ? progressScene : mainScene));
    }

    @Override
    public final void setSettingsButtonHandler(EventHandler<ActionEvent> settingsButtonHandler) {
        menu.setSettingsButtonHandler(settingsButtonHandler);
    }

    @Override
    public final void setAboutButtonHandler(EventHandler<ActionEvent> aboutButtonHandler) {
        menu.setAboutButtonHandler(aboutButtonHandler);
    }

    @Override
    public final void setHelpButtonHandler(EventHandler<ActionEvent> helpButtonHandler) {
        menu.setHelpButtonHandler(helpButtonHandler);
    }

    @Override
    public void setResetListener(EventHandler<ActionEvent> listener) {
        resetHandler = listener;
        menu.setResetButtonHandler(listener);
    }

    /**
     * Canvas that adapts width and height of the parent.
     * 
     * Source: http://stackoverflow.com/a/34264033/
     */
    private class ResizableCanvas extends Canvas {
        @Override
        public double minHeight(double width) {
            return 0;
        }

        @Override
        public double maxHeight(double width) {
            return 10000;
        }

        @Override
        public double minWidth(double height) {
            return 0;
        }

        @Override
        public double maxWidth(double height) {
            return 10000;
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public void resize(double width, double height) {
            setWidth(width);
            setHeight(height);
        }
    }
}
