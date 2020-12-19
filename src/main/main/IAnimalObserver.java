package main;

public interface IAnimalObserver {

    void positionChange(Animal animal,Vector2d startPosition);

    void animalBorn(Animal newAnimal, Animal p1, Animal p2);

    void animalDies(Animal diedAnimal);

    void animalEatGrass(Animal animal, int energyGained);

    void animalSurviveEpoch(Animal animal, int energyCost);

}
