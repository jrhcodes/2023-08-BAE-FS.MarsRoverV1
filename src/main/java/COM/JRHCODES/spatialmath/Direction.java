package com.jrhcodes.spatialmath;

public enum Direction {
    NORTH(0, 1), SOUTH(0, -1), EAST(1, 0), WEST(-1, 0);

    private final Vector vector;

    Direction(int x, int y) {
        this.vector = new Vector( x, y);
    }

    public Vector getVector() {
        return vector;
    }

    public Point move(Point point) {
        return vector.apply(point);
    }

}
