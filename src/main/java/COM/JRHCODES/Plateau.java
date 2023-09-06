package COM.JRHCODES;

import com.jrhcodes.spatialmath.Point;

public class Plateau {
    private final Point topRight ;
    private static final Point origin = new Point(0, 0);

    public Plateau( Point topRight ){
        this.topRight = topRight;
    }

    public boolean contains(Point point) {
        return point.isBoundBy(origin, topRight);
    }
}
