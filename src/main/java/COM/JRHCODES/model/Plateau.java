package com.jrhcodes.model;

import com.jrhcodes.spatialmath.Point;

public class Plateau {
    private static final Point origin = new Point(0, 0);
    private final Point topRight;

    public Plateau(int topRightX, int topRightY) {
        this.topRight = new Point(topRightX, topRightY);
    }

    public Point getTopRight() {
        return this.topRight;
    }

    public boolean contains(Point point) {
        return point.isBoundBy(origin, topRight);
    }
}
