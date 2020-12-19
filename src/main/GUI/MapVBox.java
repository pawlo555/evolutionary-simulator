package GUI;

import javafx.scene.layout.VBox;

public class MapVBox extends VBox {

    public MapVBox(MapVisualizer visualizer) {
        this.setMinSize(400, 400);
        this.getChildren().add(visualizer);
    }
}
