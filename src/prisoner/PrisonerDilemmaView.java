package prisoner;

import simstation.Agent;
import simstation.World;
import simstation.WorldView;
import java.awt.*;

public class PrisonerDilemmaView extends WorldView {
    
    public PrisonerDilemmaView(World world) {
        super(world);
    }
    
    @Override
    protected void drawAgent(Agent agent, Graphics gc) {
        if (agent instanceof Prisoner) {
            Prisoner p = (Prisoner) agent;
            Graphics2D gc2d = (Graphics2D) gc;
            
            String name = p.getName();
            if (name.startsWith("Cheat")) {
                gc2d.setColor(Color.BLUE);
            } else if (name.startsWith("Coop")) {
                gc2d.setColor(Color.RED);
            } else if (name.startsWith("Random")) {
                gc2d.setColor(Color.YELLOW);
            } else if (name.startsWith("TitForTat")) {
                gc2d.setColor(Color.BLUE);
            } else {
                gc2d.setColor(Color.GREEN);
            }

            gc2d.fillOval(p.getX() - 5, p.getY() - 5, 10, 10);
            gc2d.setColor(Color.BLACK);
            gc2d.drawString(String.valueOf(p.getFitness()), p.getX() + 6, p.getY() + 4);
        } else {
            super.drawAgent(agent, gc);
        }
    }
}