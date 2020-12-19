package GUI;

import GUI.Controllers.MapButtonsController;
import GUI.Controllers.MenuButtonsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.*;
import java.io.IOException;

public class Simulation extends Application {

    private MenuButtonsController menuController;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(this.getClass().getResource("fxml/menuOptions.fxml"));
        VBox vbox = loader1.load();
        menuController = loader1.getController();
        Scene scene1 = new Scene(vbox);

        menuController.setSimulation(this);

        scene1.getStylesheets().add(String.valueOf(getClass().getResource("styles/styleSheet.css")));
        stage.setScene(scene1);
        stage.show();
    }

    public void LoadNextScene() throws IOException {
        SimulationSettings settings = this.menuController.getSettings();
        System.out.println(settings.getValue("numberOfSimulations"));
        Scene newScene;
        if (settings.getValue("numberOfSimulations").equals("1")) {
            newScene = nextSceneWithOneMap(settings);
        } else {
            newScene = nextSceneWithTwoMaps(settings);
        }

        newScene.getStylesheets().add(String.valueOf(getClass().getResource("styles/SimulationStyleSheet.css")));
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setY(0);
    }

    public HBox loadSimulationButtons(SimulationEngine firstEngine, SimulationEngine secondEngine) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(this.getClass().getResource("fxml/SimulationButtons.fxml"));
        HBox hBoxButtons = buttonsLoader.load();
        MapButtonsController mapButtonsController = buttonsLoader.getController();

        firstEngine.addObserver(mapButtonsController);
        if (secondEngine != null)
            secondEngine.addObserver(mapButtonsController);

        mapButtonsController.setFirstEngine(firstEngine);
        mapButtonsController.setSecondEngine(secondEngine);
        mapButtonsController.setTimeline();

        return hBoxButtons;
    }

    public Scene nextSceneWithOneMap(SimulationSettings settings) throws IOException {

        SimulationElements simulationElements = new SimulationElements(settings);
        MapVBox mapVBox = simulationElements.getMapVBox();
        SimulationEngine engine = simulationElements.getEngine();
        VBoxStatistics vBoxStats = simulationElements.getVBoxStatistics();

        HBox hBoxButtons = loadSimulationButtons(engine, null);

        VBox vBoxMaps = new VBox(mapVBox, hBoxButtons);

        HBox hBox2 = new HBox(vBoxMaps, vBoxStats);
        return new Scene(hBox2);
    }

    public Scene nextSceneWithTwoMaps(SimulationSettings settings) throws IOException {
        SimulationElements firstSimulationElements = new SimulationElements(settings);
        MapVBox firstMapVBox = firstSimulationElements.getMapVBox();
        SimulationEngine firstEngine = firstSimulationElements.getEngine();
        VBoxStatistics firstVBoxStats = firstSimulationElements.getVBoxStatistics();

        SimulationElements secondSimulationElements = new SimulationElements(settings);
        MapVBox secondMapVBox = secondSimulationElements.getMapVBox();
        SimulationEngine secondEngine = secondSimulationElements.getEngine();
        VBoxStatistics secondVBoxStats = secondSimulationElements.getVBoxStatistics();

        HBox hBoxButtons = loadSimulationButtons(firstEngine, secondEngine);

        VBox vBoxMaps = new VBox(firstMapVBox, hBoxButtons, secondMapVBox);
        VBox allStats = new VBox(firstVBoxStats,secondVBoxStats);

        HBox hBox2 = new HBox(vBoxMaps, allStats);
        return new Scene(hBox2);
    }

}