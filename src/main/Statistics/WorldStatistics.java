package Statistics;

import SimulationClasses.Animal;
import Observers.IMapObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldStatistics implements IMapObserver {

    private int currentGrassAmount = 0;
    private int currentAnimalsAmount = 0;
    private int maxGrassAmount = 0;
    private int maxAnimalsAmount = 0;
    private final HashMap<String, ArrayList<Integer>> dominatingGenomes = new HashMap<>();
    private final HashMap<Integer, Integer> animalsBornEpochs = new HashMap<>();
    private final HashMap<Integer, Integer> animalsChildren = new HashMap<>();
    private int totalEnergy = 0;
    private int diedAnimals = 0;
    private int totalLiveTime = 0;
    private int totalChildrenAmount = 0;

    public int getCurrentAnimalsAmount() {
        return currentAnimalsAmount;
    }

    public int getMaxAnimalsAmount() {
        return maxAnimalsAmount;
    }

    public int getCurrentGrassAmount() {
        return currentGrassAmount;
    }

    public int getMaxGrassAmount() {
        return maxGrassAmount;
    }

    public String getDominatingGenome() {
        String dominatingGenome = "-";
        int maxSize = 0;
        for (String key: dominatingGenomes.keySet()) {
            if (dominatingGenomes.get(key).size() > maxSize) {
                maxSize = dominatingGenomes.get(key).size();
                dominatingGenome = key;
            }
        }
        return dominatingGenome;
    }

    public List<Integer> getIdsOfAnimalsWithDominatingGenome() {
        return dominatingGenomes.get(getDominatingGenome());
    }

    public int getNumberOfAnimalsWithDominatingGenome() {
        List<Integer> dominatingGenomeList = getIdsOfAnimalsWithDominatingGenome();
        if (dominatingGenomeList != null)
            return dominatingGenomeList.size();
        else
            return 0;
    }

    public double getAverageAnimalEnergy() {
        if (animalsChildren.size() > 0)
            return ((double)totalEnergy) / animalsChildren.size();
        else
            return 0;
    }

    public double getAverageNumberOfChildren() {
        if (animalsChildren.size() > 0)
            return ((double)totalChildrenAmount) / animalsChildren.size();
        else
            return 0;
    }

    public double getAverageLiveLength() {
        if (diedAnimals > 0)
            return ((double)totalLiveTime) / diedAnimals;
        else
            return 0.0;
    }

    @Override
    public void animalPlaced(Animal animal, int placeEpoch) {
        currentAnimalsAmount++;
        if (currentAnimalsAmount > maxAnimalsAmount) {
            maxAnimalsAmount=currentAnimalsAmount;
        }

        String genomeAsString = animal.getGenome().toString();
        if (dominatingGenomes.containsKey(genomeAsString)){
            dominatingGenomes.get(genomeAsString).add(animal.getId());
        }
        else {
            ArrayList<Integer> newGenomeList = new ArrayList<>();
            newGenomeList.add(animal.getId());
            dominatingGenomes.put(genomeAsString, newGenomeList);
        }

        animalsBornEpochs.put(animal.getId(), placeEpoch);
        animalsChildren.put(animal.getId(), 0);

        totalEnergy += animal.getEnergy();
    }

    @Override
    public void animalsEatGrass(int energyGained) {
        totalEnergy += energyGained;
        currentGrassAmount--;
    }

    @Override
    public void animalDied(Animal animal, int dieEpoch) {
        currentAnimalsAmount--;
        dominatingGenomes.get(animal.getGenome().toString()).remove(Integer.valueOf(animal.getId()));
        totalChildrenAmount -= animalsChildren.get(animal.getId());
        animalsChildren.remove(animal.getId());
        totalEnergy -= animal.getEnergy();
        totalLiveTime += dieEpoch - animalsBornEpochs.get(animal.getId());
        animalsBornEpochs.remove(animal.getId());
        diedAnimals++;
    }

    @Override
    public void animalBecomeParent(Animal parent, int energyLost) {
        totalChildrenAmount++;
        int currentChildren = animalsChildren.get(parent.getId());
        currentChildren++;
        animalsChildren.remove(parent.getId());
        animalsChildren.put(parent.getId(),currentChildren);
        totalEnergy -= parent.getEnergy()/4;
    }

    @Override
    public void animalSurvivedEpoch(int energyToSurvive) {
        totalEnergy -= energyToSurvive;
    }

    @Override
    public void grassSpawned(int grassSpawned) {
        currentGrassAmount += grassSpawned;
        if (currentGrassAmount > maxGrassAmount) {
            maxGrassAmount = currentGrassAmount;
        }
    }
}
