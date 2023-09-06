package com.jrhcodes.spatialmath;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Point point) {
        if (this == point) return true;
        return x == point.x && y == point.y;
    }

    public boolean isBoundBy(Point bottomLeft, Point topRight) {
        return x >= bottomLeft.x && y >= bottomLeft.y && y <= topRight.y && x <= topRight.x;
    }
}
