package greed;

import java.awt.*;
import javax.swing.*;
import simstation.World;
import simstation.WorldView;
import simstation.Agent;

public class GreedView extends WorldView {
    private static final int COW_SIZE = 6;

    public GreedView(World world) {
        super(world);
    }

    @Override
    public void paintComponent(Graphics g) {
        // 1) compute scale factors
        Dimension size = getSize();
        double sx = size.getWidth()  / (double)World.SIZE;
        double sy = size.getHeight() / (double)World.SIZE;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(sx, sy);
        super.paintComponent(g2);

        g2.dispose();
    }

    @Override
    protected void drawAgent(Agent a, Graphics gc) {
        Graphics2D g = (Graphics2D) gc;
        if (a instanceof Cow) {
            // draw cow at center of its cell
            int cellX = a.getX();
            int cellY = a.getY();
            int half = Meadow.PATCH_SIZE/2;

            int cx = cellX + half;
            int cy = cellY + half;

            g.setColor(((Cow)a).energy > 0 ? Color.RED : Color.WHITE);
            g.fillOval(cx - COW_SIZE/2, cy - COW_SIZE/2, COW_SIZE, COW_SIZE);
        }
        else if (a instanceof Patch) {
            Patch p = (Patch) a;
            Color shade = new Color(0, 50 + p.getEnergy(), 0);
            g.setColor(shade);
            g.fillRect(a.getX(), a.getY(), Meadow.PATCH_SIZE, Meadow.PATCH_SIZE);
            g.setColor(Color.WHITE);
            g.drawRect(a.getX(), a.getY(), Meadow.PATCH_SIZE, Meadow.PATCH_SIZE);
        }
    }
}