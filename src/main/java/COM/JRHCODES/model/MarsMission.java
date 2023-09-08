package com.jrhcodes.model;

import com.jrhcodes.spatialmath.Point;
import com.jrhcodes.spatialmath.Pose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class MarsMission {
    private final Plateau plateau;
    private final Rover[] rovers;

    public MarsMission(Plateau plateau, int numberOfRovers) {
        this.plateau = plateau;
        rovers = new Rover[numberOfRovers];
    }

    public void addRover(Rover rover, int index) {
        rovers[index] = rover;
    }

    public Rover getRover(int index) {
        return rovers[index];
    }

    public Rover[] getRovers() {
        return rovers;
    }

    public Stream<Rover> streamRovers() {
        return Arrays.stream(rovers);
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean isCollisionAtStep(int roverIndex, int step) {


        Rover rover = getRover(roverIndex);
        Pose[] theRoversPath = rover.getPath();
        Point theRoversPosition = theRoversPath[step].getPosition();

        boolean collisionHasOccurred = false;

        for (int otherRoverIndex = 0; otherRoverIndex < rovers.length && !collisionHasOccurred; otherRoverIndex++) {

            if (otherRoverIndex != roverIndex) {

                Rover otherRover = getRover(otherRoverIndex);
                Pose[] theOtherRoversPath = otherRover.getPath();

                int theOtherRoversPathMax = theOtherRoversPath.length - 1;

                Pose theOtherRoversPose;

                theOtherRoversPose = theOtherRoversPathMax < step ?
                        theOtherRoversPath[theOtherRoversPathMax] : theOtherRoversPath[step];

                Point theOtherRoversPosition = theOtherRoversPose.getPosition();

                if (theOtherRoversPosition.equals(theRoversPosition)) {

                    collisionHasOccurred = true;

                } else if (step > 0 && theOtherRoversPathMax < step) {

                    Point theRoversPenultimatePosition = theRoversPath[step - 1].getPosition();
                    Point theOtherRoversPenultimatePosition = theOtherRoversPath[step - 1].getPosition();

                    collisionHasOccurred =
                            theOtherRoversPenultimatePosition.equals(theRoversPosition) && theRoversPenultimatePosition.equals(theOtherRoversPosition);

                }
            }
        }
        return collisionHasOccurred;
    }

    public int findLongestRoverPath() {
        int longestPath = 0;

        for (Rover rover : rovers) {
            int pathLength = rover.getPath().length;
            if (pathLength > longestPath) {
                longestPath = pathLength;
            }
        }
        return longestPath;
    }

}

