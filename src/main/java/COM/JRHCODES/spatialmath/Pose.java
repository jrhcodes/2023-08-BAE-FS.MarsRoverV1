package com.jrhcodes.spatialmath;

public class Pose {
    private final Point position;
    private final Direction direction;

    public Pose(Point position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Point forward() {
        return direction.getVector().apply(position);
    }
}
