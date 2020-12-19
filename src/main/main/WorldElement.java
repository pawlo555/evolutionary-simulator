package main;

abstract public class WorldElement {
    protected Vector2d position;

    public WorldElement(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public abstract String toString();
}
