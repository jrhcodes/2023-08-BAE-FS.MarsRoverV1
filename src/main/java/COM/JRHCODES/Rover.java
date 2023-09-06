package com.jrhcodes;

import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Point;
import com.jrhcodes.spatialmath.Pose;

import java.util.ArrayList;
import java.util.List;

public class Rover {

    static final String commandSet = "LRF";
    String commandString;
    Pose pose;
    ArrayList<Pose> path;

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

    public void  executeMission() {

        String[] commands = commandString.split("");
        path = new ArrayList<>(1+commands.length);
        path.add(this.pose);

        for (String command : commands) {
            switch (command) {
                case "L" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().leftOf());
                case "R" -> this.pose = new Pose(this.pose.getPosition(), this.pose.getDirection().rightOf());
                case "M" ->
                        this.pose = new Pose(this.pose.getDirection().getVector().applyCopy(this.pose.getPosition()), this.pose.getDirection());
                default ->
                        throw new IllegalArgumentException("Unknown command in command sequence:'%s'".formatted(commandString));
            }
            path.add(this.pose);
        }

    }

    @Override
    public String toString() {
        Point position = pose.getPosition();
        return "%d %d %s".formatted(position.getX(), position.getY(), pose.getDirection().name());
    }

    boolean pathValid(Plateau plateau) {
        return path.stream().allMatch(pose -> plateau.contains(pose.getPosition()));
    }

    public ArrayList<Pose> getPath() {
        return path;
    }
}
