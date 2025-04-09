package simstation;

import java.awt.Point;

public abstract class MobileAgent extends Agent {
    protected Heading heading;

    public MobileAgent() {
        super();
        heading = Heading.random();
    }

    public Heading getHeading() {
        return heading;
    }

    public void turn(Heading newHeading) {
        heading = newHeading;
    }

    public void move(int steps) {
        Point oldPoint = new Point(xc, yc);

        for (int i = 0; i < steps; i++) {
            switch (heading) {
                case NORTH:
                    yc--;
                    break;
                case SOUTH:
                    yc++;
                    break;
                case EAST:
                    xc++;
                    break;
                case WEST:
                    xc--;
                    break;
            }

            // Wrap around the world if needed
            if (xc < 0) xc = World.SIZE - 1;
            if (xc >= World.SIZE) xc = 0;
            if (yc < 0) yc = World.SIZE - 1;
            if (yc >= World.SIZE) yc = 0;

            // Notify world of position change
            Point newPoint = new Point(xc, yc);
            world.changed(name, oldPoint, newPoint);
            oldPoint = newPoint;
        }
    }
}