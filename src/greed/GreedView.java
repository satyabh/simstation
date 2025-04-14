package greed;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import simstation.Agent;
import simstation.World;
import simstation.WorldView;

public class GreedView extends WorldView {
    private static int COW_SIZE = 6;
    public GreedView(World world) {
        super(world);
    }

    @Override
    protected void drawAgent(Agent a, Graphics gc) {
        // Cows
        if (a instanceof Cow) {
            Color oldColor = gc.getColor();
            // if (((Cow)a).dead) return;
            Graphics2D gc2d = (Graphics2D)gc;
            gc2d.setColor(Color.RED);
            if (((Cow)a).energy <= 0) gc2d.setColor(Color.WHITE);
            gc2d.fillOval(
                a.getX() - COW_SIZE/2,
                a.getY() - COW_SIZE/2,
                COW_SIZE,
                COW_SIZE
            );

            gc.setColor(oldColor);
        }

        // Patches
        if (a instanceof Patch) {
            Color oldColor = gc.getColor();
            Graphics2D gc2d = (Graphics2D)gc;
            gc2d.setColor(new Color(0, 50 + ((Patch)a).energy, 0));
            gc2d.fillRect(
                a.getX() - Patch.patchSize/2,
                a.getY() - Patch.patchSize/2,
                Patch.patchSize,
                Patch.patchSize
            );
            gc2d.setColor(Color.WHITE);
            gc2d.drawRect(
                a.getX() - Patch.patchSize/2,
                a.getY() - Patch.patchSize/2,
                Patch.patchSize,
                Patch.patchSize
            );

            gc.setColor(oldColor);
        }
    }
}
