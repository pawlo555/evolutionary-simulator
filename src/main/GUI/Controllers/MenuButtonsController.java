package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.JungleMap;
import main.SimulationSettings;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import GUI.*;

public class MenuButtonsController {

    public Button startButton;
    public Button basicParams;

    @FXML private TextField width;
    public TextField height;
    public TextField jungleRatio;
    public TextField animalStartingEnergy;
    public TextField energyLossPerDay;
    public TextField energyToBred;
    public TextField energyPerGrass;
    public TextField animalsOnStart;
    public TextField numberOfSimulations;

    private SimulationSettings simulationSettings;
    private Simulation simulation;

    @FXML
    public void StartPressed(ActionEvent event) {
        try {
            simulationSettings.setValue("width", width.getText());
            simulationSettings.setValue("height", height.getText());
            simulationSettings.setValue("jungleRatio", jungleRatio.getText());
            simulationSettings.setValue("startEnergy", animalStartingEnergy.getText());
            simulationSettings.setValue("energyPerEpoch", energyLossPerDay.getText());
            simulationSettings.setValue("energyToBred", energyToBred.getText());
            simulationSettings.setValue("energyPerGrass", energyPerGrass.getText());
            simulationSettings.setValue("animalsOnStart", animalsOnStart.getText());
            simulationSettings.setValue("numberOfSimulations", numberOfSimulations.getText());
            simulationSettings.CheckParameters();
            simulation.LoadNextScene();
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage() + exception.getCause());
        }
    }

    public void loadBasicSettings() throws IOException, ParseException {
        this.simulationSettings = new SimulationSettings();
        width.setText(simulationSettings.getValue("width"));
        height.setText(simulationSettings.getValue("height"));
        jungleRatio.setText(simulationSettings.getValue("jungleRatio"));
        animalStartingEnergy.setText(simulationSettings.getValue("startEnergy"));
        energyLossPerDay.setText(simulationSettings.getValue("energyPerEpoch"));
        energyToBred.setText(simulationSettings.getValue("energyToBred"));
        energyPerGrass.setText(simulationSettings.getValue("energyPerGrass"));
        animalsOnStart.setText(simulationSettings.getValue("animalsOnStart"));
        numberOfSimulations.setText(simulationSettings.getValue("numberOfSimulations"));

    }

    public void initialize() throws IOException, ParseException {
        loadBasicSettings();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public SimulationSettings getSettings() {
        return simulationSettings;
    }
}
