package com.jrhcodes.marsrover.model;

import com.jrhcodes.marsrover.spatialmath.CompassDirection;
import com.jrhcodes.marsrover.spatialmath.Point;
import com.jrhcodes.marsrover.spatialmath.Pose;

import java.util.Arrays;

public class Rover {

    static final String commandSet = "LRM";
    String commandString;
    Pose pose;
    Pose[] path;

    public Rover(int x, int y, CompassDirection compassDirection, String commands) {
        this.pose = new Pose(new Point(x, y), compassDirection);
        this.commandString = commands;
        executeMission();
    }

    public static boolean isValidCommandSequence(String commands) {
        return commands != null
                && !commands.isEmpty()
                && commands.chars().allMatch(ch -> commandSet.indexOf(ch) != -1);
    }

    public static boolean isValidCommand(char command) {
        return command != 0 && commandSet.indexOf(command) != -1;
    }

    public Pose getPose() {
        return pose;
    }

    public Point getPosition(int step) {
        if (step < path.length) {
            return path[step].getPosition();
        } else {
            return pose.getPosition();
        }
    }

    public void executeMission() {

        String[] commands = commandString.split("");
        path = new Pose[commands.length + 1];
        path[0] = this.pose;


        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case "L" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().leftOf());
                case "R" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().rightOf());
                case "M" ->
                        this.pose = new Pose(this.pose.getDirection().getVector().applyCopy(this.pose.getPosition()), this.pose.getDirection());
                default ->
                        throw new IllegalArgumentException("Unknown command '%s' at position %d in command sequence:'%s'".formatted(commands[i], i, commandString));
            }
            path[i + 1] = this.pose;
        }

        this.pose.getX();
        this.pose.getY();
    }

    @Override
    public String toString() {
        Point position = pose.getPosition();
        return "%d %d %s".formatted(position.getX(), position.getY(), pose.getDirection().name());
    }

    public boolean pathStaysWithinPlateau(com.jrhcodes.marsrover.model.Plateau plateau) {
        return Arrays.stream(this.path).allMatch(pose -> plateau.contains(pose.getPosition()));
    }

    public Pose[] getPath() {
        return this.path;
    }

    public String getCommandSequence() {
        return commandString;
    }

    public boolean hasCollidedWith(Rover otherRover, int step) {
        Point roversPosition = getPosition(step);
        Point otherRoversPosition = otherRover.getPosition(step);

        if (roversPosition.equals(otherRoversPosition)) {
            return true;
        } else if (step > 0 && step < path.length && step < otherRover.path.length) {
            Point theRoversPenultimatePosition = getPosition(step - 1);
            Point theOtherRoversPenultimatePosition = otherRover.getPosition(step - 1);

            return theOtherRoversPenultimatePosition.equals(roversPosition)
                    && theRoversPenultimatePosition.equals(otherRoversPosition);
        }
        return false;

    }
}
