package com.jrhcodes.spatialmath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;


public class PointTest {

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getXTest(int x, int y) {
        assertEquals((new Point(x, y)).getX(), x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void getYTest(int x, int y) {
        assertEquals((new Point(x, y)).getY(), y);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "TestPointValues.csv", numLinesToSkip = 2)
    public void equalsTest(int x, int y) {
        assertTrue((new Point(x, y).equals((new Point(x, y)))));
        assertFalse((new Point(x, y - 1).equals((new Point(x, y)))));
        assertFalse((new Point(x + 1, y - 1).equals((new Point(x, y)))));
    }
}
