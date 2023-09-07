package com.jrhcodes.spatialmath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompassDirectionTest {
    @ParameterizedTest
    @CsvFileSource(resources = "TestCompassDirectionValues.csv", numLinesToSkip = 2)
    public void getVectorTest(CompassDirection compassDirection, int x, int y, int sx, int sy, int rx, int ry) {
        assertEquals(compassDirection.getVector().getX(), x);
        assertEquals(compassDirection.getVector().getY(), y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestCompassDirectionValues.csv", numLinesToSkip = 2)
    public void moveTest(CompassDirection compassDirection, int x, int y, int sx, int sy, int rx, int ry) {
        Point applied = compassDirection.moveCopy(new Point(sx, sy));
        assertEquals(applied.getX(), rx);
        assertEquals(applied.getY(), ry);
    }

    @ParameterizedTest
    @CsvSource({"N,E", "E,S", "S,W", "W,N"})
    public void rightOfTest(CompassDirection start, CompassDirection left) {
        assertEquals(start.rightOf(), left);
    }

    @ParameterizedTest
    @CsvSource({"N,W", "E,N", "S,E", "W,S"})
    public void leftOfTest(CompassDirection start, CompassDirection left) {
        assertEquals(start.leftOf(), left);
    }
}
