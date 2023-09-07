# 2023-08-BAE-FS.MarsRoverV1
Version 1 of the Mars rover, written to spec with additional provision for multiple rovers.
Initial spec here: https://drive.google.com/file/d/1bP8NQ6sSnDUE1fA9TMSSfrKgZYZXWzRS/view?usp=drive_web&authuser=2

# Installation
To run this project install:
JDK 1.7
intellij IDEA
maven

Clone the git repository and open the folder in intellij.
To run the program, execute MarsRoverApp.

To run unit tests, execute the tests in the package "com.jrhcodes" under the "src/test/java" project folder.

# Using the App
1. Execute the class MarsRoverApp.
2. A window will open.
3. Click the select file button to select the mission file. 
   1. There is a file "test2.mars" in the project root you can use to test the program. 
   2. This reads the file contents into a text buffer and displays them in the main window.
   3. No error checking is performed at this stage.
4. Click "Execute Mission" to execute the mission. 
   1. This will process the mission data and then display a mission summary.
   2. The mission summary shows data for the plateau first, followed by data for each rover.
   3. For each rover the following are displayed
      1. Start position and heading ('pose').
      2. Command sequence (see below).
      3. Final location and heading.
      4. The path taken by the rover as it follows its command sequence across the surface of Mars.
5. Close the app to finish or click select file to load another mission.

# Format of Mars Mission Files
Mission files have by default the ".mars" file extension and are plan text files.
The file format is as follows:
1. A single line containing 3 integers separated by spaces.
   1. The first is the width (E/W) of the mission plateau.
   2. The second is the height (N/S) of the mission plateau.
   3. The third is the number of rovers on the mission.
   4. e.g. if this line was "10 9 2" it would define a plateau with a width of 10 and height of 9, and 2 rovers for the mission.
2. After the header, there are 2 lines for each roved.
   1. The first line gives the x and y coordinates at which the rover starts, and its heading, separated by spaces.
      1. x and y coordinates must be integers.
      2. heading must be one of the compass ordinals "N", "S", "E", "W".
      3. e.g. the line "1 2 N" would start the rover in the position (1, 2), heading North.
   2. The second line gives the command sequence to be followed by the rover, in the form of a string of letters, each instigating a single command.
      1. Permitted command values are: "L" - turn left 90 degrees, "R" - turn right 90 degrees, "M" - move forwards by a distance of 1.
      2. e.g. a command sequence of "LMR" should turn the rover left, move it forwards 1, then turn it right.
3. There is no more information contained in the mission file.


# File Structure

## src/main/java
Contains the latest version of the app code. 

### com.jrhcodes.app
The main application folder. It contains one class "MarsRoverApp" which is the Swing app used to executue missions.
It controls all aspects of the gui, using other classes to execute missions etc., displaying the results.

### com.jrhcodes.model

Contains classes which directly model aspects of the specification, used by MarsRoverApp:

#### Rover
- defines a rover, it's initial position, command sequence, heading and path taken.   
#### Plateau 
- defines the limits within which rovers may move
#### MarsMission 
- collects together all the rovers and the plateau definition for current the mission.

### com.jrhcodes.scratch
- Junk/temporary code - ignore.

### com.jrhcode.spatialmath
Contains classes to assist with storing coordinates, directional and vector maths.

#### CompassDirection
- enumeration storing the four cardinal compass points and the vectors associated with moving in their respective directions.

#### Point
- handles ordered pairs in the form (in x, int y)

#### Pose
- combines a CompassFirection and a Point to give a pose

#### Vector
- derived from the Point class

### com.jrhcode.swingutils
Includes 1 class which is derived from the swing JTextArea, but adds a printf functin - to pass formatted strings into the text area and a clear function, which clears it.

## src/test
This contains two folders: java - which contains source code for unit tests and resources which contain the associated data.

- under java folder: this contains source for the test classes for the classes in the same package under src/main/java 
- under resources folder: this contains the data files used in tests
- The first two lines of each data file are comments and give info about the fields used to test.