package SimulationClasses;

import Observers.IAnimalObserver;
import Utilities.Genome;
import Utilities.MapDirection;
import Utilities.MoveDirection;
import Utilities.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal {
    private final int animalId;
    static private int uniqueId = 0;

    private Vector2d position;
    private final Genome genome;
    private MapDirection direction;
    private int energy;
    private final JungleMap map;

    private List<IAnimalObserver> observers = new ArrayList<>();

    public Animal(Vector2d position, JungleMap map) {
        this.position = position;
        this.genome = new Genome();
        this.direction = MapDirection.NORTH;
        this.energy = Integer.parseInt(map.getSimulationSettings().getValue("startEnergy"));
        this.map = map;
        this.animalId = getUniqueId();
        this.observers.add(map);
        this.map.place(this);
    }

    public Animal(Animal parent1, Animal parent2) {
        this.position = positionOfBornAnimal(parent1.getPosition(), parent1.map);
        this.genome = new Genome(parent1.genome, parent2.genome);
        this.direction = MapDirection.NORTH;
        this.energy = Integer.parseInt(parent1.map.getSimulationSettings().getValue("energyToBreed")) / 2
                + parent1.getEnergy()/4 + parent2.getEnergy() / 4;
        this.map = parent1.map;
        this.animalId = getUniqueId();

        // animalObserver needs to observe all descendants of followed animal
        this.observers = parent1.observers;
        for (IAnimalObserver observer: parent2.observers) {
            if (!this.observers.contains(observer)) {
                this.observers.add(observer);
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

    public Vector2d positionOfBornAnimal(Vector2d parentPosition, JungleMap map) {
        int attempt = 0;
        int maxAttempt = 100;

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

    public Vector2d getPosition() {
        return this.position;
    }
}
