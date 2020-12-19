package main;

import GUI.ISimulationObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimulationEngine {

    private int currentEpoch = 0;
    private final JungleMap map;
    private final SimulationSettings simulationSettings;
    private final HashMap<Integer, Animal> animalsOnMap = new HashMap<>();

    private final List<ISimulationObserver> observers = new ArrayList<>();

    public SimulationEngine(JungleMap map) {
        this.map = map;
        map.setSimulationEngine(this);
        this.simulationSettings = map.getSimulationSettings();
        startSimulation();
    }

    public void startSimulation() {
        int animalsToSpawn = Integer.parseInt(simulationSettings.getValue("animalsOnStart"));
        for (int i=0; i<animalsToSpawn; i++) {
            Vector2d position = map.randField(map.getWidth(), map.getHeight());
            if (!map.getMapField(position).containsAnimals()) {
                Animal spawnAnimal = new Animal(position,map);
                animalsOnMap.put(spawnAnimal.getId(),spawnAnimal);
            }
            else
                i--;
        }
    }

    public void nextEpoch() {
        removeDiedAnimals();
        moveAnimals();
        grassEating();
        animalsBreed();
        map.spawnGrass();
        currentEpoch++;
        notifyObservers();
        animalsLossEnergy(); // we dont want have Animals with 0 energy before notifying observers
    }

    private void animalsLossEnergy() {
        int energyCost = Integer.parseInt(
                map.getSimulationSettings().getValue("energyPerEpoch"));
        for (int iD: this.animalsOnMap.keySet()) {
            animalsOnMap.get(iD).EpochSurvived(energyCost);
        }
    }

    private void removeDiedAnimals() {
        List<Integer> diedAnimalIDs = new ArrayList<>();
        for (int iD: this.animalsOnMap.keySet()) {
            if (animalsOnMap.get(iD).getEnergy() <= 0) {
                animalsOnMap.get(iD).animalDies();
                diedAnimalIDs.add(iD);
            }
        }
        for (int iD: diedAnimalIDs) {
            animalsOnMap.remove(iD);
        }

    }

    private void moveAnimals() {
        for (int iD: this.animalsOnMap.keySet()) {
            animalsOnMap.get(iD).move();
        }
    }

    private void grassEating() {
        for (int i=0; i< map.getWidth(); i++)
            for (int j=0; j< map.getHeight(); j++) {
                MapField field = map.getMapField(new Vector2d(i,j));
                if (field.containsGrass() && field.containsAnimals()) {
                    map.animalsEatGrass(new Vector2d(i,j));
                }
            }
    }

    private void animalsBreed() {
        for (int i=0; i< map.getWidth(); i++)
            for (int j=0; j< map.getHeight(); j++) {
                MapField field = map.getMapField(new Vector2d(i,j));
                List<Animal> animalList = field.animalsToBreed(
                        Integer.parseInt(simulationSettings.getValue("energyToBreed")));
                if (animalList.size() >= 2) {
                    Animal bornAnimal = new Animal(animalList.get(0), animalList.get(1));
                    animalsOnMap.put(bornAnimal.getId(),bornAnimal);
                }
            }
    }

    public int getCurrentEpoch() {
        return currentEpoch;
    }

    public void addObserver(ISimulationObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(ISimulationObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (ISimulationObserver observer: this.observers){
            observer.nextEpochRendered();
        }
    }

    public HashMap<Integer, Animal>  getAnimalsOnMap() {
        return animalsOnMap;
    }

}
