package com.jrhcodes.marsrover.model;

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

    public Plateau getPlateau() {
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
        for (Rover rover : rovers) {
            int pathLength = rover.getPath().length;
            if (pathLength > longestPath) {
                longestPath = pathLength;
            }
        }
        return longestPath;
    }

}

