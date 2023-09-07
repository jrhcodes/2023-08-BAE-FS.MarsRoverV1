package com.jrhcodes.app;

import com.jrhcodes.model.MarsMission;
import com.jrhcodes.model.Plateau;
import com.jrhcodes.model.Rover;
import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Pose;
import com.jrhcodes.swingutils.JTextAreaWithPrintf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MarsRoverApp {
    private final JFrame frame;
    private final JButton executeButton;
    private final JTextAreaWithPrintf textArea;
    private String fileContent;


    public MarsRoverApp() {
        frame = new JFrame("Mars Rover App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton openButton = new JButton("Select File");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        openButton.setPreferredSize(new Dimension(100, 30));

        executeButton = new JButton("Execute Mission");
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeMission();
            }
        });
        executeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(openButton);
        buttonPanel.add(executeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        textArea = new JTextAreaWithPrintf(10, 30);
        textArea.setEditable(false);
        textArea.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);


        frame.setSize(1000, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MarsRoverApp();
            }
        });
    }

    private void openFile() {

        executeButton.setEnabled(false);
        textArea.setText(null);

        JFileChooser fileChooser = new JFileChooser();

        FileFilter marsFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".mars");
            }

            @Override
            public String getDescription() {
                return "Mars Mission Files (*.mars)";
            }
        };

        fileChooser.setFileFilter(marsFileFilter);

        int result = fileChooser.showOpenDialog(frame);


        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                byte[] data = new byte[(int) selectedFile.length()];
                fileInputStream.read(data);
                fileInputStream.close();

                fileContent = new String(data, StandardCharsets.UTF_8);

                textArea.clear();
                textArea.printf("Mission File Selected: '%s'\n\n%s\n\nData loaded and awaiting execution.", selectedFile.getName(), fileContent);

                executeButton.setEnabled(true);

            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Error reading the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No file selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void executeMission() {

        executeButton.setEnabled(false);
        final String[] fileLines = fileContent.split("\r\n");
        try {
            final String[] firstLine = fileLines[0].split(" ");
            final int plateauX = Integer.parseInt(firstLine[0]);
            final int plateauY = Integer.parseInt(firstLine[1]);
            final int numberOfRovers = Integer.parseInt(firstLine[2]);

            MarsMission marsMission = new MarsMission(new Plateau(plateauX, plateauY), numberOfRovers);

            for (int i = 0; i < numberOfRovers; i++) {
                final String[] firstLineOfData = fileLines[i * 2 + 1].split(" ");
                final int roverX = Integer.parseInt(firstLineOfData[0]);
                final int roverY = Integer.parseInt(firstLineOfData[1]);
                final CompassDirection heading = CompassDirection.valueOf(firstLineOfData[2]);
                final String commands = fileLines[i * 2 + 2];
                marsMission.addRover(new Rover(roverX, roverY, heading, commands), i);
            }


            textArea.clear();
            textArea.printf("MISSION EXECUTION AS FOLLOWS:\n\nPlateau limits: %d %d\n\n", plateauX, plateauY);

            Rover[] rovers = marsMission.getRovers();

            textArea.printf("Number of Rovers: %d\n\n", rovers.length);

            for (int i = 0; i < rovers.length; i++) {

                Rover rover = rovers[i];
                Pose startPose = rover.getPath().get(0);
                String heading = startPose.getDirection().name();
                String commands = rovers[i].getCommandSequence();
                textArea.printf("Data for Rover #%d:\n", i);
                textArea.printf("Starting pose: %d %d %s\n", startPose.getX(), startPose.getY(), heading);
                textArea.printf("Rover command sequence: %s\n", commands);
                textArea.printf("Final pose: %d %d %s\n\n", rover.getPose().getX(), rover.getPose().getY(), rover.getPose().getDirection());

                ArrayList<Pose> path = rover.getPath();


                boolean bCollision = false;
                for (int j = 1; !bCollision && j < path.size(); j++) {
                    Pose pose = path.get(j);
                    textArea.printf("Step %d, command: '%s' - Location: (%d, %d). Heading: %s.", i, commands.charAt(j - 1), pose.getX(), pose.getY(), pose.getDirection().name());
                    if (marsMission.isCollisionAtStep(i, j)) {
                        textArea.printf("    *** COLLISION ***");
                        bCollision = true;
                    }
                    textArea.newLine();
                }

                textArea.newLine();

                if (rover.pathStaysWithinPlateau(marsMission.getPlateau())) {
                    textArea.printf("MISSION ALERT: OUT OF BOUNDS. Rover left the plateau.\n");
                }
                if (bCollision) {
                    textArea.printf("MISSION ALERT: COLLISION. Rover collided with another rover!\n");
                }
                textArea.newLine();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error parsing data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
