package main;

import java.util.concurrent.ThreadLocalRandom;

public enum MoveDirection {
    FORWARD(0),
    FORWARD_RIGHT(1),
    RIGHT(2),
    BACKWARD_RIGHT(3),
    BACKWARD(4),
    BACKWARD_LEFT(5),
    LEFT(6),
    FORWARD_LEFT(7);

    private final int turnBy;

    static MoveDirection RandDirection() {
        int randedInt = ThreadLocalRandom.current().nextInt(8);
        return switch (randedInt) {
            case 0 -> MoveDirection.FORWARD;
            case 1 -> MoveDirection.FORWARD_RIGHT;
            case 2 -> MoveDirection.RIGHT;
            case 3 -> MoveDirection.BACKWARD_RIGHT;
            case 4 -> MoveDirection.BACKWARD;
            case 5 -> MoveDirection.BACKWARD_LEFT;
            case 6 -> MoveDirection.LEFT;
            case 7 -> MoveDirection.FORWARD_LEFT;
            default -> throw new IllegalStateException("Unexpected randed value: " + randedInt);
        };
    }

    MoveDirection(int turnBy) {
        this.turnBy = turnBy;
    }

    public int getTurnBy() {
        return this.turnBy;
    }

    public String toString() {
        return Integer.toString(this.turnBy);
    }
}
