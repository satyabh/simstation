package simstation;

import mvc.*;
import java.awt.*;
import java.util.Iterator;

public class WorldView extends View {
    private static final int AGENT_SIZE = 10; // Diameter 10 as specified

    public WorldView(World world) {
        super(world);
        world.subscribe(this);
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(World.SIZE, World.SIZE));
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        World world = (World)model;
        Graphics2D gc2d = (Graphics2D)gc;

        // Draw a border around the world
        gc2d.setColor(Color.BLUE);
        gc2d.drawRect(0, 0, World.SIZE - 1, World.SIZE - 1);

        // Draw agents
        for (Iterator<Agent> it = world.iterator(); it.hasNext(); ) {
            Agent agent = it.next();
            drawAgent(agent, gc2d);
        }
    }

    // Default implementation draws a diameter 10 red filled oval at the agent's location
    protected void drawAgent(Agent a, Graphics gc) {
        Graphics2D gc2d = (Graphics2D)gc;
        gc2d.setColor(Color.RED);
        gc2d.fillOval(
            a.getX() - AGENT_SIZE/2,
            a.getY() - AGENT_SIZE/2,
            AGENT_SIZE,
            AGENT_SIZE
        );
    }

    @Override
    public void update() {
        repaint();
    }
}