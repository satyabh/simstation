package plague;

import mvc.*;
import simstation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Plague extends World {
    public static int INITIAL_INFECTED = 10;
    public static int INITIAL_POPULATION_SIZE = 50;
    public static int RECOVERY_FATALITY_TIME = 50;
    public static boolean FATAL = true; // whether the end of infection lifespan kills the Person
    public static int VIRULENCE = 50; // % chance of infection
    public static int RESISTANCE = 2; // % chance of resisting infection
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

    public void setResistance(int value) {
        RESISTANCE = value;
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
}
