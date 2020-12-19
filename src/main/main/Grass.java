package main;

public class Grass extends WorldElement {

    public Grass(Vector2d position) {
        super(position);
    }

    @Override
    public String toString() {
        return "T";
    }
}
