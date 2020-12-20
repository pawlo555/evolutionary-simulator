package GUI.Controllers;

import Observers.ISimulationObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import Statistics.AnimalStatistics;

public class AnimalStatisticsController implements ISimulationObserver {

    private AnimalStatistics animalStatistics;

    @FXML private Label numberOfChildren;
    @FXML private Label numberOfDescendants;
    @FXML private Label dieEpoch;
    @FXML private Label id;
    @FXML private Label genome;
    @FXML private Label energy;

    private void updateNumberOfChildren() {
        numberOfChildren.setText(Integer.toString(animalStatistics.getChildrenAmount()));
    }

    private void updateNumberOfDescendants() {
        numberOfDescendants.setText(Integer.toString(animalStatistics.getDescendantsAmount()));
    }
    private void updateDieEpoch() {
        dieEpoch.setText(animalStatistics.getDieEpoch());
    }

    private void updateEnergy() {
        energy.setText(Integer.toString(animalStatistics.getEnergy()));
    }

    @Override
    public void nextEpochRendered() {
        updateNumberOfChildren();
        updateNumberOfDescendants();
        updateDieEpoch();
        updateEnergy();
    }

    public void setAnimalStatistics(AnimalStatistics animalStatistics) {
        this.animalStatistics = animalStatistics;
        id.setText(Integer.toString(animalStatistics.getId()));
        genome.setText(animalStatistics.getGenome());
        updateEnergy();
    }
}
