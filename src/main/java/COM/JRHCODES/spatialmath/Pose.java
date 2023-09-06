package com.jrhcodes.spatialmath;

public class Pose {
    private final Point position;
    private final CompassDirection compassDirection;

    public Pose(Point position, CompassDirection compassDirection) {
        this.position = position;
        this.compassDirection = compassDirection;
    }

    public Pose forwards() {
        return new Pose(compassDirection.getVector().applyCopy(position), this.compassDirection);
    }

    public CompassDirection left() {
        return compassDirection.leftOf();
    }

    public CompassDirection right() {
        return compassDirection.rightOf();
    }

    public Point getPosition() {
        return position;
    }

    public CompassDirection getDirection() {
        return compassDirection;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
