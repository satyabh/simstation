package plague;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import simstation.Agent;
import simstation.World;
import simstation.WorldView;

public class PlagueView extends WorldView {
    int PERSON_SIZE = 4;

    public PlagueView(World world) {
        super(world);
    }

    @Override
    protected void drawAgent(Agent a, Graphics gc) {
        if (((Person)a).dead) return;

        Graphics2D gc2d = (Graphics2D)gc;
        if (((Person)a).isInfected()) gc2d.setColor(Color.RED);
        else gc2d.setColor(Color.GREEN);
        gc2d.fillOval(
            a.getX() - PERSON_SIZE/2,
            a.getY() - PERSON_SIZE/2,
            PERSON_SIZE,
            PERSON_SIZE
        );
    }
}
