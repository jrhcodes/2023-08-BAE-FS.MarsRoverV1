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

    @Override
    public boolean equals(Object object) {
        return this == object || (object instanceof Point point && x == point.x && y == point.y);
    }

    public boolean isBoundBy(Point bottomLeft, Point topRight) {
        return x >= bottomLeft.x && y >= bottomLeft.y && y <= topRight.y && x <= topRight.x;
    }
}
