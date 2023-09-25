package com.jrhcodes.marsrover.spatialmath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class VectorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getXTest(int x, int y) {
        assertEquals((new Vector(x, y)).getX(), x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getYTest(int x, int y) {
        assertEquals((new Vector(x, y)).getY(), y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void equalsTest(int x, int y) {
        Vector vector = new Vector(x, y);
        assertEquals(vector, new Vector(x, y));
        assertNotEquals(vector, new Vector(x, y - 1));
        assertNotEquals(vector, new Vector(x - 1, y));
        assertNotEquals(vector, new Vector(x, y + 1));
        assertNotEquals(vector, new Vector(x + 1, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestVectorValues.csv", numLinesToSkip = 2)
    public void applyTest(int x, int y, int vx, int vy, int rx, int ry) {
        Point point = new Vector(vx, vy).applyCopy(new Point(x, y));
        assertEquals(point.getX(), rx);
        assertEquals(point.getY(), ry);
    }
}