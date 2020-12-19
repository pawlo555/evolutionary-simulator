package main;

import java.util.ArrayList;
import java.util.TreeSet;

public class MapField {
    private final TreeSet<Animal> animals;
    private boolean hasGrass;

    public MapField() {
        AnimalComparator comparator = new AnimalComparator();
        this.animals = new TreeSet<>(comparator);
        hasGrass = false;
    }

    public boolean containsGrass() {
        return hasGrass;
    }

    public boolean containsAnimals() {
        return !animals.isEmpty();
    }

    public void addGrass() {
            hasGrass = true;
    }

    public void removeGrass() {
        hasGrass = false;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public ArrayList<Animal> animalsToBreed(int minimalEnergy) {
        ArrayList<Animal> listOfAnimalsToBreed = new ArrayList<>();
        if (animals.size() >= 2) {
            Animal first = animals.last();
            Animal second = animals.lower(animals.last());
            if (second != null && second.getEnergy() >= minimalEnergy) {
                listOfAnimalsToBreed.add(first);
                listOfAnimalsToBreed.add(second);
            }
        }
        return listOfAnimalsToBreed;
    }

    public ArrayList<Animal> animalsWithTopEnergy() {
        ArrayList<Animal> listOfAnimalsWithTopEnergy = new ArrayList<>();
        if (containsAnimals()) {
            Animal topAnimal = animals.last();
            listOfAnimalsWithTopEnergy.add(topAnimal);
            int topEnergy = topAnimal.getEnergy();
            Animal nextAnimal = animals.lower(topAnimal);
            while (nextAnimal != null && nextAnimal.getEnergy() == topEnergy) {
                listOfAnimalsWithTopEnergy.add(nextAnimal);
                nextAnimal = animals.lower(nextAnimal);
            }
        }
        return listOfAnimalsWithTopEnergy;
    }

    public Animal getTopAnimal() {
        return animals.first();
    }

    public TreeSet<Animal> getAnimalsOnField() {
        return animals;
    }
}
