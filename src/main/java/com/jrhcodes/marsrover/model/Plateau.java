package com.jrhcodes.marsrover.model;

import com.jrhcodes.marsrover.spatialmath.Point;

public class Plateau {
    private static final Point origin = new Point(0, 0);
    private final Point topRight;

    public Plateau(int topRightX, int topRightY) {

        if (topRightX < 1 || topRightY < 1) {
            throw new IllegalArgumentException("Cannot create a plateau with negative dimensions");
        }

        this.topRight = new Point(topRightX, topRightY);
    }

    public Point getTopRight() {
        return this.topRight;
    }

    public boolean contains(Point point) {
        return point.isBoundBy(origin, topRight);
    }
}
