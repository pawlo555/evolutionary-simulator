package GUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.*;
import java.util.List;
import java.util.TreeSet;


public class MapVisualizer extends Canvas implements ISimulationObserver{

    private final JungleMap map;
    private Animal followedAnimal;
    private final WorldStatistics worldStatistics;

    public MapVisualizer(JungleMap map, WorldStatistics worldStatistics) {
        this.map = map;
        this.setHeight(map.getHeight()*10);
        this.setWidth(map.getWidth()*10);
        this.worldStatistics = worldStatistics;
    }

    public void Visualize() {
        List<Integer> iDsList = worldStatistics.getIdsOfAnimalsWithDominatingGenome();
        GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
        graphicsContext2D.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (int i=0; i<map.getWidth(); i++)
            for (int j=0; j<map.getHeight(); j++){
                VisualizeField(new Vector2d(i, j), graphicsContext2D, iDsList);
            }
    }

    public void VisualizeField(Vector2d position, GraphicsContext graphicsContext2D, List<Integer> iDsList) {
        MapField mapField = this.map.getMapField(position);
        if (map.isInJungle(position) ) {
            if (mapField.containsGrass())
                graphicsContext2D.setFill(Color.DARKGREEN);
            else
                graphicsContext2D.setFill(Color.FORESTGREEN);
        }
        else
            if (mapField.containsGrass())
                graphicsContext2D.setFill(Color.GREENYELLOW);
            else
                graphicsContext2D.setFill(Color.KHAKI);
        graphicsContext2D.fillRect(position.getX()*10, position.getY()*10, 9, 9);

        if (mapField.containsAnimals()) {
            Animal animal = mapField.getTopAnimal();
            if (animal.getEnergy() >= Integer.parseInt(map.getSimulationSettings().getValue("energyToBred")))
                graphicsContext2D.setFill(Color.SADDLEBROWN);
            else
                graphicsContext2D.setFill(Color.RED);
            graphicsContext2D.fillOval(position.getX()*10+1, position.getY()*10+1, 7, 7);
        }
        TreeSet<Animal> animalsTree = mapField.getAnimalsOnField();
        if (!animalsTree.isEmpty()){
            if (followedAnimal != null && animalsTree.contains(followedAnimal)) {
                graphicsContext2D.setFill(Color.WHITE);
                graphicsContext2D.fillOval(position.getX()*10, position.getY()*10, 5, 5);
            }
            for(Animal animal: animalsTree) {
                if (iDsList.contains(animal.getId())) {
                    graphicsContext2D.setFill(Color.PURPLE);
                    graphicsContext2D.fillOval(position.getX()*10+5, position.getY()*10+5, 5, 5);
                }
            }
        }

    }

    public Vector2d getMapPosition(double width, double height) {
        return new Vector2d((int) width/10, (int) height/10);
    }

    @Override
    public void nextEpochRendered() {
        Visualize();
    }

    public void setFollowedAnimal(Animal animalToFollow) {
        System.out.println("Animal to follow: " + animalToFollow);
        followedAnimal = animalToFollow;
    }

}
