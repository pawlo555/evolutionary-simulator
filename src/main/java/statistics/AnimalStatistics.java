package statistics;

import simulationClasses.SimulationEngine;
import utilities.Vector2d;
import simulationClasses.Animal;
import observers.IAnimalObserver;

import java.util.*;

public class AnimalStatistics implements IAnimalObserver {

    private List<Integer> children = new ArrayList<>();
    private List<Integer> descendants =  new ArrayList<>();
    private int dieEpoch = -1;
    private int energy;
    private int iD;
    private String animalGenomeAsString;
    SimulationEngine engine;


    public AnimalStatistics(SimulationEngine engine) {
        this.engine = engine;
        ArrayList<Animal> animalsList = new ArrayList<>(engine.getAnimalsOnMap().values());
        Animal animalToObserve = animalsList.get(0);
        this.energy = animalToObserve.getEnergy();
        this.iD = animalToObserve.getId();
        this.animalGenomeAsString = animalToObserve.getGenome().toString();
        animalToObserve.addObserver(this);
    }

    public void changeAnimal(Animal newAnimalToObserve) {
        Animal animal = engine.getAnimalsOnMap().get(iD);
        if (animal != null)
            animal.removeObserver(this);
        this.children = new ArrayList<>();
        this.descendants = new ArrayList<>();
        this.dieEpoch = -1;
        this.energy = newAnimalToObserve.getEnergy();
        this.iD = newAnimalToObserve.getId();
        this.animalGenomeAsString = newAnimalToObserve.getGenome().toString();
        newAnimalToObserve.addObserver(this);
    }

    public String getDieEpoch() {
        if ( dieEpoch != -1)
            return Integer.toString(dieEpoch);
        else
            return "-";
    }

    public int getChildrenAmount() {
        return children.size();
    }

    public int getDescendantsAmount() {
        return descendants.size();
    }

    public int getEnergy() {
        return energy;
    }

    public int getId() {
        return iD;
    }

    public String getGenome() {
        return animalGenomeAsString;
    }

    @Override
    public void positionChange(Animal animal, Vector2d startPosition) {
    }

    @Override
    public void animalBorn(Animal newAnimal, Animal p1, Animal p2) {
        if (p1.getId() == this.iD || p2.getId() == this.iD) {
            children.add(newAnimal.getId());
            descendants.add(newAnimal.getId());
            if (p1.getId() == this.iD) {
                energy = p1.getEnergy();
            }
            else
                energy = p2.getEnergy();
        }
        else if (this.descendants.contains(p1.getId()) || this.descendants.contains(p2.getId()) ) {
            descendants.add(newAnimal.getId());
        }
    }

    @Override
    public void animalDies(Animal diedAnimal) {
        if (iD == diedAnimal.getId())
            this.dieEpoch = engine.getCurrentEpoch();
    }

    @Override
    public void animalEatGrass(Animal animal, int energyGained) {
        if (iD == animal.getId())
            this.energy += energyGained;
    }

    @Override
    public void animalSurviveEpoch(Animal animal, int energyCost) {
        if (iD == animal.getId())
            this.energy -= energyCost;
    }
}
