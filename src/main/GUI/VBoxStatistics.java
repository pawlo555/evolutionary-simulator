package GUI;

import GUI.Controllers.AnimalStatisticsController;
import GUI.Controllers.MapStatisticsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import Statistics.AnimalStatistics;
import Statistics.WorldStatistics;

import java.io.IOException;

public class VBoxStatistics extends VBox {

    private final MapStatisticsController mapController;
    private final AnimalStatisticsController animalController;

    public VBoxStatistics(WorldStatistics worldStatistics, AnimalStatistics animalStatistics) throws IOException {
        FXMLLoader mapLoader = new FXMLLoader();
        mapLoader.setLocation(this.getClass().getResource("fxml/MapStatistics.fxml"));
        VBox vboxMapStats = mapLoader.load();

        mapController = mapLoader.getController();
        mapController.setMapStatistics(worldStatistics);

        FXMLLoader animalLoader = new FXMLLoader();
        animalLoader.setLocation(this.getClass().getResource("fxml/AnimalStatistics.fxml"));

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
