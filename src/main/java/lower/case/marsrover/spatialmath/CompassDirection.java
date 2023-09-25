package com.jrhcodes.marsrover.spatialmath;

import java.util.HashMap;
import java.util.Map;

public enum CompassDirection {
    N(0, 1), S(0, -1), E(1, 0), W(-1, 0);

    private static final Map<CompassDirection, CompassDirection> leftTurns = new HashMap<>();
    private static final Map<CompassDirection, CompassDirection> rightTurns = new HashMap<>();

    static {
        leftTurns.put(CompassDirection.N, CompassDirection.W);
        leftTurns.put(CompassDirection.E, CompassDirection.N);
        leftTurns.put(CompassDirection.S, CompassDirection.E);
        leftTurns.put(CompassDirection.W, CompassDirection.S);
    }

    static {
        rightTurns.put(CompassDirection.N, CompassDirection.E);
        rightTurns.put(CompassDirection.E, CompassDirection.S);
        rightTurns.put(CompassDirection.S, CompassDirection.W);
        rightTurns.put(CompassDirection.W, CompassDirection.N);
    }

    private final com.jrhcodes.marsrover.spatialmath.Vector vector;

    CompassDirection(int x, int y) {
        this.vector = new com.jrhcodes.marsrover.spatialmath.Vector(x, y);
    }

    public CompassDirection leftOf() {
        return leftTurns.get(this);
    }

    public CompassDirection rightOf() {
        return rightTurns.get(this);
    }

    public com.jrhcodes.marsrover.spatialmath.Vector getVector() {
        return vector;
    }

    public com.jrhcodes.marsrover.spatialmath.Point moveCopy(com.jrhcodes.marsrover.spatialmath.Point point) {
        return vector.applyCopy(point);
    }

}
