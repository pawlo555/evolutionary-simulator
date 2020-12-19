package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal extends WorldElement {

    private final int animalId;
    static private int uniqueId = 0;

    private final Genome genome;
    private MapDirection direction;
    private int energy;
    private final JungleMap map;

    private final List<IAnimalObserver> observers = new ArrayList<>();

    public Animal(Vector2d position, JungleMap map) {
        super(position);
        this.genome = new Genome();
        this.direction = MapDirection.NORTH;
        this.energy = Integer.parseInt(map.getSimulationSettings().getValue("startEnergy"));
        this.map = map;
        this.animalId = getUniqueId();
        this.observers.add(map);
        this.map.place(this);
    }

    public Animal(Animal parent1, Animal parent2) {
        super(parent1.positionOfBornAnimal());
        this.genome = new Genome(parent1.genome, parent2.genome);
        this.direction = MapDirection.NORTH;
        this.energy = Integer.parseInt(parent1.map.getSimulationSettings().getValue("energyToBred")) / 2
                + parent1.getEnergy()/4 + parent2.getEnergy() / 4;
        this.map = parent1.map;
        this.animalId = getUniqueId();
        for (IAnimalObserver observer: parent1.observers) {
            if (!(observer instanceof AnimalStatistics)) {
                this.addObserver(observer);
            }
        }
        this.animalBorn(parent1, parent2);
    }

    public int Reproduce() {
        int energyLost = this.energy/4;
        this.energy -= energyLost;
        return energyLost;
    }

    public void EatGrass(int energyGained) {
        for (IAnimalObserver observer: this.observers) {
            observer.animalEatGrass(this,energyGained);
        }
    }

    public void move() {
        Vector2d oldPosition = this.position;
        MoveDirection moveDirection = this.genome.DrawSingleGen();
        this.direction = this.direction.turn(moveDirection);
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        newPosition = this.map.NormalizeVector(newPosition);
        this.position = newPosition;
        //System.out.println("Old position:" + oldPosition.toString());
        //System.out.println("New position:" + newPosition.toString());
        animalMoved(oldPosition);
    }

    @Override
    public String toString() {
        return this.energy + " - " + this.animalId;
    }

    public int getEnergy() {
        return this.energy;
    }
    public int getId() {
        return this.animalId;
    }

    static int getUniqueId() {
        return uniqueId++;
    }

    private void animalMoved(Vector2d oldPosition) {
        for (IAnimalObserver observer: this.observers) {
            observer.positionChange(this, oldPosition);
        }
    }

    private void animalBorn(Animal parent1, Animal parent2) {
        for (IAnimalObserver observer: this.observers) {
            observer.animalBorn(this, parent1, parent2);
        }
    }

    public void animalDies() {
        for (IAnimalObserver observer: this.observers) {
            observer.animalDies(this);
        }
    }

    public void EpochSurvived(int energyCost) {
        for (IAnimalObserver observer: this.observers) {
            observer.animalSurviveEpoch(this, energyCost);
        }
    }

    public void addObserver(IAnimalObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IAnimalObserver observer) {
        this.observers.remove(observer);
    }

    public Genome getGenome() {
        return this.genome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.animalId);
    }

    public void energyChange(int energyChange) {
        this.energy += energyChange;
    }

    public Vector2d positionOfBornAnimal() {
        int attempt = 0;
        int maxAttempt = 100;

        Vector2d parentPosition = this.getPosition();
        Vector2d newPosition = parentPosition;
        MapDirection mapDirection = MapDirection.NORTH;
        while (attempt < maxAttempt) {
            newPosition = map.NormalizeVector(
                    parentPosition.add(mapDirection.turn(MoveDirection.RandDirection()).toUnitVector()));
            attempt++;
            if (!map.isOccupied(newPosition)) {
                break;
            }
        }
        return newPosition;
    }
}