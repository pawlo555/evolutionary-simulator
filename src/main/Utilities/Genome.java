package Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Genome {
    private final ArrayList<MoveDirection> genomeList;
    private final String sortedGenomeAsString;

    static private final int genomeLength = 32;

    static private final ArrayList<MoveDirection> genomeBeginning =
            new ArrayList<>(new ArrayList<>(Arrays.asList(MoveDirection.values())));

    public Genome() {
        this.genomeList = new ArrayList<>();
        this.genomeList.addAll(genomeBeginning);
        for (int i=8; i<genomeLength; i++) {
            this.genomeList.add(MoveDirection.RandDirection());
        }
        sortedGenomeAsString = BuildString(genomeList);
        if (this.genomeList.size() != 32)
            throw new IllegalStateException("Incomplete genom" + this.toString());
    }

    public Genome(Genome parent1, Genome parent2) {
        Genome[] parents = {parent1, parent2};
        ArrayList<MoveDirection> childGenome = new ArrayList<>();
        childGenome.addAll(genomeBeginning);

        int firstBreak = ThreadLocalRandom.current().nextInt(8,genomeLength-1);
        int secondBreak = ThreadLocalRandom.current().nextInt(firstBreak,genomeLength);
        int parentWithOnePart = ThreadLocalRandom.current().nextInt(2);
        int PartToParentWithOnePart = ThreadLocalRandom.current().nextInt(3);
        int[] breaks = {8,firstBreak, secondBreak, genomeLength};
        for (int i=0; i<3; i++) {
            if (i == PartToParentWithOnePart) {
                childGenome.addAll(parents[parentWithOnePart].genomeList.subList(breaks[i], breaks[i+1]));
            }
            else {
                childGenome.addAll(parents[Math.abs(parentWithOnePart-1)].genomeList.subList(breaks[i], breaks[i+1]));
            }
        }
        this.genomeList = childGenome;
        sortedGenomeAsString = BuildString(genomeList);
        if (this.genomeList.size() != 32)
            throw new IllegalStateException("Incomplete genom" + this.toString());
    }

    public MoveDirection DrawSingleGen() {
        int index = ThreadLocalRandom.current().nextInt(genomeLength);
        return this.genomeList.get(index);
    }

    static public String BuildString(ArrayList<MoveDirection> genomeList) {

        StringBuilder builder = new StringBuilder();
        for (MoveDirection direction: genomeList.stream().sorted().collect(Collectors.toList())) {
            builder.append(direction);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return sortedGenomeAsString;
    }
}
