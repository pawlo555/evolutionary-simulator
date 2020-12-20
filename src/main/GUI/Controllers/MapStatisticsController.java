package GUI.Controllers;

import Observers.ISimulationObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import Statistics.WorldStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class MapStatisticsController implements ISimulationObserver {

    @FXML private Label nowGrass;
    @FXML private Label maxGrass;
    @FXML private Label nowAnimals;
    @FXML private Label maxAnimals;
    @FXML private Label dominatingGenome;
    @FXML private Label topGenomeAnimals;
    @FXML private ListView animalsWithTopGenome;
    @FXML private Label averageEnergy;
    @FXML private Label averageChildren;
    @FXML private Label averageLiveLength;

    private WorldStatistics mapStats;

    @Override
    public void nextEpochRendered() {
        updateNowGrass();
        updateMaxGrass();
        updateNowAnimals();
        updateMaxAnimals();
        updateDominatingGenome();
        updateAnimalsWithTopGenome();
        updateAverageEnergy();
        updateAverageChildren();
        updateAverageLiveLength();
        updateTopGenomeAnimals();
    }

    private void updateNowGrass() {
        nowGrass.setText(Integer.toString(mapStats.getCurrentGrassAmount()));
    }

    private void updateMaxGrass() {
        maxGrass.setText(Integer.toString(mapStats.getMaxGrassAmount()));
    }

    private void updateNowAnimals() {
        nowAnimals.setText(Integer.toString(mapStats.getCurrentAnimalsAmount()));
    }

    private void updateMaxAnimals() {
        maxAnimals.setText(Integer.toString(mapStats.getMaxAnimalsAmount()));
    }

    private void updateDominatingGenome() {
        dominatingGenome.setText(mapStats.getDominatingGenome());
    }

    private void updateTopGenomeAnimals() {
        topGenomeAnimals.setText(Integer.toString(mapStats.getNumberOfAnimalsWithDominatingGenome()));
    }

    private void updateAnimalsWithTopGenome() {
        animalsWithTopGenome.getItems().clear();
        List<Integer> idsList = mapStats.getIdsOfAnimalsWithDominatingGenome();
        if (idsList != null) {
            idsList = idsList.stream().sorted().collect(Collectors.toList());
            animalsWithTopGenome.getItems().addAll(idsList);
        }
    }

    private void updateAverageEnergy() {
        averageEnergy.setText(String.format("%.2f", mapStats.getAverageAnimalEnergy()));
    }

    private void updateAverageChildren() {
        averageChildren.setText( String.format("%.2f", mapStats.getAverageNumberOfChildren()));
    }

    private void updateAverageLiveLength() {
        averageLiveLength.setText(String.format("%.2f",mapStats.getAverageLiveLength()));
    }

    public void setMapStatistics(WorldStatistics mapStats) {
        this.mapStats = mapStats;
    }


}
