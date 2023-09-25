package com.jrhcodes.marsrover.spatialmath;

public class Vector extends com.jrhcodes.marsrover.spatialmath.Point {
    public Vector(int x, int y) {
        super(x, y);
    }

    public com.jrhcodes.marsrover.spatialmath.Point applyCopy(com.jrhcodes.marsrover.spatialmath.Point p) {
        return new com.jrhcodes.marsrover.spatialmath.Point(this.getX() + p.getX(), this.getY() + p.getY());
    }
}
