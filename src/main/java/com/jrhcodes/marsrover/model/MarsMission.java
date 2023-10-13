package com.jrhcodes.marsrover.model;
import  com.jrhcodes.marsrover.model.Plateau;
import  com.jrhcodes.marsrover.model.Rover;
import java.lang.reflect.Array;

public class MarsMission {

    private Plateau plateau;
    private Rover[] rovers;

    public MarsMission(Plateau plateau, int numberOfRovers) {
        this.plateau = plateau;
        rovers = new Rover[numberOfRovers];
    }

    public void addRover(com.jrhcodes.marsrover.model.Rover rover, int index) {
        rovers[index] = rover;
    }

    public com.jrhcodes.marsrover.model.Rover getRover(int index) {
        return rovers[index];
    }

    public com.jrhcodes.marsrover.model.Rover[] getRovers() {
        return rovers;
    }

    public com.jrhcodes.marsrover.model.Plateau getPlateau() {
        return plateau;
    }

    public boolean isCollisionAtStep(int roverIndex, int step) {

        com.jrhcodes.marsrover.model.Rover theRover = getRover(roverIndex);

        for (int otherRoverIndex = 0; otherRoverIndex < rovers.length; otherRoverIndex++) {
            if (otherRoverIndex != roverIndex && theRover.hasCollidedWith(getRover(otherRoverIndex), step)) {
                return true;
            }
        }
        return false;
    }

    public int findLongestRoverPath() {
        int longestPath = 0;
        for (com.jrhcodes.marsrover.model.Rover rover : rovers) {
            int pathLength = rover.getPath().length;
            if (pathLength > longestPath) {
                longestPath = pathLength;
            }
        }
        return longestPath;
    }

}

