package com.jrhcodes;

public class MissionFileReader {/*
    static public Mission readMissionFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final String pleaeauDefinition = reader.readLine();

            if (pleaeauDefinition != null) {
                String[] plateauSize = pleaeauDefinition.split(" ");
                if (plateauSize.length == 2) {
                    try {
                        int plateauSizeX = Integer.parseInt(plateauSize[0]);
                        int plateauSizeY = Integer.parseInt(plateauSize[1]);
                        Mission mission = new Mission(new Plateau( new Point(plateauSizeX, plateauSizeY)));
                        // Now, you have the two numbers and can work with them
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in plateau definition.");
                    }
                } else {
                    System.err.println("The first line does not contain two numbers separated by a space.");
                }
            } else {
                System.err.println("The file is empty.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

    }

*/
}
