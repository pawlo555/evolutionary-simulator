package SimulationClasses;

import Observers.IAnimalObserver;
import Observers.IMapObserver;
import Statistics.WorldStatistics;
import Utilities.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class JungleMap implements IAnimalObserver {

    private final int width;
    private final int height;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final Map<Vector2d, MapField> mapFields;
    private final SimulationSettings simulationSettings;
    private SimulationEngine engine;
    private final List<IMapObserver> observers = new ArrayList<>();

    public JungleMap() {
        this.width = 50;
        this.height = 50;
        this.jungleLowerLeft = new Vector2d(20,20);
        this.jungleUpperRight = new Vector2d(30,30);
        this.mapFields = CreateHashMap(width, height);
        this.simulationSettings = new SimulationSettings();
    }

    public JungleMap(SimulationSettings settings, WorldStatistics worldStatistics) {
        this.width = Integer.parseInt(settings.getValue("width"));
        this.height = Integer.parseInt(settings.getValue("height"));
        int jungleWidth = (int) (width*Float.parseFloat(settings.getValue("jungleRatio")));
        int jungleHeight = (int) (height*Float.parseFloat(settings.getValue("jungleRatio")));
        this.jungleLowerLeft = new Vector2d((width-jungleWidth)/2, (height-jungleHeight)/2);
        this.jungleUpperRight = new Vector2d(
                jungleLowerLeft.getX()+jungleWidth, jungleLowerLeft.getY()+ jungleHeight);

        this.mapFields = CreateHashMap(Integer.parseInt(

                settings.getValue("width")), Integer.parseInt(settings.getValue("height")));

        this.simulationSettings = settings;
        this.addObserver(worldStatistics);

    }


    public void place(Animal animal) {
        mapFields.get(animal.getPosition()).addAnimal(animal);
        animalPlacedOnMap(animal);
    }

    public boolean isOccupied(Vector2d position) {
        return mapFields.get(position).containsAnimals();
    }

    @Override
    public void positionChange(Animal animal, Vector2d startPosition) {
        mapFields.get(startPosition).removeAnimal(animal);
        mapFields.get(animal.getPosition()).addAnimal(animal);
    }

    @Override
    public void animalBorn(Animal newAnimal, Animal p1, Animal p2) {
        this.place(newAnimal);

        MapField mapField = getMapField(p1.getPosition());
        mapField.removeAnimal(p1);
        mapField.removeAnimal(p2);
        int parent1EnergyLost = p1.Reproduce();
        int parent2EnergyLost = p2.Reproduce();
        animalBecomeParentOnMap(p1, parent1EnergyLost);
        animalBecomeParentOnMap(p2, parent2EnergyLost);
        mapField.addAnimal(p1);
        mapField.addAnimal(p2);
    }

    @Override
    public void animalDies(Animal diedAnimal) {
        mapFields.get(diedAnimal.getPosition()).removeAnimal(diedAnimal);
        animalDiedOnMap(diedAnimal, engine.getCurrentEpoch());
    }

    @Override
    public void animalEatGrass(Animal animal, int energyGained) {
        MapField mapField = getMapField(animal.getPosition());
        mapField.removeAnimal(animal);
        animal.energyChange(energyGained);
        mapField.addAnimal(animal);
    }

    @Override
    public void animalSurviveEpoch(Animal animal, int energyCost) {
        animalSurvivedEpochOnMap(energyCost);
        MapField mapField = getMapField(animal.getPosition());
        mapField.removeAnimal(animal);
        animal.energyChange(-1*energyCost);
        mapField.addAnimal(animal);
    }

    public void animalsEatGrass(Vector2d position) {
        MapField field = this.getMapField(position);
        field.removeGrass();
        ArrayList<Animal> animalList = field.animalsWithTopEnergy();
        int energyPerAnimal = Integer.parseInt(simulationSettings.getValue("energyPerGrass"))/ animalList.size();
        for(Animal animal: animalList) {
            animal.EatGrass(energyPerAnimal);
        }
        animalsEatGrassOnMap(energyPerAnimal*animalList.size());
    }

    public Vector2d NormalizeVector(Vector2d newPosition) {
        int x = newPosition.getX();
        int y = newPosition.getY();
        if (x < 0 || x >= width) {
            x = (x + width) % width;
        }
        if (y < 0 || y >= height) {
            y = (y + height) % height;
        }
        return new Vector2d(x,y);
    }

    private static HashMap<Vector2d, MapField> CreateHashMap(int width, int height) {
        HashMap<Vector2d, MapField> hashMap = new HashMap<>(width*height*2);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {

                Vector2d position = new Vector2d(i, j);
                MapField mapField = new MapField();
                hashMap.put(position, mapField);
            }
        return hashMap;
    }

    public boolean isInJungle(Vector2d position) {
        return position.precedes(this.jungleUpperRight) && position.follows(this.jungleLowerLeft);
    }

    public void spawnGrass() {
        boolean grassInJungle = false;
        boolean grassInSteppe = false;
        int attempt = 0;
        int MaxAttempt = 1000;
        while ((!grassInJungle || !grassInSteppe) && attempt < MaxAttempt) {
            if (!grassInJungle) {
                Vector2d junglePosition = randVectorInJungle();
                MapField mapField = mapFields.get(junglePosition);
                if (!mapField.containsGrass()) {
                    mapField.addGrass();
                    grassInJungle = true;
                }
            }
            if (!grassInSteppe) {
                Vector2d steppePosition = randVectorInSteppe();
                MapField mapField = mapFields.get(steppePosition);
                if (!mapField.containsGrass()) {
                    mapField.addGrass();
                    grassInSteppe = true;
                }
            }
            attempt++;
        }
        grassSpawnedOnMap(grassInJungle,grassInSteppe);
    }

    public Vector2d randVectorInJungle() {
        int width = this.jungleUpperRight.getX() - jungleLowerLeft.getX();
        int height = this.jungleUpperRight.getY() - jungleLowerLeft.getY();
        return this.jungleLowerLeft.add(randField(width+1,height+1));
    }

    public Vector2d randVectorInSteppe() {
        Vector2d position = randField(width, height);
        if (isInJungle(position)) {
            position = position.add(this.jungleLowerLeft.subtract(this.jungleUpperRight));
            position = NormalizeVector(position);
        }
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MapField getMapField(Vector2d position) {
        return this.mapFields.get(position);
    }

    public SimulationSettings getSimulationSettings() {
        return this.simulationSettings;
    }


    public Vector2d randField(int width, int height) {
        int x = ThreadLocalRandom.current().nextInt(width);
        int y = ThreadLocalRandom.current().nextInt(height);
        return new Vector2d(x,y);
    }

    public void addObserver(IMapObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IMapObserver observer) {
        this.observers.remove(observer);
    }

    private void animalPlacedOnMap(Animal animal) {
        for (IMapObserver observer: observers) {
            observer.animalPlaced(animal, engine.getCurrentEpoch());
        }
    }

    private void animalsEatGrassOnMap(int energyGained) {
        for (IMapObserver observer: observers) {
            observer.animalsEatGrass(energyGained);
        }
    }

    private void animalDiedOnMap(Animal animal, int dieEpoch) {
        for (IMapObserver observer: observers) {
            observer.animalDied(animal, dieEpoch);
        }
    }

    public void animalBecomeParentOnMap(Animal parent, int energyLost) {
        for (IMapObserver observer: observers) {
            observer.animalBecomeParent(parent, energyLost);
        }
    }

    public void animalSurvivedEpochOnMap(int energyToSurvive) {
        for (IMapObserver observer: observers) {
            observer.animalSurvivedEpoch(energyToSurvive);
        }
    }

    public void setSimulationEngine(SimulationEngine engine) {
        this.engine = engine;
    }

    public void grassSpawnedOnMap(boolean inJungle, boolean inSteppe) {
        int spawnedGrass = 0;
        if (inJungle)
            spawnedGrass++;
        if (inSteppe)
            spawnedGrass++;
        for (IMapObserver observer: observers) {
            observer.grassSpawned(spawnedGrass);
        }

    }

}
