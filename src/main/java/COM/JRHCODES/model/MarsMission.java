package com.jrhcodes.model;

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

        for (int j = 0; j < rovers.length; j++) {
            if (j != roverIndex && rovers[roverIndex].getPath().get(step).equals(rovers[j].getPath().get(step))) {
                return true;
            }
        }

        return false;

    }

}
