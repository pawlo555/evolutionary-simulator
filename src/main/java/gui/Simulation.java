package gui;

import gui.controllers.MapButtonsController;
import gui.controllers.MenuButtonsController;
import simulationClasses.SimulationEngine;
import simulationClasses.SimulationSettings;
import statistics.WorldStatistics;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        FXMLLoader loader1 = new FXMLLoader(this.getClass().getResource("fxml/menuOptions.fxml"));
        System.out.println(this.getClass().getResource("fxml/menuOption.fxml"));
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
        Scene newScene;
        if (settings.getValue("numberOfSimulations").equals("1")) {
            newScene = nextSceneWithOneMap(settings);
        } else {
            newScene = nextSceneWithTwoMaps(settings);
        }

        newScene.getStylesheets().add(String.valueOf(getClass().getResource("styles/simulationStyleSheet.css")));
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setY(0);
    }

    public HBox loadSimulationButtons(SimulationElements firstSimulationElements,
                                      SimulationElements secondSimulationElements) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(this.getClass().getResource("fxml/mapButtons.fxml"));
        HBox hBoxButtons = buttonsLoader.load();
        MapButtonsController mapButtonsController = buttonsLoader.getController();
        SimulationEngine firstEngine = firstSimulationElements.getEngine();
        firstEngine.addObserver(mapButtonsController);
        mapButtonsController.setFirstEngine(firstEngine);

        WorldStatistics worldStatistics1 = firstSimulationElements.getWorldStatistics();
        WorldStatistics worldStatistics2 = null;
        if (secondSimulationElements != null) {
            SimulationEngine secondEngine = secondSimulationElements.getEngine();
            mapButtonsController.setSecondEngine(secondEngine);
            worldStatistics2 = secondSimulationElements.getWorldStatistics();
            secondEngine.addObserver(mapButtonsController);
        }

        mapButtonsController.setStatisticsSavers(worldStatistics1, worldStatistics2);
        mapButtonsController.setTimeline();

        return hBoxButtons;
    }

    public Scene nextSceneWithOneMap(SimulationSettings settings) throws IOException {
        SimulationElements simulationElements = new SimulationElements(settings);
        MapVBox mapVBox = simulationElements.getMapVBox();
        VBoxStatistics vBoxStats = simulationElements.getVBoxStatistics();

        HBox hBoxButtons = loadSimulationButtons(simulationElements, null);

        VBox vBoxMaps = new VBox(mapVBox, hBoxButtons);

        HBox hBox2 = new HBox(vBoxMaps, vBoxStats);
        return new Scene(hBox2);
    }

    public Scene nextSceneWithTwoMaps(SimulationSettings settings) throws IOException {

        SimulationElements firstSimulationElements = new SimulationElements(settings);
        MapVBox firstMapVBox = firstSimulationElements.getMapVBox();
        VBoxStatistics firstVBoxStats = firstSimulationElements.getVBoxStatistics();

        SimulationElements secondSimulationElements = new SimulationElements(settings);
        MapVBox secondMapVBox = secondSimulationElements.getMapVBox();
        VBoxStatistics secondVBoxStats = secondSimulationElements.getVBoxStatistics();

        HBox hBoxButtons = loadSimulationButtons(firstSimulationElements, secondSimulationElements);

        VBox vBoxMaps = new VBox(firstMapVBox, hBoxButtons, secondMapVBox);
        VBox allStats = new VBox(firstVBoxStats,secondVBoxStats);

        HBox hBox2 = new HBox(vBoxMaps, allStats);
        return new Scene(hBox2);
    }
}