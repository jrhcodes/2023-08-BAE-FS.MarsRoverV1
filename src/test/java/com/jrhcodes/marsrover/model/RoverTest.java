package com.jrhcodes.marsrover.model;

import com.jrhcodes.marsrover.spatialmath.CompassDirection;
import com.jrhcodes.marsrover.spatialmath.Pose;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class RoverTest {

    @ParameterizedTest
    @CsvFileSource(resources = {"RoverNegativePathTest.csv", "RoverPathTest.csv"}, numLinesToSkip = 2)
    public void testGetCommandSequence(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        assertEquals(commands, rover.getCommandSequence());
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"RoverNegativePathTest.csv", "RoverPathTest.csv"}, numLinesToSkip = 2)
    public void testRoverPathX(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalX, finalPose.getX());
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"RoverNegativePathTest.csv", "RoverPathTest.csv"}, numLinesToSkip = 2)
    public void testRoverPathY(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalY, finalPose.getY());
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"RoverNegativePathTest.csv", "RoverPathTest.csv"}, numLinesToSkip = 2)
    public void testRoverPathDir(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalDir, finalPose.getDirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "RoverPathTest.csv", numLinesToSkip = 2)
    public void testRoverPathStaysWithin1x1Plateau(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        if (initX == 0 && initY == 0 && ("LR".contains(commands))) {
            assertTrue(rover.pathStaysWithinPlateau(new Plateau(1, 1)));
        } else {

            assertFalse(rover.pathStaysWithinPlateau(new Plateau(1, 1)));
        }

    }

    @ParameterizedTest
    @CsvFileSource(resources = "RoverPathTest.csv", numLinesToSkip = 2)
    public void testRoverPathStaysWithin10x10Plateau(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        assertTrue(rover.pathStaysWithinPlateau(new Plateau(10, 10)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "RoverNegativePathTest.csv", numLinesToSkip = 2)
    public void testRoverPathStaysWithin10x10PlateauNegativePath(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover(initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertFalse(rover.pathStaysWithinPlateau(new Plateau(10, 10)));
    }
}