import com.jrhcodes.Rover;
import com.jrhcodes.spatialmath.CompassDirection;
import com.jrhcodes.spatialmath.Pose;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {
//    @Test
//    public void testRover() {
//        Rover rover = new Rover( 1, 1, E, "MMLRRRMMMMM");
//        rover.executeMission();
//        ArrayList<Pose> path = rover.getPath();
//        for( Pose pose: path ) {
//            System.out.printf("Rover: %d, %d, %s\r\n", pose.getX(), pose.getY(), pose.getDirection().name());
//        }
//    }

    @ParameterizedTest
    @CsvFileSource(resources = "RoverPathTest.csv", numLinesToSkip = 2)
    public void testRoverPathX(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover( initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalX, finalPose.getX());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "RoverPathTest.csv", numLinesToSkip = 2)
    public void testRoverPathY(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover( initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalY, finalPose.getY());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "RoverPathTest.csv", numLinesToSkip = 2)
    public void testRoverPathDir(int initX, int initY, CompassDirection initDir, String commands, int finalX, int finalY, CompassDirection finalDir) {
        Rover rover = new Rover( initX, initY, initDir, commands);
        Pose finalPose = rover.getPose();
        assertEquals(finalDir, finalPose.getDirection());
    }
}