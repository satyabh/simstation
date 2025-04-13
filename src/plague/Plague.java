package plague;

import mvc.*;
import simstation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

class Person extends MobileAgent {
    boolean infected;
    int infectedStartClock;
    boolean dead = false;

    public Person(boolean infected) {
        super();
        this.infected = infected;
        if (this.infected) this.infectedStartClock = 0;
    }

    public void update() {
        Person neighbor = (Person) world.getNeighbor(this, 10);

        // Infect neighbor
        if (this.infected && neighbor != null && !neighbor.isInfected()) {
            if (Math.random() < (Plague.VIRULENCE / 100.0)) {
                ((Person)neighbor).setInfected(true);
                ((Person)neighbor).setInfectedStartClock(world.getClock());
            }
        }

        // Fatality / Recovery
        if (this.infected && this.world.getClock() >= this.infectedStartClock + Plague.RECOVERY_FATALITY_TIME) {
            this.setInfected(false);
            if (Plague.FATAL) {
                this.dead = true;
                this.stop(); 
            }
        }

        heading = Heading.random();
        move(4);
    }

    public boolean isInfected() { return infected; }
    public void setInfected(boolean infected) { this.infected = infected; }
    private void setInfectedStartClock(int clock) { this.infectedStartClock = clock; }
}

public class Plague extends World {
    public static int INITIAL_INFECTED = 10;
    public static int INITIAL_POPULATION_SIZE = 50;
    public static int RECOVERY_FATALITY_TIME = 50;
    public static boolean FATAL = true; // whether the end of infection lifespan kills the Person
    public static int VIRULENCE = 50; // % chance of infection
    // public static int RESISTANCE = 2; // % chance of resisting infection
    // RESISTANCE comes from assignment's page example, but Idk what to do with it 😭

    public void populate() {
        int infectedCount = (int)(INITIAL_INFECTED * .01 * INITIAL_POPULATION_SIZE);
        for(int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            if (i < infectedCount) addAgent(new Person(true));
            else addAgent(new Person(false));
        }
    }

    public void setVirulence(int value) {
        VIRULENCE = value;
        changed();
    }

    public void SetRecoveryTime(int value) {
        RECOVERY_FATALITY_TIME = value;
        changed();
    }

    private double infectedPercentage() {
        double totalInfected = 0;
        for(Agent a: agents) {
            if (((Person)a).infected && !((Person)a).dead) totalInfected++;
        }
        return totalInfected / this.alive * 100;
    }

    public void triggerFatal() {
        FATAL = !FATAL;
        changed();
    }

    public ArrayList<String> getStats() {
        String[] stats = new String[3];
        stats[0] = "#agents = " + this.alive;
        stats[1] = "#clock = " + this.clock;
        stats[2] = "% infected = " + String.format("%.2f", infectedPercentage());
        return new ArrayList<>(Arrays.asList(stats));
    }

    public static void main(String[] args) {
        AppPanel panel = new PlaguePanel(new PlagueFactory());

        panel.display();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.pack();
        frame.setResizable(false);
    }
}
