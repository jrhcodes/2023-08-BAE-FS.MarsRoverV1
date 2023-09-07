package com.jrhcodes;

import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Pose;

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
    private final JTextArea textArea;
    private String fileContent;

    private Plateau plateau;
    private Rover rover;

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


        textArea = new JTextArea(10, 30);
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

                textArea.setText(String.format(String.format("Mission File Selected: '%s'\n\n%s\n\nData loaded and awaiting execution.", selectedFile.getName(), fileContent)));

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
            plateau = new Plateau(plateauX, plateauY);

            final String[] secondLine = fileLines[1].split(" ");
            final int roverX = Integer.parseInt(secondLine[0]);
            final int roverY = Integer.parseInt(secondLine[1]);
            final CompassDirection heading = CompassDirection.valueOf(secondLine[2]);
            final String commands = fileLines[2];

            rover = new Rover(roverX, roverY, heading, commands);

            textArea.setText(null);

            textArea.append(String.format("MISSION EXECUTION AS FOLLOWS:\n\nPlateau limits: %d %d\n\n", plateauX, plateauY));
            textArea.append(String.format("Rover starting pose %d %d %s\n", roverX, roverY, heading.name()));
            textArea.append(String.format("Rover orders: %s\n\n", commands));
            textArea.append(String.format("Rover command sequence: %s\n\n", commands));

            ArrayList<Pose> path = rover.getPath();


            for (int i = 1; i < path.size(); i++) {
                Pose pose = path.get(i);
                textArea.append(String.format("Step %d, command: '%s' - Location: (%d, %d). Heading: %s.\n", i, commands.charAt(i - 1), pose.getX(), pose.getY(), pose.getDirection().name()));
            }

            textArea.append(String.format("\nMission was a %s", rover.pathInBounds(plateau) ? "success!" : "failure - out of bounds."));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error parsing data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
