package GUI;

import GUI.Controllers.AnimalStatisticsController;
import GUI.Controllers.MapStatisticsController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.*;

import java.io.IOException;

public class SimulationElements {

    private final VBoxStatistics vBoxStatistics;
    private final MapVBox mapVBox;
    private final SimulationEngine engine;
    private final JungleMap map;
    private final AnimalStatistics animalStatistics;
    private final AnimalStatisticsController animalStatisticsController;

    public SimulationElements(SimulationSettings settings) throws IOException {
        WorldStatistics worldStatistics = new WorldStatistics();
        map = new JungleMap(settings, worldStatistics);
        engine = new SimulationEngine(map);

        MapVisualizer mapVisualizer = new MapVisualizer(map, worldStatistics);
        mapVisualizer.Visualize();
        mapVBox = new MapVBox(mapVisualizer);
        animalStatistics = new AnimalStatistics(engine);

        vBoxStatistics = new VBoxStatistics(worldStatistics, animalStatistics);
        MapStatisticsController mapStatisticsController = vBoxStatistics.getMapController();
        animalStatisticsController = vBoxStatistics.getAnimalController();

        animalStatisticsController.setAnimalStatistics(animalStatistics);

        mapVisualizer.Visualize();
        engine.addObserver(mapVisualizer);
        engine.addObserver(mapStatisticsController);
        engine.addObserver(animalStatisticsController);

        mapVisualizer.setOnMouseClicked(mapClicked(mapVisualizer));
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    public VBoxStatistics getVBoxStatistics() {
        return vBoxStatistics;
    }

    public MapVBox getMapVBox() {
        return mapVBox;
    }

    public EventHandler<MouseEvent> mapClicked(MapVisualizer mapVisualizer) {
        return e -> {
            Vector2d clickedField = mapVisualizer.getMapPosition(e.getX(),e.getY());
            if (map.getMapField(clickedField).containsAnimals()) {
                Animal followedAnimal = map.getMapField(clickedField).getTopAnimal();
                animalStatistics.changeAnimal(followedAnimal);
                animalStatisticsController.setAnimalStatistics(animalStatistics);
                mapVisualizer.setFollowedAnimal(followedAnimal);
                mapVisualizer.Visualize();
            }
        };
    }

}