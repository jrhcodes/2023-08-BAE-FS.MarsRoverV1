package com.jrhcodes.marsrover.spatialmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class VectorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getXTest(int x, int y) {
        Assertions.assertEquals((new com.jrhcodes.marsrover.spatialmath.Vector(x, y)).getX(), x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getYTest(int x, int y) {
        Assertions.assertEquals((new com.jrhcodes.marsrover.spatialmath.Vector(x, y)).getY(), y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void equalsTest(int x, int y) {
        com.jrhcodes.marsrover.spatialmath.Vector vector = new com.jrhcodes.marsrover.spatialmath.Vector(x, y);
        Assertions.assertEquals(vector, new com.jrhcodes.marsrover.spatialmath.Vector(x, y));
        Assertions.assertNotEquals(vector, new com.jrhcodes.marsrover.spatialmath.Vector(x, y - 1));
        Assertions.assertNotEquals(vector, new com.jrhcodes.marsrover.spatialmath.Vector(x - 1, y));
        Assertions.assertNotEquals(vector, new com.jrhcodes.marsrover.spatialmath.Vector(x, y + 1));
        Assertions.assertNotEquals(vector, new com.jrhcodes.marsrover.spatialmath.Vector(x + 1, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestVectorValues.csv", numLinesToSkip = 2)
    public void applyTest(int x, int y, int vx, int vy, int rx, int ry) {
        com.jrhcodes.marsrover.spatialmath.Point point = new com.jrhcodes.marsrover.spatialmath.Vector(vx, vy).applyCopy(new com.jrhcodes.marsrover.spatialmath.Point(x, y));
        assertEquals(point.getX(), rx);
        assertEquals(point.getY(), ry);
    }
}