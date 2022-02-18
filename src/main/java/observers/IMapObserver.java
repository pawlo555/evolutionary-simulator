package observers;

import simulationClasses.Animal;

public interface IMapObserver {

    void animalPlaced(Animal animal, int placeEpoch);

    void animalsEatGrass(int energyGained);

    void animalDied(Animal animal, int dieEpoch);

    void animalBecomeParent(Animal parent, int energyLost);

    void animalSurvivedEpoch(int energyToSurvive);

    void grassSpawned(int grassSpawned);
}
