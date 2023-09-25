package com.jrhcodes.marsrover.spatialmath;

public class Pose {
    private final com.jrhcodes.marsrover.spatialmath.Point position;
    private final com.jrhcodes.marsrover.spatialmath.CompassDirection compassDirection;

    public Pose(com.jrhcodes.marsrover.spatialmath.Point position, com.jrhcodes.marsrover.spatialmath.CompassDirection compassDirection) {
        this.position = position;
        this.compassDirection = compassDirection;
    }

    public Pose forwards() {
        return new Pose(compassDirection.getVector().applyCopy(position), this.compassDirection);
    }

    public com.jrhcodes.marsrover.spatialmath.CompassDirection left() {
        return compassDirection.leftOf();
    }

    public com.jrhcodes.marsrover.spatialmath.CompassDirection right() {
        return compassDirection.rightOf();
    }

    public com.jrhcodes.marsrover.spatialmath.Point getPosition() {
        return position;
    }

    public com.jrhcodes.marsrover.spatialmath.CompassDirection getDirection() {
        return compassDirection;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
