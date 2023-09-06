package com.jrhcodes.spatialmath;

public class Vector extends Point {
    public Vector(int x, int y) {
        super(x, y);
    }

    public Point apply(Point p) {
        return new Point(this.getX()+p.getX(), this.getY()+p.getY());
    }
}
