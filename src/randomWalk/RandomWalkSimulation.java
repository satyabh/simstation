package randomWalk;


import mvc.*;
import simstation.*;

import javax.swing.*;
import java.awt.*;

class Drunk extends MobileAgent {

    public Drunk() {
        super();
    }

    public void update() {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(20) + 1;
        move(steps);
    }

}


class RandomWalkFactory extends WorldFactory {
    public Model makeModel() { return new RandomWalkSimulation(); }
    public String getTitle() { return "Random Walks";}
}

public class RandomWalkSimulation extends World {

    public void populate() {
        for(int i = 0; i < 50; i++)
            addAgent(new Drunk());
    }

    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new RandomWalkFactory());
        panel.display();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.pack(); // sets the window size to whatever needs
        frame.setResizable(false);  // locks and prevents user from resizing
    }
}
