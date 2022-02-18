package utilities;

import simulationClasses.Animal;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal>{

    @Override
    public int compare(Animal a1, Animal a2) {
        if (a1.getId() == a2.getId()) {
            return 0;
        }
        int energyDifference = a1.getEnergy() - a2.getEnergy();
        int idDifference = a1.getId() - a2.getId();

        if (energyDifference != 0)
            return energyDifference;
        else
            return idDifference;
    }
}
