package gui;

import gui.controllers.AnimalStatisticsController;
import gui.controllers.MapStatisticsController;
import simulationClasses.Animal;
import simulationClasses.JungleMap;
import simulationClasses.SimulationEngine;
import simulationClasses.SimulationSettings;
import statistics.AnimalStatistics;
import statistics.WorldStatistics;
import utilities.Vector2d;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SimulationElements {

    private final VBoxStatistics vBoxStatistics;
    private final MapVBox mapVBox;
    private final SimulationEngine engine;
    private final JungleMap map;
    private final AnimalStatistics animalStatistics;
    private final AnimalStatisticsController animalStatisticsController;
    private final WorldStatistics worldStatistics;

    public SimulationElements(SimulationSettings settings) throws IOException {
        worldStatistics = new WorldStatistics();
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

    public WorldStatistics getWorldStatistics() {
        return worldStatistics;
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
