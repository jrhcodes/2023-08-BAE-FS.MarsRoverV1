package com.jrhcodes.app;

import com.jrhcodes.model.MarsMission;
import com.jrhcodes.model.Plateau;
import com.jrhcodes.model.Rover;
import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Pose;
import com.jrhcodes.swingutils.JTextAreaWithPrintf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MarsRoverApp {
    private final JFrame frame;
    private final JButton executeButton;
    private final JTextAreaWithPrintf textArea;
    private JScrollPane missionTablePane = null;
    String fileBuffer = null;

    public MarsRoverApp() {


        frame = new JFrame("Mars Rover App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton openButton = new JButton("Select File");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileBuffer = openFile();
            }
        });
        openButton.setPreferredSize(new Dimension(100, 30));

        executeButton = new JButton("Execute Mission");
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeMission(fileBuffer);
            }
        });
        executeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(openButton);
        buttonPanel.add(executeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        textArea = new JTextAreaWithPrintf(10, 30);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.NORTH);


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

    private String openFile() {

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

        String buffer = null;


        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                byte[] data = new byte[(int) selectedFile.length()];
                fileInputStream.read(data);
                fileInputStream.close();

                buffer = new String(data, StandardCharsets.UTF_8);

                textArea.clear();
                textArea.printf("Mission File Selected: '%s'\n\n%s\n\nData loaded and awaiting execution.", selectedFile.getName(), buffer);

                if (missionTablePane != null) {
                    frame.remove(missionTablePane);
                }
                executeButton.setEnabled(true);



            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Error reading the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No file selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        return buffer;
    }

    MarsMission parseFileBuffer(String buffer) {

        final String[] fileLines = buffer.split("\r\n");

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

        return marsMission;
    }

    private void updateMissionHeader(MarsMission marsMission) {
        Plateau plateau = marsMission.getPlateau();

        textArea.clear();
        textArea.printf("MISSION EXECUTION AS FOLLOWS:\n\nPlateau limits: %d %d\n\n", plateau.getTopRight().getX(), plateau.getTopRight().getY());

        int numberOfRovers = marsMission.getRovers().length;

        for (int i = 0; i < numberOfRovers; i++) {
            Rover rover = marsMission.getRover(i);
            Pose[] roverPath = rover.getPath();
            Pose startPose = roverPath[0];

            String heading = startPose.getDirection().name();
            String commands = rover.getCommandSequence();

            textArea.printf("Data for Rover #%d:\n", i);
            textArea.printf("Starting pose: %d %d %s\n", startPose.getX(), startPose.getY(), heading);
            textArea.printf("Rover command sequence: %s\n", commands);
            textArea.printf("Final pose: %d %d %s\n\n", rover.getPose().getX(), rover.getPose().getY(), rover.getPose().getDirection());

        }
    }

    void updateMissionTable(MarsMission marsMission) {

        int numberOfRovers = marsMission.getRovers().length;

        if (missionTablePane != null) {
            frame.remove((missionTablePane));
        }

        String[][] emptyTable = {};
        String[] columnNames = new String[numberOfRovers + 1];

        columnNames[0] = "Step";
        for (int i = 0; i < numberOfRovers; i++) {
            columnNames[i+1] = "Rover" + i;
        }

        DefaultTableModel model = new DefaultTableModel(emptyTable, columnNames);

        // Create a JTable with the model
        JTable table = new JTable(model);

        table.setVisible(true);
        // Create a JScrollPane to hold the table
        missionTablePane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        frame.add(missionTablePane, BorderLayout.CENTER);

        final String[] rowData = new String[numberOfRovers+1];

        rowData[0] = String.valueOf(0);

        for (int roverIndex = 0; roverIndex< numberOfRovers; roverIndex++) {

            final Rover rover = marsMission.getRover(roverIndex);
            final Pose[] path = rover.getPath();
            final Pose pose = path[0];
            final int tableColumn = roverIndex + 1;

            rowData[tableColumn] = String.format("Land->(%3d, %3d)%s", pose.getX(), pose.getY(), pose.getDirection().name());
        }

        model.addRow(rowData);

        int longestPath = marsMission.findLongestRoverPath();

        for (int step = 0; step < longestPath-1; step++) {

            rowData[0] = String.valueOf(step+1);

            for (int roverIndex = 0; roverIndex < numberOfRovers; roverIndex++) {

                final Rover rover = marsMission.getRover(roverIndex);
                final Pose[] path = rover.getPath();
                final int tableColumn = roverIndex + 1;


                if (step < path.length-1) {
                    final Pose pose = path[step+1];
                    rowData[tableColumn] =
                            String.format("%c->(%3d, %3d)%s", rover.getCommandSequence().charAt(step),
                                    pose.getX(), pose.getY(), pose.getDirection().name());

                } else {
                    rowData[tableColumn] ="-";

                }


            }


            model.addRow(rowData);

            boolean roverCollision = false;
            boolean roverLeftPlateau = false;

            if (roverLeftPlateau) {
                textArea.printf("MISSION FAILED: OUT OF BOUNDS. Rover left the plateau.\n");
            }
            if (roverCollision) {
                textArea.printf("MISSION FAILED: COLLISION. Rover collided with another rover!\n");
            }

        }
        frame.setVisible(true);


    }

    private void executeMission(String fileBuffer) {

        executeButton.setEnabled(false);

        try {

            MarsMission marsMission = parseFileBuffer(fileBuffer);
            updateMissionHeader(marsMission);
            updateMissionTable(marsMission);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error parsing data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
