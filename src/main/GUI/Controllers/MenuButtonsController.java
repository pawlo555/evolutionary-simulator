package GUI.Controllers;

import GUI.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import SimulationClasses.SimulationSettings;

public class MenuButtonsController {

    public Button startButton;
    public Button basicParams;

    @FXML private TextField width;
    public TextField height;
    public TextField jungleRatio;
    public TextField animalStartingEnergy;
    public TextField energyLossPerDay;
    public TextField energyToBreed;
    public TextField energyPerGrass;
    public TextField animalsOnStart;
    public TextField numberOfSimulations;

    private SimulationSettings simulationSettings;
    private Simulation simulation;

    @FXML
    public void StartPressed() {
        try {
            simulationSettings.setValue("width", width.getText());
            simulationSettings.setValue("height", height.getText());
            simulationSettings.setValue("jungleRatio", jungleRatio.getText());
            simulationSettings.setValue("startEnergy", animalStartingEnergy.getText());
            simulationSettings.setValue("energyPerEpoch", energyLossPerDay.getText());
            simulationSettings.setValue("energyToBreed", energyToBreed.getText());
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

    public void loadBasicSettings() {
        this.simulationSettings = new SimulationSettings();
        width.setText(simulationSettings.getValue("width"));
        height.setText(simulationSettings.getValue("height"));
        jungleRatio.setText(simulationSettings.getValue("jungleRatio"));
        animalStartingEnergy.setText(simulationSettings.getValue("startEnergy"));
        energyLossPerDay.setText(simulationSettings.getValue("energyPerEpoch"));
        energyToBreed.setText(simulationSettings.getValue("energyToBreed"));
        energyPerGrass.setText(simulationSettings.getValue("energyPerGrass"));
        animalsOnStart.setText(simulationSettings.getValue("animalsOnStart"));
        numberOfSimulations.setText(simulationSettings.getValue("numberOfSimulations"));
    }

    public void initialize() {
        loadBasicSettings();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public SimulationSettings getSettings() {
        return simulationSettings;
    }
}
