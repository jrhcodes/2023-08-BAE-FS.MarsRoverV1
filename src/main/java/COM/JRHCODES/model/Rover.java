package com.jrhcodes.model;

import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Point;
import com.jrhcodes.spatialmath.Pose;

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

    public Pose getPose() {
        return pose;
    }

    public boolean validCommands(String commands) {
        return commands.chars().allMatch(ch -> commandSet.indexOf(ch) != -1);
    }

    public String executeMission() {

        String[] commands = commandString.split("");
        path = new Pose[commands.length+1];
        path[0] = this.pose;


        for (int i = 0; i<commands.length; i++) {
            switch (commands[i]) {
                case "L" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().leftOf());
                case "R" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().rightOf());
                case "M" ->
                        this.pose = new Pose(this.pose.getDirection().getVector().applyCopy(this.pose.getPosition()), this.pose.getDirection());
                default ->
                        throw new IllegalArgumentException("Unknown command in command sequence:'%s'".formatted(commandString));
            }
            path[i+1] = this.pose;
        }

        return String.format("%d %d %s", this.pose.getX(), this.pose.getY(), this.pose.getDirection().name());
    }

    @Override
    public String toString() {
        Point position = pose.getPosition();
        return "%d %d %s".formatted(position.getX(), position.getY(), pose.getDirection().name());
    }

    public boolean pathStaysWithinPlateau(Plateau plateau) {
        return Arrays.stream(this.path).allMatch(pose -> plateau.contains(pose.getPosition()));
    }

    public Pose[] getPath() {
        return this.path;
    }

    public String getCommandSequence() {
        return commandString;
    }

}
