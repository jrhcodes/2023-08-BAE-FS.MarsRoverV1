package com.jrhcodes.marsrover.model;

import com.jrhcodes.marsrover.spatialmath.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlateauTest {
    @ParameterizedTest
    @CsvSource({"19,10", "10,1", "10, 1", "1000,1000" })
    public void constructorTest(int x, int y) {
        com.jrhcodes.marsrover.model.Plateau plateau = new com.jrhcodes.marsrover.model.Plateau(x, y);
        Point topRight = plateau.getTopRight();

        assertEquals( x, topRight.getX() );
        assertEquals(y, topRight.getY());
    }
    @ParameterizedTest
    @CsvSource({"0,0", "1,0", "0, -1",
                "1,-1", "-1, 1", "-1000,1000", "1000,-1000" })
    public void constructorInvalidTest(int x, int y) {
        assertThrows(IllegalArgumentException.class,  () -> new com.jrhcodes.marsrover.model.Plateau(x, y));
    }

}
