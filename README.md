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

### com.jrhcodes.marsrover..scratch
- Junk/temporary code - ignore.

### com.jrhcode.marsrover.spatialmath
Contains classes to assist with storing coordinates, directional and vector maths.

#### CompassDirection
- enumeration storing the four cardinal compass points and the vectors associated with moving in their respective directions.

#### Point
- handles ordered pairs in the form (in x, int y)

#### Pose
- combines a CompassDirection and a Point to give a pose

#### Vector
- derived from the Point class

### com.jrhcode.marsrover.swingutils
Includes the class JTextAreaWithPrintf which is derived from the swing JTextArea, but adds a printf function - to pass formatted strings into the text area and a clear function, which clears it.

## src/test
This contains two folders: java - which contains source code for unit tests and resources which contain the associated data.

- under java folder: this contains source for the test classes for the classes in the same package under src/main/java 
- under resources folder: this contains the data files used in tests
- The first two lines of each data file are comments and give info about the fields used to test.

### in the resource folder:

#### com.jrhcodes.marsrover.model:

#### RoverNegativePathTest.csv
Contains data to test rover pathing, including negative values. 
- The first 2 lines of the file are comments
- Other lines give a sequence of comma delimited values as follows:
  - StartX, StartY, StartHeading, CommandSequence, FinalX, FinalY, FinalHeading
      - StartX, StartY, StartHeading give the initial location and heading of the rover. 
      - CommandSequence - is a sequence of characters (L/R/M) giving the command sequence for the rover to follow.
      - FinalX, FinalY, FinalHeading give the expected final position and direction of the rover.
- Each line is a separate test case.

#### RoverPathTest.csv
Has the same format as RoverNegativePathTest.csv except that it contains only positive path values.

Cases for these tests were chosen as follows:
- Command Sequences which didn't move the rover, just rotated it.
- Command Sequences which returned the rover to its original position.
- Command Sequences which both moved the rover and changed its heading.
- Command Sequences which kept the rover within bounds of both a 1x1 and 10x10 plateau.

### RoverCommendSetTest
Test rover command sequence data.

### RoverCommendTest
Test individual command values

### com.jrhcodes.marsrover.spatialmath:
#### TestCompassDirectionValues
Contains the vector expected for each compass ordinal, and some test data - a start point and the point you'd expect to end up moving in the direction from that start point
#### TestPointValues
Contains a series of ordered pairs with which to test the Point class constructors and their associated accessor functions.
#### TestVectorValues
Contains a series of coordinates, vector x and y values and the result of applying the vector to the ordered pair. 

# What I didn't have time for
- Refactoring the may app into a main app class and utility classes for most side functionality.
  - I have created shells for these in the project folder:
    -   MarsRoverAppUI: into which I would move all the swing stuff - possibly migrate it to JavaFX. 
      - I have never created a GUI project in Java and found Swing first - then JavaFX. I tried installing JavaFX and then decided I had already gone too far with Swing, so left it as-is.
      - 
    - MarsRoverMissionLoader which contain everything to do with loading the mission file
      - I'd also separate out the execution of missions from the loading. Currently, rovers execute their mission as soon as the parameters are set in the constructor, I'd add a separate executeMission method, only.
  - Use this refactoring to create a separate MarsRoverConsoleApp using existing functionality
  - It would also make testing much easier
- The app has minimal error checking atm on, loaded data - I'd add a lot more.

# Future expansions:
## give the Plateaus class a height map and obstacles
- for an n x m Plateau, there would be an n x m array, each element (x,y) showing the relative height of the location.
- where the difference in heights between two locations is above a threshold value, a rover would not be able to traverse the terrain between the two and the mission would fail.
## fuel cells
- each order processed by a rover would consume different amounts of energy from the rover's battery. 
- The rover could pause and charge, requiring a new command "C". THe rover does not move but it's batteries recharge.
- Suggested energy use:
  - moving horizontally: 2 energy
  - moving up or down a slope: 2 energy + 1 per different in height between start location and the next step.
  - rotate left or right: 1 energy.
  - hold and charge: + 20 energy.
## new command types
- new mission related command types could be added
- e.g. take photo, collect sample
## new vehicle types
- new types of vehicle could be created by either extending the rover class or creating an interface other vehicle types could include
- e.g. a "Copter" class. 
  - smaller battery than the rover and so must land to recharge, frequently.
  - is unhindered by gradients and can pass over obstacles. 
  - It would not collide with rovers or charging copters whilst airborne. 
  - When it recharges it gains only 10 energy due to smaller solar panels.
  - it has no heading and moves in any direction, N, S, E, W from its current position.
## route finding
- adding functionality to the app to find the best route for a set of vehicles to take on the surface.
- each vehicle would have a destination toward which they would head
- routes could be optimised in terms of time taken or fuel usage
- extensions to the route finding could add the ability to give each vehicle multiple waypoints, which they must visit to complete their mission
- if orders such as take photo or collect sample were added to vehicle capabilities, orders at the various waypoints could be specified - e.g. collect a sample at 2,3

# Acknowledgements
I'd like to thank Tech Returners for their assistance on the course and especially Simon Morgan and Chris Ward, course tutors for the Java section of the course. 

# Appendix
You can see my initial class diagram, below.
![Class Diagram](https://i.imgur.com/36w3aYy.jpg)

The original test schedule and design document is [here](https://docs.google.com/document/d/14nbPuLkE97libjRZ2grYHXHp-Q2SPAJHYxB_BC8P7tk/edit?usp=sharing).
