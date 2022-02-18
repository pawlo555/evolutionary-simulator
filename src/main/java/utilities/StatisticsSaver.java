package utilities;

import observers.ISimulationObserver;
import statistics.WorldStatistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsSaver implements ISimulationObserver {

    private final WorldStatistics worldStatistics;
    private int epochs;
    private final String nameOfFileToSave;

    private final List<Integer> grassOnEachEpoch = new ArrayList<>();
    private final List<Integer> animalsOnEachEpoch = new ArrayList<>();
    private final HashMap<String, Integer> dominatingGenomesOnEachEpoch = new HashMap<>();
    private final List<Double> averageEnergyOnEachEpoch = new ArrayList<>();
    private final List<Double> averageLiveLengthOnEachEpoch = new ArrayList<>();
    private final List<Double> averageChildrenAmountOnEachEpoch = new ArrayList<>();

    public StatisticsSaver(WorldStatistics worldStatistics, String nameOfFileToSave) {
        this.worldStatistics = worldStatistics;
        this.nameOfFileToSave = nameOfFileToSave;
        epochs = 0;
    }

    @Override
    public void nextEpochRendered() {
        grassOnEachEpoch.add(worldStatistics.getCurrentGrassAmount());
        animalsOnEachEpoch.add(worldStatistics.getCurrentAnimalsAmount());

        String dominatingGenomeAsString = worldStatistics.getDominatingGenome();
        if (dominatingGenomesOnEachEpoch.containsKey(dominatingGenomeAsString)) {
            int numberOfOccurrences = dominatingGenomesOnEachEpoch.get(dominatingGenomeAsString);
            dominatingGenomesOnEachEpoch.replace(dominatingGenomeAsString,numberOfOccurrences + 1);
        }
        else
            dominatingGenomesOnEachEpoch.put(dominatingGenomeAsString,0);

        averageEnergyOnEachEpoch.add(worldStatistics.getAverageAnimalEnergy());
        averageLiveLengthOnEachEpoch.add(worldStatistics.getAverageLiveLength());
        averageChildrenAmountOnEachEpoch.add(worldStatistics.getAverageNumberOfChildren());

        epochs++;
    }

    public void saveToFile() throws IOException {
        File fileToSave = new File(nameOfFileToSave+".txt" );
        System.out.println(fileToSave.canWrite());
        System.out.println(fileToSave.exists());
        FileWriter writer = new FileWriter(fileToSave);
        writer.write("Statistics after epoch " + epochs +":\n");
        writer.write("Grass: " + getAverageValueI(grassOnEachEpoch) + "\n");
        writer.write("Animals: " + getAverageValueI(animalsOnEachEpoch) + "\n");
        writer.write("Genome: " + getAverageGenome() + "\n");
        writer.write("Energy: " + getAverageValueD(averageEnergyOnEachEpoch) + "\n");
        writer.write("LiveLength: " + getAverageValueD(averageLiveLengthOnEachEpoch) + "\n");
        writer.write("ChildrenAmount: " + getAverageValueD(averageChildrenAmountOnEachEpoch) + "\n");
        writer.close();
    }

    public String getAverageGenome() {
        int maxAmount = 0;
        String averageDominatingGenome = "-";
        for (String key: dominatingGenomesOnEachEpoch.keySet()) {
            if (dominatingGenomesOnEachEpoch.get(key) > maxAmount) {
                maxAmount = dominatingGenomesOnEachEpoch.get(key);
                averageDominatingGenome = key;
            }
        }
        return averageDominatingGenome;
    }

    public double getAverageValueI(List<Integer> listIOfDoubles) {
        double sum = 0;
        for(int value: listIOfDoubles) {
            sum += value;
        }
        return sum/epochs;
    }

    public double getAverageValueD(List<Double> listIOfDoubles) {
        double sum = 0;
        for(double value: listIOfDoubles) {
            sum += value;
        }
        return sum/epochs;
    }

}
