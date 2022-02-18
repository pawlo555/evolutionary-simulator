package gui;

import gui.controllers.AnimalStatisticsController;
import gui.controllers.MapStatisticsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import statistics.AnimalStatistics;
import statistics.WorldStatistics;

import java.io.IOException;

public class VBoxStatistics extends VBox {

    private final MapStatisticsController mapController;
    private final AnimalStatisticsController animalController;

    public VBoxStatistics(WorldStatistics worldStatistics, AnimalStatistics animalStatistics) throws IOException {
        FXMLLoader mapLoader = new FXMLLoader();
        mapLoader.setLocation(this.getClass().getResource("fxml/mapStatistics.fxml"));
        VBox vboxMapStats = mapLoader.load();

        mapController = mapLoader.getController();
        mapController.setMapStatistics(worldStatistics);

        FXMLLoader animalLoader = new FXMLLoader();
        animalLoader.setLocation(this.getClass().getResource("fxml/animalStatistics.fxml"));

        VBox vboxAnimalStats = animalLoader.load();

        animalController = animalLoader.getController();
        animalController.setAnimalStatistics(animalStatistics);

        this.getChildren().add(vboxMapStats);
        this.getChildren().add(vboxAnimalStats);
    }

    public MapStatisticsController getMapController() {
        return mapController;
    }

    public AnimalStatisticsController getAnimalController() {
        return animalController;
    }
}
